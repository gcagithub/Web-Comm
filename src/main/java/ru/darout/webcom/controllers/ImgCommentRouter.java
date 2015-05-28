package ru.darout.webcom.controllers;

import java.util.HashMap;
import java.util.Map;

import ru.darout.webcom.models.ImgCommModel;
import ru.darout.webcom.util.JsonTransformer;
import ru.darout.webcom.util.URIUtil;
import spark.Spark;

public class ImgCommentRouter extends AController {
	
	private final ImgCommModel _imgComm;
	
	public ImgCommentRouter (ImgCommModel model) {
		_imgComm = model;
		setupEndpoints();
	}
	
	@Override
	public void setupEndpoints() {
		Spark.post(WEB_COMM_CONTEXT + "/postImgComm", "application/json", (request, response) -> {
			Map<String, String[]> params = request.queryMap().toMap();
			Map<String, String> convertedParams = new HashMap<String, String>();
			params.forEach((k,v) -> convertedParams.put(k, v[0]));
			
			_imgComm.createNewComment(convertedParams);
            response.status(201);
            return "";
        });
		
		Spark.get(WEB_COMM_CONTEXT + "/getAllImgComm", "application/json", (request, response) -> {
			return _imgComm.findAll();
		}, new JsonTransformer());
		
		Spark.get(WEB_COMM_CONTEXT + "/getImgComm", "application/json", (request, response) -> {
			return _imgComm.find(request.queryParams("hashId"));
		}, new JsonTransformer());

		Spark.get(WEB_COMM_CONTEXT + "/getAllImgStatus", "application/json", (request, response) -> {
			return _imgComm.findImgStatus(URIUtil.getQueryValues(request.queryString()));
		}, new JsonTransformer());
	}

}
