package com.example.myapplication.contract;

public interface IContract {
    interface IModel{
        void getData(String url,ModelCallBack callBack);
    }
    interface ModelCallBack{
        void modelSuccess(String json);
        void modelError(String error);
    }
    interface IView{
        void viewGetData(String json);
    }
    interface IPresenter{
        void toRequest(String url);
    }
}
