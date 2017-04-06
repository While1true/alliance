package util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * 描述：Gson封装
 */
public class GSONUtils {
    private static final String TAG = GSONUtils.class.getSimpleName();

    public static Gson gson = new Gson();

    public static <T> T parseJson(Class<T> cls, String json) {
        try {
            return gson.fromJson(json, cls);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    public static <T> T parseJson(Type type, String json) {
        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static String toJson(Object src) {
        try {
            return gson.toJson(src);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
//
}
