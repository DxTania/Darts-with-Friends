//*******************************************
//* GAME MODEL
//* Necessary to link our authentication with
//* various auth protected APIs
//* @requires mongoose
//* @requires mongoose-unique-validator
//* @requires crypto
//*******************************************
var mongoose = require('mongoose'),
    uniqueValidator = require('mongoose-unique-validator'),
    Schema = mongoose.Schema,
    User = mongoose.model("User"),
    crypto = require('crypto');


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
  scoresOne:          [ type: Number ],
  scoresTwo:          [ type: Number ],
  startDate:          { type: Date, default Date.now },
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
};

// Exported MODEL
module.exports = GameSchema;