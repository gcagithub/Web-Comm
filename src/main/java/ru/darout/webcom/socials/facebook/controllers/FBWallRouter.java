package ru.darout.webcom.socials.facebook.controllers;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;

import facebook4j.Facebook;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.internal.org.json.JSONObject;
import ru.darout.webcom.controllers.AController;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class FBWallRouter extends AController {
	
	private Facebook _fb;
	
	public FBWallRouter(){
	}

	@Override
	public void setupEndpoints() {
		post(API_CONTEXT + "/fbwall/:id", "application/json", (request, response) -> {
			if("wallMessage".equalsIgnoreCase(request.params("id"))){
				JSONObject json = new JSONObject(request.body());
				if(getFBFromSession(request, response) == null) return "";
				try {
					getFBFromSession(request, response).
						postStatusMessage(URLDecoder.decode(json.getString("message"), "UTF-8"));
				} catch (Exception e) {
					response.status(404);
					System.err.println(e);
				}
			}
			response.status(201);
			return "";
		});
		
		get(API_CONTEXT + "/fbwall/:id", "application/json", (request, response) -> {
			if("lastPostMessages".equalsIgnoreCase(request.params("id"))){
				if(getFBFromSession(request, response) == null) return "";
				
				String limit = request.queryParams("limit");
				ResponseList<Post> posts = getFBFromSession(request, response).
						getStatuses(new Reading().limit(Integer.valueOf(limit)));
				
//				Map<Long, String> timeMessage = new HashMap<Long, String>();
				List<String> messages = new ArrayList<String>();
				for(Post post : posts){
					messages.add(post.getMessage());
				}
				
				JSONObject result = new JSONObject();
				result.put("result", messages);
				
				return result;
				
			}
			return "";
		});


	}
	
	private Facebook getFBFromSession(Request request, Response response){
		if(_fb == null){
			_fb = (Facebook) request.session().attribute(FBLoginRouter.FB);
			checkAccessability(_fb, response);
		}
		return _fb;
	}

	private void checkAccessability(Facebook fb, Response response) {
		if (fb == null) {
			response.status(401);
		}
	}

}
