package com.saibaizi.alliance.In.Mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saibaizi.alliance.In.Mine.Settings.SetActivity;
import com.saibaizi.alliance.R;

import java.net.URLDecoder;

import base.BaseF;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.client.utils.URLEncodedUtils;

/**
 * Created by S0005 on 2017/4/6.
 */

public class MineF extends BaseF {
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_rank_qy)
    TextView tvRankQy;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_bind)
    TextView tvBind;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.tv_assistant)
    TextView tvAssistant;
    @BindView(R.id.tv_about)
    TextView tvAbout;

    @Override
    protected void initView() {
        ButterKnife.bind(this, view);
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    protected void initLazyData() {
        super.initLazyData();
        makeText("我的懒加载成功");
    }
    @OnClick({R.id.tv_rank_qy, R.id.rl_top, R.id.tv_recommend, R.id.tv_bind, R.id.tv_setting, R.id.tv_assistant, R.id.tv_about})
    public void onClick(View view) {
        makeText(view.getId()+"");
        switch (view.getId()) {
            case R.id.tv_rank_qy:
                break;
            case R.id.rl_top:
                break;
            case R.id.tv_recommend:
                break;
            case R.id.tv_bind:
                break;
            case R.id.tv_setting:
                startActivity(new Intent(getContext(), SetActivity.class));
                break;
            case R.id.tv_assistant:
                break;
            case R.id.tv_about:
                break;
        }
    }
}
