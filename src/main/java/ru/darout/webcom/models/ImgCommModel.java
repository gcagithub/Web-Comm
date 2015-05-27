package ru.darout.webcom.models;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.io.JsonStringEncoder;
import org.codehaus.jackson.map.util.JSONPObject;
import org.postgresql.util.GT;

import ru.darout.webcom.util.JacksonUtil;
import ru.darout.webcom.util.URIUtil;
import ru.darout.webcom.views.Comment;
import ru.darout.webcom.views.ImgComment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ImgCommModel {
	private final DB _db;
	private final DBCollection _collection;
	
	public ImgCommModel (DB db) {
		_db = db;
		_collection = db.getCollection("web-comm-imgcomm");
	}
	
	public void createNewComment(Map<String, String> params) throws UnsupportedEncodingException, DecoderException, URISyntaxException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(params);
		ImgComment imgComm = JacksonUtil.readValue(json, ImgComment.class);
		_collection.insert(
			new BasicDBObject("imgSrc", imgComm.getImgSrc()).
			append("title", imgComm.getTitle()).
			append("comment", imgComm.getComment()).
			append("createdOn", imgComm.getCreatedOn().toString()).
			append("hashId", imgComm.getHashId())
		);
		
	}
	
	public List<ImgComment> findAll() throws UnsupportedEncodingException {
	    List<ImgComment> comments = new ArrayList<>();
        DBCursor dbObjects = _collection.find();
        dbObjects.sort(new BasicDBObject("createdOn", -1));
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            comments.add(new ImgComment((BasicDBObject) dbObject));
        }
        return comments;
    }
	
	public List<ImgComment> find(String hashId) throws UnsupportedEncodingException {
		List<ImgComment> comments = new ArrayList<>();
		DBCursor dbObjects = _collection.find(new BasicDBObject("hashId", URLEncoder.encode(hashId, "UTF-8")));
		dbObjects.sort(new BasicDBObject("createdOn", -1));
		while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            comments.add(new ImgComment((BasicDBObject) dbObject));
        }
        return comments;
	}

	public List<ImgComment> findImgStatus(List<String> vals) throws UnsupportedEncodingException {
		List<ImgComment> comments = new ArrayList<>();
		BasicDBObject inArray = new BasicDBObject("$in", vals);
		DBCursor dbObjects = _collection.find(new BasicDBObject("hashId",inArray));
		while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            comments.add(new ImgComment((BasicDBObject) dbObject));
        }
		return comments;
	}
}
