var mongoose = require( 'mongoose' ),
  api = require( API_CORE ),
  Utils = require( UTILS_PATH ),
  User = mongoose.model( 'User' ),
  Game = mongoose.model( 'Game' ),
  express = require( 'express' );


//*********************************************
//* User API Endpoints
//* Includes initial ghateway signup
//*********************************************
exports.bind = function( app ) {
  var gameRouter = express.Router();



  // Get a user's games
  gameRouter.post( '/games/user/:userId', function( req, res, next ) {
    var userId = req.param( "userId" );
    // TODO: Require "currentUser", need to implement header auth

    Game.statics.findByPlayerId( userId, function( err, games ) {
      if ( err ) {
        api.ServerErrorResponse( err, res );
        return;
      }
      api.JsonResponse( games, res, 200 );
      return next();
    });
  });


  app.use(gameRouter);
};