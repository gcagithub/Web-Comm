package ru.darout.webcom.socials.VKcom.content;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import spark.Request;
import spark.Session;

import com.google.gson.annotations.SerializedName;
import com.googlecode.vkapi.domain.OAuthToken;

public class VkOAuthToken implements OAuthToken {
	
	public static final String VK_OAUTH = "VK_OAUTH";
	
	@SerializedName("access_token")
    private String accessToken;
    private Date expiresIn;
    private int userId;

    public VkOAuthToken(String accessToken, int expiresIn, int userId) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.expiresIn = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn));
    }
    
    public VkOAuthToken(String accessToken, String expiresIn, String userId) {
    	this(accessToken, Integer.valueOf(expiresIn), Integer.valueOf(userId));
    }
    
    public static void putVkAuthInSession(Session session, OAuthToken auth){
		session.attribute(VkOAuthToken.VK_OAUTH, auth);
	}
	public static OAuthToken getVkAuthToken(Session session){
		return session.attribute(VkOAuthToken.VK_OAUTH);
	}

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public boolean isExpired() {
        return System.currentTimeMillis() > expiresIn.getTime();
    }
    
    @Override
    public Date getExpirationMoment() {
        return new Date(expiresIn.getTime());
    }

    @Override
    public String toString() {
        return "VkOAuthToken [accessToken=" + accessToken + ", expirationMoment=" + expiresIn + ", userId="
                + userId + "]";
    }

}

