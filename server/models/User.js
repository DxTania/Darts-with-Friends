//*******************************************
//* USER MODEL
//* Necessary to link our authentication with
//* various auth protected APIs
//* @requires mongoose
//* @requires mongoose-unique-validator
//* @requires crypto
//*******************************************
var mongoose = require( 'mongoose' ),
    uniqueValidator = require( 'mongoose-unique-validator' ),
    Schema = mongoose.Schema,
    crypto = require( 'crypto' ),
    request = require( 'request' );


var UserSchema = new Schema({
  email:              { type: String, unique: true },
  name:               { type: String },
  password:           { type: String },
  authToken:          { type: String },
  fbAccessToken:      { type: String },
  fbId:               { type: String }     // TODO: Unique
});



// Requirements
// UserSchema.path('name').required( true, 'Please provide a userName.');
UserSchema.plugin( uniqueValidator, { message: 'That userName is already in use.'} );



// Model Helpers
UserSchema.methods = {

  checkPassword: function( inPass ) {
    if ( !inPass ) inPass = '';
    var pwElements = this.password.split( ':', 3 );
    var algo = pwElements[0], salt = pwElements[1], validHash = pwElements[2];

    var hash = crypto.createHash( 'sha1' ).update( salt + inPass ).digest( 'hex' );
    return ( hash === validHash );
  }

};



// Static Methods
UserSchema.statics = {
  findById: function findById( id, callback ) {
    return this.where( {"_id": id} ).findOne( callback );
  },
  findByEmail: function findByEmail( email, callback ) {
    return this.where( {"email": email} ).findOne( callback );
  },
  findByName: function findByName( name, callback ) {
    return this.where({ "name": name }).findOne( callback );
  },

  checkExisting: function checkExisting( email, callback ) {
    this.where({ "email": email } ).findOne( function( err, exUser ) {
      return callback( !!exUser );
    });
  }//,
  //verifyFacebookAccess: function verifyFacebookAccess( accessToken, userId, callback ) {
  //  // TODO: request to https://graph.facebook.com/me?fields=id&access_token=@accesstoken
  //  // TODO: add fields for the data
  //  // TODO: Match userId to the responses userId, callback with error or data
  //  request( 'https://graph.facebook.com/me?fields=id&access_token='+accessToken, function( err, res, body ) {
  //    if ( error ) return callback( error );
  //
  //
  //  });
  //}


}

// Exported MODEL
module.exports = UserSchema;