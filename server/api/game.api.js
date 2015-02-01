var mongoose = require( 'mongoose' ),
		api = require( API_CORE ),
		User = mongoose.model( 'User' ),
		Game = mongoose.model( 'Game' ),
		Utils = require( UTILS_PATH ),
		express = require( 'express' );


//*********************************************
//* User API Endpoints
//* Includes initial ghateway signup
//*********************************************
exports.bind = function( app ) {
	var gamesRouter = express.Router();

	// TODO: EVERYTHING
	// New game creation
	userRouter.post( '/game', function( req, res, next ) {
		// Create newUser object and setup auth data
		var playerOne = User.findById( req.body.playerOneId );
		var playerTwo = User.findById( req.body.playerTwoId );

		var newGame = new Game({
			gameType: req.body.gameType,
			playerOne: { id: playerOne._id, name: playerOne.name },
			playerTwo: { id: playerTwo._id, name: playerTwo.name },
			turnPlayerId: playerOne._id
			// playerOne: req.body.playerOne,
		});

		newGame.save( function( error, game ) {
			if ( error ) {
				api.ServerErrorResponse( error, response );
				return;
			}
			api.JsonResponse( game, response, 200 );
			next();
		});

	});

	app.use(gamesRouter);
};