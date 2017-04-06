package com.saibaizi.alliance.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saibaizi.alliance.R;

import base.BaseF;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                break;
            case R.id.login:
                break;
        }
    }
}
