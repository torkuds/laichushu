package com.sofacity.laichushu.mvp.login;

import android.widget.EditText;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sofacity.laichushu.anim.ShakeAnim;
import com.sofacity.laichushu.bean.netbean.Login_Paramet;
import com.sofacity.laichushu.retrofit.ApiCallback;
import com.sofacity.laichushu.ui.activity.LoginActivity;
import com.sofacity.laichushu.ui.activity.MainActivity;
import com.sofacity.laichushu.ui.base.BasePresenter;
import com.sofacity.laichushu.utils.SharePrefManager;
import com.sofacity.laichushu.utils.ToastUtil;
import com.sofacity.laichushu.utils.UIUtil;
import com.sofacity.laichushu.utils.Validator;

/**
 * 登录 presenter
 * Created by wangtong on 2016/10/12.
 */
public class LoginPresenter extends BasePresenter<LoginView> {
    private LoginActivity mActivity;

    //初始化构造
    public LoginPresenter(LoginView view) {
        attachView(view);
        mActivity = (LoginActivity) view;
    }

    //登陆前的判断
    public void preLogin() {
        String loginInfo = SharePrefManager.getLoginInfo();
        //保存正确的帐号密码 直接进app
        if (!"".equals(loginInfo)) {
            String[] loginStrs = loginInfo.split(",");
            String username = loginStrs[0];
            String password = loginStrs[1];
            UIUtil.openActivity(mActivity, MainActivity.class);
            mActivity.finish();
        }
    }

    //登陆
    public boolean login(EditText usernameEt, EditText passwordEt) {
        String username = usernameEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        boolean isLogin ;
        //校验
        if (username.isEmpty()) {
            ToastUtil.showToast("手机号不能为空！");
            usernameEt.startAnimation(ShakeAnim.shakeAnimation(3));
            return isLogin = false;
        } else if (password.isEmpty()) {
            ToastUtil.showToast("密码不能为空！");
            passwordEt.startAnimation(ShakeAnim.shakeAnimation(3));
            return isLogin = false;
        } else if (username.length() < 11) {
            ToastUtil.showToast("手机号11位！");
            usernameEt.startAnimation(ShakeAnim.shakeAnimation(3));
            return isLogin = false;
        } else if (password.length() < 6) {
            ToastUtil.showToast("密码至少6位！");
            passwordEt.startAnimation(ShakeAnim.shakeAnimation(3));
            return isLogin = false;
        }
        if (!Validator.isMobile(username)){
            ToastUtil.showToast("请输入正确的手机号!");
            usernameEt.startAnimation(ShakeAnim.shakeAnimation(3));
            return isLogin = false;
        }
        if (!Validator.isUsername(password)) {
            ToastUtil.showToast("您输入的密码含有汉字或特殊字符，请重新输入！");
            passwordEt.startAnimation(ShakeAnim.shakeAnimation(3));
            return isLogin = false;
        }
        loadData(username, password);
        return isLogin = true;
    }

    //网络请求
    public void loadData(String username, String password) {
        mvpView.showLoading();
        Login_Paramet paramet = new Login_Paramet(username, password);
        Logger.e("登录请求参数");
        Logger.json(new Gson().toJson(paramet));
        addSubscription(apiStores.loginLoadData(paramet),
                new ApiCallback<LoginModel>() {
                    @Override
                    public void onSuccess(LoginModel model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mvpView.getDataFail("code+" + code + "/msg:" + msg);
                    }

                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }

                });
    }

    public void lastLogin(String username, String password) {
        //记住密码
        SharePrefManager.setLoginInfo(username + "," + password);
        //延时跳转页面
        UIUtil.getMainThreadHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UIUtil.openActivity(mActivity, MainActivity.class);
                mActivity.finish();
            }
        }, 1710);
    }
}