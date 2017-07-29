package com.hao.v_30;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hao.v_30.bean.User;
import com.hao.v_30.bean.UserDao;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private UserDao userDao;
    private Query<User> userQuery;
    private EditText name;
    private EditText age;
    private EditText key;
    private ListView list_view;
    private Button btn_submit;
    private UserAdapter adapter;
    private List<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        users=new ArrayList<>();
        userDao = App.getInstance().getDaoSession().getUserDao();
        userQuery = userDao.queryBuilder().orderAsc(UserDao.Properties.Age).build();
        users.addAll(userQuery.list());
        adapter=new UserAdapter(this,android.R.layout.simple_list_item_1,users);
    }

    private void initView() {
        name = (EditText) findViewById(R.id.name);
        age = (EditText) findViewById(R.id.age);
        key = (EditText) findViewById(R.id.key);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        list_view= (ListView) findViewById(R.id.list_view);
        btn_submit.setOnClickListener(this);

        list_view.setAdapter(adapter);
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
                Toast.makeText(this,"insert success!",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof net.sqlcipher.database.SQLiteConstraintException) {
                Toast.makeText(this,"insert failed! key already exists!",Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "name", Toast.LENGTH_SHORT).show();
            return;
        }

        String ageString = age.getText().toString().trim();
        if (TextUtils.isEmpty(ageString)) {
            Toast.makeText(this, "age", Toast.LENGTH_SHORT).show();
            return;
        }

        String keyString = key.getText().toString().trim();
        if (TextUtils.isEmpty(keyString)) {
            Toast.makeText(this, "key", Toast.LENGTH_SHORT).show();
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
}
