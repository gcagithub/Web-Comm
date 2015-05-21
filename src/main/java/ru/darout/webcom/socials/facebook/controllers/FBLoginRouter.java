package ru.darout.webcom.socials.facebook.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import ru.darout.webcom.controllers.AController;
import ru.darout.webcom.util.URIUtil;
import spark.Request;
import static spark.Spark.*;

public class FBLoginRouter extends AController{
	public static final String FB = "FB";
	private Facebook _ff;
	public FBLoginRouter () {
		_ff = new FacebookFactory().getInstance();
	}

	@Override
	public void setupEndpoints() {
		get("/fbauth", (request, response) -> {
			String oAuthURL = _ff.getOAuthAuthorizationURL(_ff.getConfiguration().getOAuthCallbackURL());
			clearFBSession(request);
			response.redirect(oAuthURL + "&response_type=token");
			return "";
		});
		
		get(API_CONTEXT + "/fblogin/:id", "application/json", (request, response) -> {
			if("OAuthURL".equalsIgnoreCase(request.params("id"))){
				initAccess(request);
			}
			response.status(201);
			return "";
		});
	}

	private void initAccess(Request request) {
		String url = request.queryParams("url");
		Map<String, String> queries = new HashMap<String, String>();
		try {
			queries = URIUtil.getUriQueries(url.replaceFirst("#", ""));
		} catch (UnsupportedEncodingException | DecoderException
				| URISyntaxException e1) {
			System.err.println(e1);
		}
		
		Long expiresIn = Long.valueOf(queries.get("expires_in")) + new Date().getTime();
		
		AccessToken token = new AccessToken(queries.get("access_token"), expiresIn);
		_ff.setOAuthAccessToken(token);
		
		request.session().attribute(FB, _ff);
	}
	
	private void clearFBSession(Request request){
		request.session().attribute(FB, null);
	}
	
}
