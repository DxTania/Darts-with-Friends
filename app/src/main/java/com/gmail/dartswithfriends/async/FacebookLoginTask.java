package com.gmail.dartswithfriends.async;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FacebookLoginTask extends AsyncTask<Void, Void, String> {
    private String mAccessToken, mFbId;

    public FacebookLoginTask(String accessToken, String fbId) {
        mAccessToken = accessToken;
        mFbId = fbId;
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://taniad.me:8000/user/facebook");

        List<NameValuePair> loginParams = new ArrayList<>();
        loginParams.add(new BasicNameValuePair("fbAccessToken", mAccessToken));
        loginParams.add(new BasicNameValuePair("fbId", mFbId));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(loginParams));
            HttpResponse response = httpClient.execute(httpPost);
            return getStringFromServerResponse(response.getEntity());
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String response) {
        Log.d("BLAH", response);
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
