package ru.darout.webcom.socials.twiiter.controllers;

import static spark.Spark.get;
import static spark.Spark.post;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import ru.darout.webcom.controllers.AController;
import spark.Request;
import spark.Response;
import twitter4j.JSONObject;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;

public class TWTwittRouter extends AController {
	
	private Twitter _tw;

	@Override
	public void setupEndpoints() {
		post(API_CONTEXT + "/twtweet/:id", "application/json", (request, response) -> {
			if("twMessage".equalsIgnoreCase(request.params("id"))){
				JSONObject json = new JSONObject(request.body());
				Twitter tw = getTWFromSession(request, response);
				if(tw == null){
					response.status(401);
					return "";
				}
				try {
					tw.updateStatus(URLDecoder.decode(json.getString("message"), "UTF-8"));
				} catch (Exception e) {
					response.status(404);
					System.err.println(e);
				}
			}
			response.status(201);
			return "";
		});
		
		get(API_CONTEXT + "/twtweet/:id", "application/json", (request, response) -> {
			if("lastTwittes".equalsIgnoreCase(request.params("id"))){
				Twitter tw = getTWFromSession(request, response);
				if(tw == null){
					response.status(401);
					return "";	
				}
				
				ResponseList<Status> statuses = tw.getUserTimeline();
				
				List<String> messages = new ArrayList<String>();
				for(Status status : statuses){
					messages.add(status.getText());
				}
				
				JSONObject result = new JSONObject();
				result.put("result", messages);
				
				return result;
				
			}
			return "";
		});

	}
	
	private Twitter getTWFromSession(Request request, Response response){
		if(_tw == null){
			_tw = (Twitter) request.session().attribute(TWLoginRouter.TW);
			checkAccessability(_tw, response);
		}
		return _tw;
	}

	private void checkAccessability(Twitter tw, Response response) {
		if (tw == null) {
			response.status(401);
		}
	}

}
