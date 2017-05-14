package com.quickindex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Friend> mFriendList;

    public MyAdapter(List<Friend> friendList, Context context) {
        mFriendList = friendList;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Friend friend = mFriendList.get(position);
        viewHolder.mName.setText(friend.getName());
        String text = "";
        if (position > 0) {
            Friend preFriend = mFriendList.get(position - 1);
            char preFriendFirstLetter = preFriend.getPinYin().charAt(0);
            char thisFriendFirstLetter = friend.getPinYin().charAt(0);
            if (thisFriendFirstLetter != preFriendFirstLetter) {
                text = thisFriendFirstLetter + "";
            }
        } else {
            text = friend.getPinYin().charAt(0) + "";
        }
        viewHolder.mIndex.setText(text);
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mIndex;
        private TextView mName;
        private CircleImageView mHeader;

        public ViewHolder(View itemView) {
            super(itemView);
            mIndex = (TextView) itemView.findViewById(R.id.index);
            mHeader = (CircleImageView) itemView.findViewById(R.id.header);
            mName = (TextView) itemView.findViewById(R.id.name);
        }
    }
}