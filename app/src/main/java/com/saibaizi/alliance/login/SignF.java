package com.saibaizi.alliance.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saibaizi.alliance.R;

import base.BaseF;

/**
 * Created by S0005 on 2017/4/5.
 */

public class SignF extends BaseF {
    @Override
    protected void initView() {

    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_sign,container,false);
    }
}
