package com.agtools.agtools.ServiceProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HOME on 1/9/2018.
 */
public class Apicalls {

    static JSONObject responseObj;
    public static ProgressDialog progressDialog;
    static JSONArray respArray;

    public static JSONObject sendRequest(final Context context, String url, String loadMessage, JSONObject requestObj, final VolleyCallback callback) {
        showLoader(context, loadMessage);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                responseObj = response;
                callback.getResponse(responseObj);
                dismissLoader(context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissLoader(context);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

        return responseObj;
    }

    public static JSONObject getRequest(final Context context, String url, String loadMessage, final VolleyCallback callback) {
        System.out.println("call get request...............");
        showLoader(context, loadMessage);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                responseObj = response;
                callback.getResponse(responseObj);
                dismissLoader(context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error weather: " + error);
                callback.getError(error);
                dismissLoader(context);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return responseObj;
    }

    public static JSONObject getLiveWeatherRequest(final Context context, String url, final VolleyCallback callback) {
        System.out.println("urlllllllll..."+url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                responseObj = response;
                System.out.println("response weather in api calls....." + responseObj);
                callback.getResponse(responseObj);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error weather: " + error);
                callback.getError(error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return responseObj;
    }

    public static JSONArray getWeatherRequest(final Context context, String url, String loadMessage, final VolleyWatherCallBack callBack) {
        showLoader(context, loadMessage);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("weather resp:" + response);
                respArray = response;
                callBack.getResponse(respArray);
                dismissLoader(context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error weather: " + error);
                callBack.getError(error);
                dismissLoader(context);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return respArray;
    }

    public static JSONObject getSigninRequest(final Context context, String url, String loadMessage, final String username, final String password, final VolleyCallback callback) {
        showLoader(context, loadMessage);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                responseObj = response;
                callback.getResponse(response);
                dismissLoader(context);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error: " + error);
                callback.getError(error);
                dismissLoader(context);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                String encodedCredentials = Base64.encodeToString((username.toString() + ":" + password.toString()).getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + encodedCredentials);

                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return responseObj;
    }

    public interface VolleyCallback {
        public void getResponse(JSONObject response);

        public void getError(VolleyError error);
    }

    public interface VolleyWatherCallBack {
        public void getResponse(JSONArray response);

        public void getError(VolleyError error);
    }

    public static void showLoader(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public static void dismissLoader(Context context) {
        progressDialog.dismiss();
    }

    public static JSONObject googlePlacesRequest(final Context context, String url, final VolleyCallback callback) {
        System.out.println("call get google request...............");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                responseObj = response;
                System.out.println("response google....." + responseObj);
                callback.getResponse(responseObj);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print("Error weather: " + error);
                callback.getError(error);

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return responseObj;
    }

}
