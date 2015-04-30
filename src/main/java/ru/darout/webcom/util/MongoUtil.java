package ru.darout.webcom.util;

import java.util.Optional;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class MongoUtil {
	
	private static final String DEFAULT_MONGO_PORT = "27017";
	
    private static final ThreadLocal<DB> DB = new ThreadLocal<DB>();
	
	public static DB getMongoDB() throws Exception {
		DB db = DB.get();
		
		if(db != null){
			return db;
		}
		
		String host = Optional.ofNullable(System.getenv("OPENSHIFT_MONGODB_DB_HOST")).orElse("localhost");
		int port = Integer.parseInt(Optional.ofNullable(System.getenv("OPENSHIFT_MONGODB_DB_PORT")).orElse(DEFAULT_MONGO_PORT));
		String dbname = System.getenv("OPENSHIFT_APP_NAME");
		String username = System.getenv("OPENSHIFT_MONGODB_DB_USERNAME");
		String password = System.getenv("OPENSHIFT_MONGODB_DB_PASSWORD");

		if ("localhost".equals(host)) {
            MongoClient mongoClient = new MongoClient(new ServerAddress(host, port));
            db = mongoClient.getDB("angspark1");
        } else {
        	MongoClientOptions mongoClientOptions = MongoClientOptions.builder().build();
        	MongoClient mongoClient = new MongoClient(new ServerAddress(host, port), mongoClientOptions);
        	mongoClient.setWriteConcern(WriteConcern.SAFE);
        	db = mongoClient.getDB(dbname);
        	
        	if (!db.authenticate(username, password.toCharArray())) {
        		throw new RuntimeException("Not able to authenticate with MongoDB");
        	}
        }
        
        DB.set(db);
        return db;
	}
}
