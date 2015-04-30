package ru.darout.webcom.socials.VKcom.models.deserializers;

import java.lang.reflect.Type;

import ru.darout.webcom.socials.VKcom.content.VkOAuthToken;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class VkAuthTokenDeserializer implements JsonDeserializer<VkOAuthToken> {

	@Override
	public VkOAuthToken deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		
		VkOAuthToken token = new VkOAuthToken(jsonObject.get("access_token").getAsString(), jsonObject.get("expires_in").getAsInt(), -1);
		
		return token;
	}

}
