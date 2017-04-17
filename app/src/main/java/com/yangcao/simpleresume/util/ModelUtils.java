package com.yangcao.simpleresume.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Rainie on 4/6/17.
 */

//用来把data model写入sharedPreference或从sharedPreference中读取
public class ModelUtils {
    private static Gson gson = new Gson();
    private static String PREF_NAME = "model";

    //write to sp
    public static void saveModel(@NonNull Context context, String key, Object model) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, gson.toJson(model)).apply();
    }

    //read from sp. generic!!
    public static <T> T readModel(@NonNull Context context, String key, TypeToken<T> typeToken) {
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonString = sp.getString(key, "");
        try {
            return gson.fromJson(jsonString, typeToken.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
