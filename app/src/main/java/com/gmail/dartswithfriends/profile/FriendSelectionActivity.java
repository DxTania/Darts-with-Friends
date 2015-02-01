package com.gmail.dartswithfriends.profile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.gmail.dartswithfriends.R;

import org.json.JSONException;

public class FriendSelectionActivity extends ActionBarActivity {
    private static final String TAG = "FriendSelectionActivity";
    private FriendsListAdapter adapter;
    private ListView friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_friend_selection);

        friends = (ListView) findViewById(R.id.friendList);

        new Request(
                Session.getActiveSession(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
                        try {
                            onFriendsListRetrieved(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }

    private void onFriendsListRetrieved(Response response) throws JSONException {
        if (response != null) {
            GraphObjectList<GraphUser> data = response.getGraphObject().getPropertyAsList("data", GraphUser.class);
            Log.d(TAG, data.toString());
            adapter = new FriendsListAdapter(FriendSelectionActivity.this, data);
            friends.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Couldn't retrieve friends list", Toast.LENGTH_SHORT).show();
        }
    }
}
