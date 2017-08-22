package com.hao.v_30.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hao.v_30.App;
import com.hao.v_30.R;
import com.hao.v_30.adapter.UserAdapter;
import com.hao.v_30.bean.User;
import com.hao.v_30.bean.UserDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * desc:  greenDao的基本使用 <br>
 * author: huanghao123
 * date: 8/22 0022-11:13
 */
public class UserFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = "UserFragment";
    private UserDao userDao;   //用于数据增删改
    private Query<User> userQuery;  //用于数据查询

    private EditText name;
    private EditText age;
    private EditText key;
    private ListView list_view;
    private Button btn_submit;
    private UserAdapter adapter;
    private List<User> users;

    public UserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initData() {
        users=new ArrayList<>();
        userDao = App.getInstance().getDaoSession().getUserDao();
        userQuery = userDao.queryBuilder().orderAsc(UserDao.Properties.Age).build();
        users.addAll(userQuery.list());

        adapter=new UserAdapter(getContext(),android.R.layout.simple_list_item_1,users);
    }

    private void initView(View view) {
        name = (EditText) view.findViewById(R.id.name);
        age = (EditText) view.findViewById(R.id.age);
        key = (EditText) view.findViewById(R.id.key);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        list_view= (ListView) view.findViewById(R.id.list_view);
        btn_submit.setOnClickListener(this);

        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(this);
        updateListData();
    }

    private void updateListData(){
        adapter.setUsers(userQuery.list());
        Log.d(TAG, "updateListData: user.size()="+users.size());
        adapter.notifyDataSetChanged();
    }

    private void insertUser(User user){
        try {
            long l = userDao.insert(user);
            if (l>=0) {
                Toast.makeText(getContext(),"insert success!",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof net.sqlcipher.database.SQLiteConstraintException) {
                Toast.makeText(getContext(),"insert failed! key already exists!",Toast.LENGTH_SHORT).show();
            }
        }
        Log.d(TAG, "Inserted new user, ID: " + user.getId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String nameString = name.getText().toString().trim();
        if (TextUtils.isEmpty(nameString)) {
            Toast.makeText(getContext(), "name", Toast.LENGTH_SHORT).show();
            return;
        }

        String ageString = age.getText().toString().trim();
        if (TextUtils.isEmpty(ageString)) {
            Toast.makeText(getContext(), "age", Toast.LENGTH_SHORT).show();
            return;
        }

        String keyString = key.getText().toString().trim();
        if (TextUtils.isEmpty(keyString)) {
            Toast.makeText(getContext(), "key", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        User user=new User();
        user.setName(nameString);
        user.setAge(Integer.valueOf(ageString));
        user.setKey(keyString);
        user.setDate(new Date());
        insertUser(user);
        updateListData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = adapter.getUsers().get(position);
        userDao.delete(user);
        updateListData();
    }
}
