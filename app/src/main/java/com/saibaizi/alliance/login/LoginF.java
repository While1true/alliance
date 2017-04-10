package com.saibaizi.alliance.login;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saibaizi.alliance.INActivity;
import com.saibaizi.alliance.R;

import Constance.GlobalDatas;
import base.BaseF;
import bean.UserBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.GSONUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by S0005 on 2017/4/5.
 */

public class LoginF extends BaseF {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.rl_quicklogin)
    RelativeLayout rlQuicklogin;
    @BindView(R.id.login)
    TextView login;

    @Override
    protected void initView() {
        ButterKnife.bind(this, view);
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @OnClick({R.id.tv_forget, R.id.rl_quicklogin, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_forget:
                break;
            case R.id.rl_quicklogin:
                Intent intent = new Intent("com.sbz.auth");
                intent.addCategory("android.intent.category.DEFAULT");
                startActivityForResult(intent, 110);

                break;
            case R.id.login:
                startActivity(new Intent(getContext(), INActivity.class));
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110 && resultCode == RESULT_OK) {
            String phone = data.getStringExtra("phone");
            String token = data.getStringExtra("token");
            if (GlobalDatas.DEBUG)
                Log.i(GlobalDatas.TAG, "onActivityResult: " + phone + " --- " + token);
            UserBean userBean = new UserBean();
            userBean.setToken(token);
            userBean.setPhone(phone);
            makeText("授权成功");
            getApp().getSp().edit().putString("userbean", GSONUtils.toJson(userBean)).commit();
            startActivity(new Intent(getContext(), INActivity.class));
            getActivity().finish();
        } else {
            makeText("授权失败");
        }
    }
}
