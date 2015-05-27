package ru.darout.webcom.socials.VKcom.models;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import ru.darout.webcom.socials.VKcom.content.VkOAuthToken;
import ru.darout.webcom.socials.VKcom.helpers.HttpClientWrapper;
import ru.darout.webcom.socials.VKcom.helpers.VkUri;
import ru.darout.webcom.socials.VKcom.models.deserializers.VkAuthTokenDeserializer;
import ru.darout.webcom.util.URIUtil;
import spark.Session;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.googlecode.vkapi.HttpVkApi;
import com.googlecode.vkapi.convert.JsonConverter;
import com.googlecode.vkapi.domain.OAuthToken;
import com.googlecode.vkapi.domain.error.VkErrorResponse;
import com.googlecode.vkapi.exceptions.VkException;
import com.googlecode.vkapi.exceptions.VkExceptions;

public class VkOAuth extends HttpVkApi {

	private final String _appId;
    private final String _responseUri;
    private HttpClientWrapper _httpClient;
    private OAuthToken _vkAuth;
        
    private GsonBuilder _gsonBuilder;
	
	public VkOAuth(String appId, String appKey, String responseUri) {
		super(appId, appKey, responseUri);
		
		this._appId = appId;
        this._responseUri = responseUri;
	}
	
	public OAuthToken getVkAuth() {
		return _vkAuth;
	}

	public void setVkAuth(OAuthToken _vkOAuth) {
		this._vkAuth = _vkOAuth;
	}

	@Override
    public String getAuthUri() {
        return VkUri.authUri(_appId, APP_SCOPES, _responseUri);
    }
	
	  @Override
	  public OAuthToken authUser(String uri) throws VkException {
	        Validate.notNull(uri, "Expected code not to be null");
	        
			OAuthToken token = null;
			try {
				String decodedUri = URLDecoder.decode(uri, "UTF-8").replace("#", "?");
				Map<String, String> queryUriPairs = URIUtil.getEncordedQueryParams(decodedUri);
				token = new VkOAuthToken(queryUriPairs.get("access_token"), queryUriPairs.get("expires_in"), queryUriPairs.get("user_id"));
			} catch (DecoderException | URISyntaxException | UnsupportedEncodingException e) {
				throw new VkException(e.getMessage());
			}
			
	        return token;
	  }
	  
	  public OAuthToken authUser(String uri, Session session) throws VkException{
		  OAuthToken token = authUser(uri);
		  VkOAuthToken.putVkAuthInSession(session, token);
	      return token;
	  }
	  
	  
	  public String getJsonFriends() throws VkException {
	        String uri = VkUri.userFriendsUri(USER_FIELDS, getVkAuth());
			String json = executeAndProcess(uri);
			JsonElement items = new JsonParser().parse(json).getAsJsonObject().getAsJsonObject("response").getAsJsonArray("items");
	        
	        return items.toString();

	  }	    
	    
	  private String executeAndProcess(String uri) throws VkException {
	        String json = getHttpWrapper().executeGet(uri);

	        if (StringUtils.startsWith(json, "{\"error\":")) {
	            VkErrorResponse error = getJsonConverter().jsonToVkError(json);
	            VkExceptions.throwAppropriate(error, getVkAuth());
	        }

	        return json;
	  }
	  
	private GsonBuilder getGsonBuilder(){
		  if(_gsonBuilder == null) {
			  _gsonBuilder = new GsonBuilder()
			  .registerTypeAdapter(VkOAuthToken.class, new VkAuthTokenDeserializer());
		  }
		  return _gsonBuilder;
	  }
	  
	  
	  public JsonConverter getJsonConverter(){
		  return JsonConverter.INSTANCE;
	  }
	
	  private HttpClientWrapper getHttpWrapper(){
		  if(_httpClient == null){
			  _httpClient = new HttpClientWrapper();
		  }
		  return _httpClient; 
	  }

}
