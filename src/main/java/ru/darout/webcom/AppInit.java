package ru.darout.webcom;

import static spark.Spark.*;
import static spark.SparkBase.stop;

import java.util.Optional;

import org.eclipse.jetty.server.session.SessionHandler;

import ru.darout.webcom.controllers.CommentRouter;
import ru.darout.webcom.models.Commentator;
import ru.darout.webcom.socials.VKcom.controllers.VkLoginRouter;
import ru.darout.webcom.socials.VKcom.controllers.VkWallRouter;
import ru.darout.webcom.socials.facebook.controllers.FBDefaultRouter;
import ru.darout.webcom.socials.facebook.controllers.FBLoginRouter;
import ru.darout.webcom.socials.facebook.controllers.FBWallRouter;
import ru.darout.webcom.socials.twiiter.controllers.TWDefaultRouter;
import ru.darout.webcom.socials.twiiter.controllers.TWLoginRouter;
import ru.darout.webcom.socials.twiiter.controllers.TWTwittRouter;
import ru.darout.webcom.util.MongoUtil;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;
import spark.Spark;
import spark.SparkBase;
import spark.utils.SparkUtils;
import spark.webserver.SparkServer;

public class AppInit {
	private static final String IP_ADDRESS = Optional.ofNullable(System.getenv("OPENSHIFT_DIY_IP")).orElse("localhost");
    private static final String PORT = Optional.ofNullable(System.getenv("OPENSHIFT_DIY_PORT")).orElse("8080") ;
    
    private Session _session;
    
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
		      System.err.println(e);
		});
		
	}

	private void initControllers() throws Exception {
		before((request, response) -> {
			_session = request.session();
		});
		
		simpleController();
		
		// Comment's resource
		new CommentRouter(new Commentator(MongoUtil.getMongoDB()));
		// Vk.com login resource
		new VkLoginRouter().setupEndpoints();
		// Vk.com wall resource
		new VkWallRouter().setupEndpoints();
		// FB login resource
		new FBLoginRouter().setupEndpoints();
		new FBDefaultRouter().setupEndpoints();
		new FBWallRouter().setupEndpoints();
		
		new TWLoginRouter().setupEndpoints();
		new TWDefaultRouter().setupEndpoints();
		new TWTwittRouter().setupEndpoints();
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
