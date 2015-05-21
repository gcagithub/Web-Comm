package ru.darout.webcom.socials.twiiter.controllers;

import static spark.Spark.get;

import ru.darout.webcom.controllers.AController;
import spark.Request;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TWLoginRouter extends AController {
	
	private Twitter _tw;
	public static final String TW = "TW"; 
	
	public TWLoginRouter(){
		_tw = new TwitterFactory().getInstance();
	}

	@Override
	public void setupEndpoints() {
		get("/twauth", (request, response) -> {
			if(!_tw.getAuthorization().isEnabled()){
				RequestToken reqToken = _tw.getOAuthRequestToken();
				String redirectURL = reqToken.getAuthenticationURL();
				response.redirect(redirectURL);
			}
//			clearTWSession(request);
			return "Access token already available.";
		});
		
		get(API_CONTEXT + "/twlogin/:id", "application/json", (request, response) -> {
			if("OAuthPIN".equalsIgnoreCase(request.params("id"))){
				initAccessibility(request);
			}
			response.status(201);
			return "";
		});
	}
	
	private void initAccessibility(Request request) {
		if(_tw.getAuthorization().isEnabled()){
			return;
		}
		
		String pin = request.queryParams("pin");
		try {
			AccessToken token = _tw.getOAuthAccessToken(pin);
			_tw.setOAuthAccessToken(token);
		} catch (TwitterException e) {
			System.err.println(e);
		}
		
		saveTWInSession(request);
	}
	
	private void saveTWInSession(Request request) {
		request.session().attribute(TW, _tw);
	}
	
	private void clearTWSession(Request request){
		request.session().attribute(TW, null);
	}

}
