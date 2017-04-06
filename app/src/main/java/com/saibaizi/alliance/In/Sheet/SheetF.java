package com.saibaizi.alliance.In.Sheet;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;
import com.saibaizi.alliance.MainActivity;
import com.saibaizi.alliance.R;

import Constance.GlobalDatas;
import Constance.NetUrl;
import adapter.AllianceAdapter;
import base.BaseF;
import bean.AllTXBean;
import bean.AllianceBean;
import listener.HttpResultListener;
import util.GSONUtils;
import util.PublicUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by S0005 on 2017/4/6.
 */

public class SheetF extends BaseF {
    XRecyclerView recyclerView;
    AllTXBean allTXBean;
    private AllianceAdapter adapter;

    @Override
    protected void initView() {
       recyclerView = (XRecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        adapter = new AllianceAdapter(this);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initLazyData();
            }

            @Override
            public void onLoadMore() {

            }
        });


    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return  inflater.inflate(R.layout.fragment_sheet,container,false);
    }

    @Override
    protected void initLazyData() {
        super.initLazyData();
        makeText("报表懒加载成功");
        String token = getToken();
        RequestParams params=new RequestParams();
        params.put("token",token);
        if (GlobalDatas.DEBUG)
            Log.i(GlobalDatas.TAG, "prepareData: "+NetUrl.MEMBER_SBZ_ALL_1+"&"+params.toString());
        PublicUtils.postHttp(getContext(), NetUrl.MEMBER_SBZ_ALL_1, params, new HttpResultListener() {
            @Override
            public void success(int code, String message, String resultCode) {
                AllianceBean allianceBean = GSONUtils.parseJson(AllianceBean.class, resultCode);
                adapter.setBean(allianceBean);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void finish() {
                super.finish();
                recyclerView.refreshComplete();
            }

            @Override
            public void login() {
                super.login();
                startActivityForResult(new Intent(getContext()
                        ,MainActivity.class),100);
            }
        });
        getTXmessage();
    }

    public void getTXmessage(){
        String token = getToken();
        RequestParams params=new RequestParams();
        params.put("token",token);
        params.put("type",1);
        if (GlobalDatas.DEBUG)
            Log.i(GlobalDatas.TAG, "getTXmessage: "+ NetUrl.ALLIANCE_TX+"&"+params.toString());
        PublicUtils.postHttp(getContext(), NetUrl.ALLIANCE_TX, params, new HttpResultListener() {
            @Override
            public void success(int code, String message, String resultCode) {
                if (GlobalDatas.DEBUG)
                    Log.i(GlobalDatas.TAG, "success: "+resultCode);
                allTXBean = GSONUtils.parseJson(AllTXBean.class, resultCode);
                if(allTXBean==null)
                    return;
                adapter.setTXInfo(allTXBean);
                adapter.showNotice(allTXBean.getStatus().equals("1"));
                if (GlobalDatas.DEBUG)
                    Log.i(GlobalDatas.TAG, "success:getTXmessage "+resultCode);

            }

            @Override
            public void failed(String failed) {
                if (GlobalDatas.DEBUG)
                    Log.i(GlobalDatas.TAG, "failed:getTXmessage "+failed);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK){
            initLazyData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(adapter!=null)
            adapter.clear();
        if(recyclerView!=null)
            recyclerView.setLoadingListener(null);
    }
}
