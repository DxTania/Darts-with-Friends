//*******************************************
//* USER MODEL
//* Necessary to link our authentication with
//* various auth protected APIs
//* @requires mongoose
//* @requires mongoose-unique-validator
//* @requires crypto
//*******************************************
var mongoose = require('mongoose'),
    uniqueValidator = require('mongoose-unique-validator'),
    Schema = mongoose.Schema,
    crypto = require('crypto');


var UserSchema = new Schema({
  name:               { type: String, unique: true },
  password:           { type: String },
  authToken:          { type: String },
  fbAuthToken:        { type: String },
  //fbId:               { type: Number, unique: true }
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
  findByName: function findByName( name, callback ) {
    return this.where({ "name": name } ).findOne( callback );
  }
}

// Exported MODEL
module.exports = UserSchema;