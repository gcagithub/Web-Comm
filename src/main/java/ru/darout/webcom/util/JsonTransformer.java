package ru.darout.webcom.util;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {

    @Override
    public String render(final Object model) throws Exception {
    	return JacksonUtil.newObjectMapper().writeValueAsString(model);
    }
}
