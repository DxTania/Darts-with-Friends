package com.gmail.dartswithfriends.profile;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.gmail.dartswithfriends.R;


public class FriendsListAdapter implements ListAdapter {
    private static final String TAG = "FriendsListAdapter";
    private GraphObjectList<GraphUser> mFriends;
    private Context mContext;

    FriendsListAdapter(Context context, GraphObjectList<GraphUser> jFriends) {
        mFriends = jFriends;
        mContext = context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mFriends.size();
    }

    @Override
    public GraphUser getItem(int position) {
        return mFriends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.friend_view, parent, false);
            viewHolder.friendName = (TextView) convertView.findViewById(R.id.friend_name);
            viewHolder.friendImage = (ProfilePictureView) convertView.findViewById(R.id.friend_picture);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GraphUser friend = mFriends.get(position);
        viewHolder.friendName.setText(friend.getName());
        viewHolder.friendImage.setProfileId(friend.getId());

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    static class ViewHolder {
        ProfilePictureView friendImage;
        TextView friendName;
    }
}
