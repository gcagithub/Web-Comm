package ru.darout.webcom.socials.VKcom.models;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import ru.darout.webcom.socials.VKcom.content.VkOAuthToken;
import ru.darout.webcom.socials.VKcom.content.VkWallMessage;
import ru.darout.webcom.socials.VKcom.helpers.HttpClientWrapper;
import ru.darout.webcom.socials.VKcom.helpers.VkUri;
import ru.darout.webcom.socials.VKcom.models.deserializers.VkWallMessageDeserializer;
import spark.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.vkapi.domain.OAuthToken;

public class VkWallMessanger {
	private GsonBuilder _gsonBuilder;
	private OAuthToken _authToken;
	private HttpClientWrapper _httpClient;
	
	public VkWallMessanger(){
	}

	public void openAccess(Session _session) {
		_authToken = VkOAuthToken.getVkAuthToken(_session);
	}
	
	public void createNewMessage(String body) {
		Gson gb = getGsonBuilder().create();
		VkWallMessage message = gb.fromJson(body, VkWallMessage.class);
		
		StringBuilder uriBuilder = VkUri.wallPost(_authToken);
		uriBuilder.append("message=").append(message.getMessage()).append("&");
		
		String json = getAnswerByUri(uriBuilder.toString());
		System.err.println("json: " + json);
	}
	
	public String getLastWallMessage(){
		return getAnswerByUri(VkUri.lastWallPost(_authToken).toString());
	}
	
	private String getAnswerByUri(String uri) {
		if(_httpClient == null){
			_httpClient = new HttpClientWrapper();
		}
		return _httpClient.executeGet(uri);
	}

	private GsonBuilder getGsonBuilder(){
		if(_gsonBuilder == null) {
			_gsonBuilder = new GsonBuilder()
			.registerTypeAdapter(VkWallMessage.class, new VkWallMessageDeserializer());
		}
		return _gsonBuilder;
	}

}
