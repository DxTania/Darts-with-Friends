package com.gmail.dartswithfriends.async;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RegisterTask extends AsyncTask<Void, Void, String> {
    private String mEmail, mPassword;

    public RegisterTask(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://taniad.me:8000/user");

        JSONObject json = new JSONObject();
        try {
            json.put("email", mEmail);
            json.put("password", mPassword);
            StringEntity login = new StringEntity(json.toString());
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(login);
            HttpResponse response = httpClient.execute(httpPost);
            return getStringFromServerResponse(response.getEntity());
        } catch (IOException e) {
            return null;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        Log.d("BLAH", "BLAH" + response);
    }

    public String getStringFromServerResponse(HttpEntity entity) {
        try {
            if (entity != null) {
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                StringBuilder builder = new StringBuilder();

                String st;
                while ((st = reader.readLine()) != null) {
                    builder.append(st).append("\n");
                }

                instream.close();
                return builder.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
}
