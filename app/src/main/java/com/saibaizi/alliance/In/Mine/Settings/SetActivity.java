package com.saibaizi.alliance.In.Mine.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saibaizi.alliance.R;

import base.BaseUI;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S0005 on 2017/4/10.
 */

public class SetActivity extends BaseUI {
    @BindView(R.id.img_title_back)
    ImageView imgTitleBack;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tv_modify_phone)
    TextView tvModifyPhone;
    @BindView(R.id.tv_loginpass)
    TextView tvLoginpass;
    @BindView(R.id.tv_paypass)
    TextView tvPaypass;
    @BindView(R.id.tv_push)
    TextView tvPush;
    @BindView(R.id.tv_clear)
    TextView tvClear;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void back() {
finish();
    }

    @OnClick({R.id.img_title_back, R.id.tv_modify_phone, R.id.tv_loginpass, R.id.tv_paypass, R.id.tv_push, R.id.tv_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_title_back:
                back();
                break;
            case R.id.tv_modify_phone:
                break;
            case R.id.tv_loginpass:
                startActivity(new Intent(this,LPSWActivity.class));
                break;
            case R.id.tv_paypass:
                break;
            case R.id.tv_push:
                break;
            case R.id.tv_clear:
                break;
        }
    }
}
