package ru.darout.webcom.socials.VKcom.controllers;

import static spark.Spark.get;
import ru.darout.webcom.controllers.AController;
import ru.darout.webcom.socials.VKcom.models.VkOAuth;


import com.googlecode.vkapi.domain.OAuthToken;

public class VkLoginRouter extends AController {
	
	private VkOAuth _vkApi;
	
	public VkLoginRouter(){
		_vkApi = new VkOAuth("4890204", "flq2uIeenodAO8anVUcj", "http://oauth.vk.com/blank.html");
	}

	@Override
	public void setupEndpoints()
	{
	  get(API_CONTEXT + "/vklogin/:uri", "application/json", (request, response) -> {
		  if(request.params(":uri").isEmpty()){
			  response.status(404);
			  return response;
		  }
		  OAuthToken auth = _vkApi.authUser(request.params(":uri"), request.session());
		  _vkApi.setVkAuth(auth);
		  return _vkApi.getJsonFriends();
      });
	  
	  get("/vklogin", "application/json", (request, response) -> {
		  response.redirect("/vkauth.html");
		  return "";
	  });
	  
	  get("/vkauth", (request, response) -> {
		  response.redirect(_vkApi.getAuthUri());
          return "";
      });
	  
	}
	
}
