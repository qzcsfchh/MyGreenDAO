package com.hao.v_30.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.hao.v_30.App;
import com.hao.v_30.R;
import com.hao.v_30.adapter.UserWrapperAdapter;
import com.hao.v_30.bean.User;
import com.hao.v_30.bean.UserDao;
import com.hao.v_30.bean.UserWrapper;
import com.hao.v_30.bean.UserWrapperDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:  <br>
 * author: huanghao123
 * date: 8/22 0022-11:26
 */
public class UserWrapperFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView listUserWrapper;
    private List<UserWrapper> userWrappers;
    private UserWrapperAdapter adapter;

    private UserWrapperDao userWrapperDao;
    private Query<UserWrapper> userWrapperQuery;
    private ListView list_user_wrapper;
    private Button btn_update;

    public UserWrapperFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        return inflater.inflate(R.layout.fragment_user_wrapper, container, false);
    }

    private void initData() {
        userWrappers=new ArrayList<>();
        getUsers();
        userWrapperDao=App.getInstance().getDaoSession().getUserWrapperDao();
        userWrapperQuery=userWrapperDao.queryBuilder().build();
        userWrapperDao.insertInTx(userWrappers);
        adapter=new UserWrapperAdapter(getContext(),android.R.layout.simple_list_item_1,userWrappers);
    }

    private void getUsers() {
        userWrappers.clear();
        List<User> users = App.getInstance().getDaoSession().getUserDao().queryBuilder().orderDesc(UserDao.Properties.Id).build().list();
        for (User user : users) {
            UserWrapper wrapper=new UserWrapper();
            wrapper.setUser(user);
            wrapper.setUserId(user.getId());
            userWrappers.add(wrapper);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listUserWrapper= (ListView) view.findViewById(R.id.list_user_wrapper);
        listUserWrapper.setAdapter(adapter);
        listUserWrapper.setOnItemClickListener(this);

        btn_update= (Button) view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserWrapper wrapper = adapter.getUserWrapper().get(position);
        userWrapperDao.delete(wrapper);
        updateList();
    }

    private void updateList() {
        getUsers();
        userWrapperDao.deleteAll();
        userWrapperDao.insertInTx(userWrappers);
        List<UserWrapper> list = userWrapperQuery.list();
        adapter.setUserWrappers(list);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                updateList();
                break;
        }
    }
}
