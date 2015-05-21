package ru.darout.webcom.socials.VKcom.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;


import com.googlecode.vkapi.domain.OAuthToken;


/**
 * See Vk.com API by http://vk.com/dev/wall.get
 *
 */
public class VkUri{
	public static final String METHOD_URI = "https://api.vk.com/method/";
	
	/**
     * Generates uri for sending authorization requests
     * 
     * @param appId id of the application
     * @param scopes needed functions (such as "friends", "messages", etc)
     * @param responseUri the uri to which the response will be sent
     * @return uri to be shown to the user to authorization
     */
    public static String authUri(String appId, String[] scopes, String responseUri) {
        return "http://oauth.vk.com/authorize?" + 
                "client_id=" + appId + "&" + 
                "scope=" + StringUtils.join(scopes, ",") + "&" + 
                "redirect_uri=" + responseUri + "&" +
                "v=5.30&" +
                "response_type=token";
    }
    
    public static StringBuilder wallPost(OAuthToken authToken){
    	StringBuilder result = new StringBuilder(METHOD_URI);
    	result.append("wall.post?");
    	result.append("user_id=").append(authToken.getUserId()).append("&");
    	result.append("v=5.30&");
    	result.append("access_token=").append(authToken.getAccessToken()).append("&");
    	return result;
    }
    
    public static StringBuilder lastWallPost(OAuthToken authToken){
    	StringBuilder result = new StringBuilder(METHOD_URI);
    	result.append("wall.get?");
    	result.append("owner_id=").append(authToken.getUserId()).append("&");
    	result.append("v=5.30&");
    	result.append("count=1&");
    	return result;
    }

    public static String accessTokenUri(String appId, String appKey, String code) {
        return "https://oauth.vk.com/access_token?client_id=" + appId +
        		"&client_secret=" + appKey +
        		"&code=" + code +
        		 "&v=5.30&grant_type=client_credentials";
    }

    public static String userInfoUri(int vkUserId, String[] fields, OAuthToken authToken) {
        return METHOD_URI + "users.get?" + 
                "uids=" + vkUserId + "&" + 
                "fields=" + StringUtils.join(fields, ",") + "&" + 
                "access_token=" + authToken.getAccessToken();
    }

    public static String userFriendsUri(String[] fields, OAuthToken authToken) {
        return METHOD_URI + "friends.get?" + 
        		"v=5.30&uid=" + authToken.getUserId() + "&" +
        		"count=10&" + 
                "fields=" + StringUtils.join(fields, ",") + "&" + 
                "access_token=" + authToken.getAccessToken();
    }

}

