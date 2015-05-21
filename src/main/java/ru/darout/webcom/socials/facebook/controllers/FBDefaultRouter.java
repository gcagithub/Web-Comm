package ru.darout.webcom.socials.facebook.controllers;

import java.util.HashMap;
import java.util.Map;


import facebook4j.Facebook;
import facebook4j.internal.org.json.JSONObject;
import ru.darout.webcom.controllers.AController;
import spark.Request;
import static spark.Spark.*;

public class FBDefaultRouter extends AController {
	
	public FBDefaultRouter(){
	}

	@Override
	public void setupEndpoints() {
		get(API_CONTEXT + "/fbsocial/:id", "application/json", (request, response) -> {
			if("init".equalsIgnoreCase(request.params("id"))){
				if(!isFBSessionValid(request)){
					response.status(401);
					return "";
				}
			}
			response.status(201);
			
			Map<String, String> data = new HashMap<String, String>();
			data.put("fb", "active");
			JSONObject result = new JSONObject().put("result", data);
			return result;
		});
	}
	
	private boolean isFBSessionValid(Request request){
		Facebook fb = (Facebook) request.session().attribute(FBLoginRouter.FB);
		
//		if(fb == null || fb.getOAuthAccessToken().getExpires() <= (new Date()).getTime()){
//			return false;
//		}
		
		if(fb == null){
			return false;
		}
		return true;
	}

}
