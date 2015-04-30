package ru.darout.webcom.socials.VKcom.controllers;

import ru.darout.webcom.controllers.*;
import ru.darout.webcom.socials.VKcom.models.VkWallMessanger;
import spark.Spark;

public class VkWallRouter extends AController{
	
	private VkWallMessanger _wallRouter;
	
	public VkWallRouter() {
		_wallRouter = new VkWallMessanger();
	}

	@Override
	public void setupEndpoints() {

		Spark.post(API_CONTEXT + "/vkMessage/:id", "application/json", (request, response) -> {
			_wallRouter.openAccess(request.session());
			_wallRouter.createNewMessage(request.body());
			response.status(201);
            return response;
		});
		
		Spark.get(API_CONTEXT + "/vkMessage/:id", "application/json", (request, response) -> {
			_wallRouter.openAccess(request.session());
			return _wallRouter.getLastWallMessage();
		});
		
	}

}
