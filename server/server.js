var express = require( 'express' ),
    mongoose = require( 'mongoose' ),
    fs = require( 'fs' ),
    cookieParser = require( 'cookie-parser' ),
    bodyParser = require( 'body-parser' ),
    expressValidator = require( 'express-validator' ),
    _       = require( 'underscore' ),
    async = require('async');

//*******************************************
//* GLOBAL VARS (accessible from anywhere)
//*******************************************
require( './globals.js' );




//*******************************************
//* DATABASE INIT
//*******************************************
/**
 * Creates connection to the mongo database
 * @param {String} Config.db
 */
var connect = function() {
  var options = { server: { socketOptions: {keepAlive: 1 } } };
  mongoose.connect( CONFIG.db, options );
}
connect();

// Handle errors
mongoose.connection.on( 'error', function( err ) {
  console.log( err );
});

// Reconnect on disconnect
mongoose.connection.on( 'disconnected', function(err) {
    connect();
});
// Handle errors
mongoose.connection.on( 'error', function(err) {
    console.log(err);
});
// Reconnect on disconnect
mongoose.connection.on( 'disconnected', function(err) {
    connect();
});

// Bootstrap models
var models_path = __dirname + '/models';
fs.readdirSync(models_path).forEach(function (file) {
  if (~file.indexOf('.js')) require(models_path + '/' + file)
});



//*******************************************
//* Server FAILURE listener
//*******************************************
process.on( 'uncaughtException', function( error ) {
  console.error( error.stack ? error.stack : error );
  process.exit(1);
});
// this is always done anyway AND error.stack doesn't contain whole stack.
// use some external logs



//*******************************************
//* Server initialization
//*******************************************
// Setup all middleware
var app = express();
// Body parser for parameter reading
app.use( bodyParser.json() );
// Validator which will help with validating request params
app.use( expressValidator() );

// Bootstrap models
fs.readdirSync(MODEL_PATH).forEach(function(filename) {
    var schemaName = filename.replace(/\.js$/, ''),
        Schema = require(MODEL_PATH + filename);

    mongoose.model(schemaName, Schema);
});

// Include all api files
var apiDir = fs.readdirSync( __dirname + '/api' );
_.filter( apiDir, function( libFile ) {
    if ( !fs.statSync( __dirname + '/api/' + libFile ).isDirectory() && libFile.indexOf( '.js' ) !== -1 && libFile != 'API.js' ) {
      console.log( 'Binding API Class: ' + libFile );
      require( __dirname + '/api/' + libFile ).bind( app );
    }
});


process.env.TZ = 'UTC';


//*******************************************
//* Server Start
//*******************************************

app.listen( CONFIG.port, function( error ) {
  if ( error ) {
    console.error( error );
    process.exit(1);
  }
  else {
    console.log( 'Listening on Port: ' + CONFIG.port );
  }
});
