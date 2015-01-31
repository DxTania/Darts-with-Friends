var mongoose = require( 'mongoose' ),
		api = require( API_CORE ),
		User = mongoose.model( 'User' ),
		Utils = require( UTILS_PATH ),
		express = require( 'express' );


//*********************************************
//* User API Endpoints
//* Includes initial ghateway signup
//*********************************************
exports.bind = function( app ) {
	var gamesRouter = express.Router();

	// TODO: EVERYTHING

	app.use(gamesRouter);
};