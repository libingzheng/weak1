package com.example.myapplication.model;

import com.example.myapplication.contract.IContract;
import com.example.myapplication.utils.OkHttpUtil;

public class MyModel implements IContract.IModel {
    @Override
    public void getData(String url, IContract.ModelCallBack callBack) {
        OkHttpUtil.getInstance().doGet(url, new OkHttpUtil.OkCallBack() {
            @Override
            public void okSuccess(String json) {
                callBack.modelSuccess(json);
            }

            @Override
            public void okError(String error) {
                callBack.modelError(error);
            }
        });
    }
}
