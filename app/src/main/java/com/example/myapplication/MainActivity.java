package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.MyAdapter;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.base.BasePresenter;
import com.example.myapplication.entity.Bean;
import com.example.myapplication.presenter.MyPresenter;
import com.example.myapplication.utils.OkHttpUtil;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Override
    protected void initData() {
        if (OkHttpUtil.getInstance().is(this)) {
            p.toRequest("http://blog.zhaoliang5156.cn/api/news/ranking.json");
        } else {
            Toast.makeText(this, "没网", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected BasePresenter initPresenter() {
        return new MyPresenter();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void viewGetData(String json) {
        Bean bean = new Gson().fromJson(json, Bean.class);
        MyAdapter myAdapter = new MyAdapter(this, bean.getRanking());
        recycler.setAdapter(myAdapter);
        myAdapter.setOnItemClick(new MyAdapter.OnItemClick() {
            @Override
            public void onClick(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.textView)
    public void onViewClicked() {
        Intent intent=new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }
}
