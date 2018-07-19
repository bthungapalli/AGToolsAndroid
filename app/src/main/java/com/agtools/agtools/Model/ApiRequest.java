package com.agtools.agtools.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HOME on 1/9/2018.
 */
public class ApiRequest {

    public static JSONObject signinRequest(String username, String password){
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("username",username);
            jsonObject.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
