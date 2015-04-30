package ru.darout.webcom.socials.VKcom.models.deserializers;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;

import ru.darout.webcom.socials.VKcom.content.VkWallMessage;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class VkWallMessageDeserializer implements JsonDeserializer<VkWallMessage> {

	@Override
	public VkWallMessage deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String message = jsonObject.get("vkMessage").getAsString();
		
		return new VkWallMessage(message);
	}

}
