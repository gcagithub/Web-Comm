package ru.darout.webcom;

import static spark.Spark.get;
import static spark.SparkBase.stop;

import java.util.Optional;

import ru.darout.webcom.controllers.CommentRouter;
import ru.darout.webcom.models.Commentator;
import ru.darout.webcom.socials.VKcom.controllers.VkLoginRouter;
import ru.darout.webcom.socials.VKcom.controllers.VkWallRouter;
import ru.darout.webcom.util.MongoUtil;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class AppInit {
	private static final String IP_ADDRESS = Optional.ofNullable(System.getenv("OPENSHIFT_DIY_IP")).orElse("localhost");
    private static final String PORT = Optional.ofNullable(System.getenv("OPENSHIFT_DIY_PORT")).orElse("8080") ;
    
    protected void init() throws Exception {
    	catchExeption();
    	
    	Spark.ipAddress(IP_ADDRESS);
    	Spark.port(Integer.valueOf(PORT));
    	Spark.externalStaticFileLocation("public");
    	
    	initControllers();
    }

	private void catchExeption() {
		Spark.exception(Exception.class, (e, request, response) -> {
		      response.status(500);
		});
		
	}

	private void initControllers() throws Exception {
		simpleController();
		
		// Comment's resource
		new CommentRouter(new Commentator(MongoUtil.getMongoDB()));
		// Vk.com login resource
		new VkLoginRouter().setupEndpoints();
		// Vk.com wall resource
		new VkWallRouter().setupEndpoints();
		
	}

	private void simpleController() {
        get("/stopApp", (request, response) -> {
            stop();
            return "";
        });

        get("/", (request, response) -> {
            response.redirect("/index.html");
            return "";
        });
        
        get("/sayHello", new Route(){
        	@Override
        	public Object handle(Request request, Response response){
        		return "Hello World!";
        	}
        });
	}
}
