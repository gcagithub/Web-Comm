package ru.darout.webcom.util;


import com.google.gson.Gson;

import spark.ResponseTransformer;

public class GsonTransformer implements ResponseTransformer {

	private Gson gson = new Gson();
	
    @Override
    public String render(final Object model) throws Exception {
    	return gson.toJson(model);
    }
}
