package ru.darout.webcom.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class URIUtil {

	public static Map<String, String> getUriQueries(String decodedUri) throws DecoderException, URISyntaxException, UnsupportedEncodingException{
    	Map<String, String> queryPair = new HashMap<String, String>();
    	for(NameValuePair pair : URLEncodedUtils.parse(new URI(decodedUri), "UTF-8")){
    		queryPair.put(pair.getName(), pair.getValue());
    	}
    	return queryPair;
    }
}
