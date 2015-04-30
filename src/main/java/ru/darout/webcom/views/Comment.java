package ru.darout.webcom.views;

import java.util.Date;
import java.util.Optional;

import javax.persistence.*;

import org.bson.types.ObjectId;
import org.joda.time.LocalDate;

import com.mongodb.BasicDBObject;

public class Comment {

    private String id;
    
    private String title;
    
    private Boolean done;
    
    private LocalDate createdOn = new LocalDate();
    
    public Comment (BasicDBObject dbObject) {
//    	this.id = ((ObjectId) dbObject.get("id")).toString();
    	this.title = dbObject.getString("title");
    	this.done = dbObject.getBoolean("done");
    	Date date = Optional.ofNullable(dbObject.getDate("createdOn")).orElse(new Date());
    	this.createdOn = LocalDate.fromDateFields(date);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
    
    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }
    
    public void setCreatedOn(String createdOn) {
        this.createdOn = LocalDate.parse(createdOn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment that = (Comment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
