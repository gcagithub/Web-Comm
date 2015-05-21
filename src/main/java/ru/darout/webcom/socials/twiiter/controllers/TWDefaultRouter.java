package ru.darout.webcom.socials.twiiter.controllers;

import static spark.Spark.get;

import java.util.HashMap;
import java.util.Map;

import facebook4j.internal.org.json.JSONObject;
import ru.darout.webcom.controllers.AController;
import spark.Request;
import twitter4j.Twitter;

public class TWDefaultRouter extends AController {

	@Override
	public void setupEndpoints() {
		get(API_CONTEXT + "/twsocial/:id", "application/json", (request, response) -> {
			if("init".equalsIgnoreCase(request.params("id"))){
				if(!isTWSessionValid(request)){
					response.status(401);
					return "";
				}
			}
			response.status(201);
			
			Map<String, String> data = new HashMap<String, String>();
			data.put("tw", "active");
			JSONObject result = new JSONObject().put("result", data);
			return result;
		});

	}
	
	private boolean isTWSessionValid(Request request){
		Twitter tw = (Twitter) request.session().attribute(TWLoginRouter.TW);
		
//		if(fb == null || fb.getOAuthAccessToken().getExpires() <= (new Date()).getTime()){
//			return false;
//		}
		
		if(tw == null){
			return false;
		}
		return true;
	}


}
