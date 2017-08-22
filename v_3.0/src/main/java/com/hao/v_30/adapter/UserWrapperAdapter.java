package com.hao.v_30.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hao.v_30.bean.UserWrapper;

import java.util.List;

/**
 * Desc:
 * Created by huanghao123 on 7/18 0018.
 */
public class UserWrapperAdapter extends BaseAdapter {
    private static final String TAG = "UserAdapter";
    private LayoutInflater mInflater;
    private int mLayoutId;
    private List<UserWrapper> mUsers;

    public void setUserWrappers(List<UserWrapper> users) {
        this.mUsers = users;
    }

    public List<UserWrapper> getUserWrapper(){
        return mUsers;
    }

    public UserWrapperAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<UserWrapper> objects) {
        mInflater=LayoutInflater.from(context);
        mLayoutId=resource;
        mUsers=objects;
    }

    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            holder=new ViewHolder();
            convertView=mInflater.inflate(mLayoutId,parent,false);
            holder.text1= (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        UserWrapper user = mUsers.get(position);
        StringBuilder sb=new StringBuilder();
        sb.append("id="+user.getId()+"\n")
                .append("userId="+user.getUserId()+"\n")
                .append("age="+user.getUser().getAge()+"\n")
                .append("key="+user.getUser().getKey()+"\n")
                .append(user.getUser().getDate().toLocaleString());
        holder.text1.setText(sb.toString());
        return convertView;
    }



    class ViewHolder{
        TextView text1;
    }
}
