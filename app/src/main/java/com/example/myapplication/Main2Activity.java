package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.btn_wx)
    Button btnWx;
    @BindView(R.id.btn_qq)
    Button btnQq;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bind = ButterKnife.bind(this);
        CodeUtils.init(this);
        EventBus.getDefault().register(this);
        Bitmap bitmap = CodeUtils.createImage("李秉正", 300, 300, null);
        imageView.setImageBitmap(bitmap);
    }
    @Subscribe
    public void getName(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    @OnClick({ R.id.btn_wx, R.id.btn_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_wx:
                EventBus.getDefault().post("微信");
                break;
            case R.id.btn_qq:
                EventBus.getDefault().post("QQ");
                break;
        }
    }
    @OnLongClick(R.id.imageView)
    public void OnLongClick(View v){
        CodeUtils.analyzeByImageView(imageView, new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Toast.makeText(Main2Activity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnalyzeFailed() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (bind!=null){
            bind.unbind();
        }
    }
}
