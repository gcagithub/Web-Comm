package ru.darout.webcom.util;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.codec.DecoderException;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class URIUtil {

	public static Map<String, String> getEncordedQueryParams(String decodedUri) throws DecoderException, URISyntaxException, UnsupportedEncodingException{
    	Map<String, String> queryPair = new HashMap<String, String>();
    	for(NameValuePair pair : URLEncodedUtils.parse(new URI(decodedUri), "UTF-8")){
    		queryPair.put(pair.getName(), pair.getValue());
    	}
    	return queryPair;
    }
	
	String test = "imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0091.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0092.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0093.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0094.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0095.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0096.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0097.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0098.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0099.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2F2015%2F02%2Fbook-0100.jpg&imgSrcs%5B%5D=http%3A%2F%2Feax.me%2Ffiles%2Fcounter.gif";
	
	public static List<String> getQueryValues(String strQuery){
		List<String> result = new ArrayList<>(); 
		StringTokenizer tokens = new StringTokenizer(strQuery, "&");
		String token; 
		while (tokens.hasMoreElements()) {
			token = (String) tokens.nextElement();
			result.add(token.substring(token.indexOf("=") + 1));
		}
		return result;
	}
}
