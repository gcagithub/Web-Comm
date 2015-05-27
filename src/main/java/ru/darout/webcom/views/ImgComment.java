package ru.darout.webcom.views;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import org.joda.time.DateTime;

import com.mongodb.BasicDBObject;

public class ImgComment {
	private String id;
	private String hashId;
	private String imgSrc;
	private String comment;
	private String title;
	
//	@JsonFormat
//    (shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd'T'HH:mm:ss.SSSZ")
	private DateTime createdOn = new DateTime();
	
	public ImgComment(){
		
	}
	
	public ImgComment(BasicDBObject dbObj) throws UnsupportedEncodingException{
		this.id = dbObj.getObjectId("_id").toString();
		this.imgSrc = dbObj.getString("imgSrc");
		this.comment = dbObj.getString("comment");
		this.title = dbObj.getString("title");
		this.createdOn = new DateTime(dbObj.getString("createdOn", null));
//		this.hashId = URLEncoder.encode(getImgSrc(), "UTF-8");
		
	}
	
	public String getId() {
		return id;
	}

	public String getHashId() throws UnsupportedEncodingException {
		return URLEncoder.encode(getImgSrc(), "UTF-8");
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public DateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(DateTime createdOn) {
		this.createdOn = createdOn;
	}
	

}
