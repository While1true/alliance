package com.saibaizi.alliance.In.Mine.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class LPSWActivity extends BaseUI {
    @BindView(R.id.img_title_back)
    ImageView imgTitleBack;
    @BindView(R.id.et_old)
    EditText etOld;
    @BindView(R.id.et_new)
    EditText etNew;
    @BindView(R.id.et_new_twice)
    EditText etNewTwice;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.bt_confirm)
    TextView btConfirm;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_lpsw);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void back() {
        finish();

    }

    @OnClick({R.id.img_title_back, R.id.tv_forget, R.id.bt_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_title_back:
                back();
                break;
            case R.id.tv_forget:
                break;
            case R.id.bt_confirm:
                break;
        }
    }
}
