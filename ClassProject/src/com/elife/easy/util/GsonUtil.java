package com.elife.easy.util;


import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonUtil {
	public static <T> T jsonToModel(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
	
	public static <T> List<T> jsonToList(String json, Class<T> clazz) {
		
		Type type = new TypeToken<List<T>>(){}.getType();
		Gson gson = new Gson();
		List<T> list = gson.fromJson(json, type);
		return list;
		
	}
	
}
