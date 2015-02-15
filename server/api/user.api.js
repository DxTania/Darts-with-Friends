var mongoose = require( 'mongoose' ),
		api = require( API_CORE ),
	Utils = require( UTILS_PATH ),
		User = mongoose.model( 'User' ),
		express = require( 'express' );


//*********************************************
//* User API Endpoints
//* Includes initial ghateway signup
//*********************************************
exports.bind = function( app ) {
	var userRouter = express.Router();

	var userResponse = function( user, responseObj ) {
		var censoredFields = { password: true };
		var censoredUser = Utils.Security.censorResponse( user, censoredFields );
		// TODO: Implement header auth
		//Utils.Security.setAuthCookie( user, responseObj );
		api.JsonResponse( censoredUser, responseObj, 200 );
	};

	var invalidAuthResponse = function( responseObj ) {
 	  api.JsonResponse( 'Invalid userName/password combination.', responseObj, 400 );
 	  return;
	};

	function makeReqFun( required ) {
		return function( req, res, next ) {
			if ( !req.body[required] ) {
				api.JsonResponse( "Missing parameter: " + required, res, 400 );
				return false;
			}
			return next();
		}
	}

	// For requiring post parameters
	var requirables = [ "authToken", "name", "isFacebook", "fbAuthToken" ];
	var requireParam = {};
	for ( var i = 0; i < requirables.length; i++ ) {
		var required = requirables[i];

		requireParam[required] = makeReqFun( required );
	}

	// Create new user, require name and post parameters
	userRouter.post( '/', requireParam["name"], function( req, res, next ) {
		console.log( "Hello" );
		console.log( req.body );
		// TODO Validate Parameters
		var newUser = new User({
			name: req.body.name
		});

		if ( !req.body["fbAuthToken"] && !req.body["password"] ) {
			invalidAuthResponse( res );
		}

		// If not facebook register, build password
		if ( !req.body["fbAuthToken"] ) {
			newUser.password = Utils.Security.encryptPassword( req.body.password );
		} else {
			// Give new user it's fbAuthToken value
			newUser.fbAuthToken = req.body.fbAuthToken;
		}

		newUser.authToken = Utils.Security.getAuthToken();

		newUser.save( function( err, user ) {
			if ( err ) {
				api.ServerErrorResponse( err, res );
				return;
			}
			userResponse( user, res );
			return next();
		});
	});


	// Resends auth token to user.
	userRouter.post( '/signin', function( req, res, next ) {
		// TODO: validate parameters
		User.findByName( req.body.name , function( error, existingUser ) {
			if ( error ) {
				api.ServerErrorResponse( error, res );
			 	return;
			}

		 	if ( !existingUser || ( !req.body.fbAuthToken && !existingUser.checkPassword( req.body.password) )
				   || ( existingUser.fbAuthToken != req.body["fbAuthToken"] )) {
		 	  invalidAuthResponse( res );
				return;
			}

		 	userResponse( existingUser, res );
			return next();

		});
	});

	// Link user router to application
	app.use( '/user', userRouter );

};