package com.hao.v_30;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hao.v_30.adapter.FragmentAdapter;
import com.hao.v_30.fragment.UserFragment;
import com.hao.v_30.fragment.UserWrapperFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ViewPager view_pager;
    private FragmentAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        String[] titles={
                "基本用法",
                "一对一关联"
        };
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new UserFragment());
        fragments.add(new UserWrapperFragment());
        adapter=new FragmentAdapter(getSupportFragmentManager(),fragments, Arrays.asList(titles));
    }


    private void initView() {
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setAdapter(adapter);
    }
}
