package ru.darout.webcom.controllers;

import ru.darout.webcom.models.Commentator;
import ru.darout.webcom.util.JsonTransformer;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

public class CommentRouter {
//	private static final String API_CONTEXT = "/api/v1";
	private static final String API_CONTEXT = "angspark1";
	 
    private final Commentator commentService;
 
    public CommentRouter(Commentator commentService) {
        this.commentService = commentService;
        setupEndpoints();
    }
 
    private void setupEndpoints() {
        post(API_CONTEXT + "/comments", "application/json", (request, response) -> {
            commentService.createNewComment(request.body());
            response.status(201);
            return response;
        }, new JsonTransformer());
 
        get(API_CONTEXT + "/comments/:id", "application/json", (request, response)
 
                -> commentService.find(request.params(":id")), new JsonTransformer());
 
        get(API_CONTEXT + "/comments", "application/json", (request, response)
 
                -> commentService.findAll(), new JsonTransformer());
 
        put(API_CONTEXT + "/comments/:id", "application/json", (request, response)
 
                -> commentService.update(request.params(":id"), request.body()), new JsonTransformer());
    }
}
