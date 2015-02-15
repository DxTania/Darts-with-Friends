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

	// New game creation
	gameRouter.post( '/game', function( req, res, next ) {
		// Create newUser object and setup auth data
		// TODO: Validate parameters
		var playerOne = User.findById( req.body.playerOneId );
		var playerTwo = User.findById( req.body.playerTwoId );

		var newGame = new Game({
			gameType: req.body.gameType,
			playerOne: { id: playerOne._id, name: playerOne.name },
			playerTwo: { id: playerTwo._id, name: playerTwo.name },
			turnPlayerId: playerOne._id
		});

		newGame.save( function( error, game ) {
			if ( error ) {
				api.ServerErrorResponse( error, res );
				return;
			}
			api.JsonResponse( game, res, 200 );
			return next();
		});
	});

	gameRouter.get( '/game/:gameId', function( req, res, next ) {
		var gameId = req.param( "gameId" );
		// TODO: Validate parameters

		Game.statics.findById( gameId, function( error, game ) {
			if ( error ) {
				api.ServerErrorResponse( error, res );
				return;
			}
			api.JsonResponse( game, res, 200 );
			return next();
		})
	});

	app.use(gameRouter);
};