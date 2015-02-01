//*******************************************
//* GAME MODEL
//* Necessary to link our authentication with
//* various auth protected APIs
//* @requires mongoose
//* @requires mongoose-unique-validator
//* @requires crypto
//*******************************************
var mongoose = require('mongoose'),
    Schema = mongoose.Schema;


var GameSchema = new Schema({
  gameType:           { type: String },
  playerOne:          {
                        id: Number,
                        name: String
                      },
  playerTwo:          {
                        id: Number,
                        name: String
                      },
  turnPlayerId:       { type: Number },
  scoresOne:          [ Number ],
  scoresTwo:          [ Number ],
  startDate:          { type: Date, default: Date.now },
  endDate:            { type: Date },
  winnerId:           { type: Number },
  loserId:            { type: Number },
  status:             { type: String, default: "pending" },
  turnCount:          { type: Number, default: 0 }

  // TODO: Game Schema
});


// Requirements
// TODO: Required fields

// Model Helpers
GameSchema.methods = {

};

GameSchema.statics = {
  findById: function findById( id, callback ) {
    return this.where( {"_id": id} ).findOne( callback );
  },
  findByPlayerId: function( id, callback ) {
    return this.where( {"playerOne": {"id": id }} ).or( {"playerTwo": {"id": id }} ).exec( callback );
  }

};

// Exported MODEL
module.exports = GameSchema;