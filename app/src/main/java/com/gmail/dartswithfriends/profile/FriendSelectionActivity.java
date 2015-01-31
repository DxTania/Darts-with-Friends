package com.gmail.dartswithfriends.profile;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.gmail.dartswithfriends.R;

import org.json.JSONException;

public class FriendSelectionActivity extends ActionBarActivity {
    private ListAdapter adapter;
    private ListView friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_friend_selection);

        friends = (ListView) findViewById(R.id.friendList);

        // TODO: get tylor to enable so i actually get a response
        new Request(
                Session.getActiveSession(),
                "/me/friends",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
                        Object blah = response.getGraphObject().getProperty("data");
                        try {
                            adapter = new FriendsListAdapter(response.getGraphObject().getInnerJSONObject().getJSONArray("data"));
                            friends.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
}
