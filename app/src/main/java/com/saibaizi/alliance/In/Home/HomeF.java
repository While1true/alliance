package com.saibaizi.alliance.In.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.saibaizi.alliance.R;

import base.BaseF;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by S0005 on 2017/4/6.
 */

public class HomeF extends BaseF {
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;

    @Override
    protected void initView() {
        ButterKnife.bind(this, view);

    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initLazyData() {
        super.initLazyData();
        makeText("主页懒加载成功");
    }
    @OnClick({R.id.iv_1, R.id.iv_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_1:
                break;
            case R.id.iv_2:
                break;
        }
    }
}
