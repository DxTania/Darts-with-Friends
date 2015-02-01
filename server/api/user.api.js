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
	}

	var invalidAuthResponse = function( responseObj ) {
 	  api.JsonResponse( 'Invalid userName/password combination.', responseObj, 400 );
 	  return;
	}

	// For requiring post parameters
	var requirables = [ "authToken", "name", "isFacebook", "fbAuthToken" ];
	var requireParam = [];
	for ( var i = 0; i < requirables.length; i++ ) {
		requireParam[requirables[i]] = function( req, res, next ) {
			if ( !req.body[requirables[i]] ) {
				api.JsonResponse( "Missing parameter: " + requirables[i], res, 400 );
				return false;
			}
			return next();
		}
	}

	// Create new user, require name and post parameters
	userRouter.post( '/', requireParam["name"], requireParam["isFacebook"], function( req, res, next ) {
		// TODO Validate Parameters
		var newUser = new User({
			name: req.body.name
		});

		// If not facebook register, build password
		if ( !req.body.isFacebook ) {
			newUser.password = Utils.Security.encryptPassword( req.body.password );

		} else if ( !requireParam["fbAuthToken"] ) {
			// If facebook register, require fbAuthToken
			return next();
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
	userRouter.post( '/signin', requireParam["isFacebook"], function( req, res, next ) {
		// TODO: validate parameters
		User.findByName( req.body.name , function( error, existingUser ) {
			if ( error ) {
				api.ServerErrorResponse( error, res );
			 	return;
			}

		 	if ( !existingUser || ( !req.body.isFacebook && !existingUser.checkPassword( req.body.password) )
				   || ( req.body.isFacebook && existingUser.fbAuthToken != req.body["fbAuthToken"] )) {
		 	  invalidAuthResponse( res );
				return;
			}

		 	userResponse( existingUser, res );
			return next();

		});
	});

	// Link user router to application
	app.use( '/user', userRouter );

}