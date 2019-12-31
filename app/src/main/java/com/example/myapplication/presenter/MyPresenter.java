package com.example.myapplication.presenter;

import com.example.myapplication.base.BasePresenter;
import com.example.myapplication.contract.IContract;
import com.example.myapplication.model.MyModel;

public class MyPresenter extends BasePresenter {

    @Override
    public void toRequest(String url) {
        new MyModel().getData(url, new IContract.ModelCallBack() {
            @Override
            public void modelSuccess(String json) {
                getView().viewGetData(json);
            }

            @Override
            public void modelError(String error) {

            }
        });
    }
}
