package ru.darout.webcom.models;

import com.google.gson.Gson;
import com.mongodb.*;

import org.bson.types.ObjectId;
 


import ru.darout.webcom.views.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Commentator {
	private final DB db;
    private final DBCollection collection;
 
    public Commentator(DB db) {
        this.db = db;
        this.collection = db.getCollection("comments");
    }
 
    public List<Comment> findAll() {
        List<Comment> comments = new ArrayList<>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            comments.add(new Comment((BasicDBObject) dbObject));
        }
        return comments;
    }
 
    public void createNewComment(String body) {
        Comment comment = new Gson().fromJson(body, Comment.class);
        collection.insert(new BasicDBObject("title", comment.getTitle())
        .append("done", comment.isDone()).append("createdOn", comment.getCreatedOn()));
    }
 
    public Comment find(String id) {
        return new Comment((BasicDBObject) collection.findOne(new BasicDBObject("id", new ObjectId(id))));
    }
 
    public Comment update(String commentId, String body) {
        Comment comment = new Gson().fromJson(body, Comment.class);
        collection.update(new BasicDBObject("id", new ObjectId(commentId)), new BasicDBObject("$set", new BasicDBObject("done", comment.isDone())));
        return this.find(commentId);
    }
}
