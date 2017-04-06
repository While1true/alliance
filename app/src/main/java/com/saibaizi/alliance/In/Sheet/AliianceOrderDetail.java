/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.saibaizi.alliance.In.Sheet;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.http.RequestParams;
import com.saibaizi.alliance.MainActivity;
import com.saibaizi.alliance.R;

import java.lang.reflect.Type;
import java.util.List;

import Constance.GlobalDatas;
import Constance.NetUrl;
import adapter.AllianceDetailAdapter;
import base.BaseUI;
import bean.AllianceOrderDetailBean;
import listener.HttpResultListener;
import util.GSONUtils;
import util.PublicUtils;
import util.Utils;

/**
 * Created by S0005 on 2017/3/17.
 */

public class AliianceOrderDetail extends BaseUI implements View.OnClickListener {

    private View back_top, img_title_back, iv_msg_icon;

    private XRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AllianceDetailAdapter adapter;
    private int pagerNum = 1;
    private int pagerSize = 15;
    private int scrollY;
    private boolean loading, nomore;

    @Override
    protected void back() {
        finish();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.alliance_order_detail);
        findViewById(R.id.gl_top).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.title_name)).setText("订单明细");
        //返回按钮
        img_title_back = findViewById(R.id.img_title_back);
        img_title_back.setOnClickListener(this);


        //RecyclerView
        recyclerView = (XRecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int itemCount = parent.getAdapter().getItemCount();
                int childAdapterPosition = parent.getChildAdapterPosition(view);
                if (childAdapterPosition == 0)
                    return;
                if (childAdapterPosition < itemCount)
                    outRect.set(0, 0, 0, Utils.dp2px(parent.getContext(), 14));
            }
        });
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
                recyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RefreshLoading(recyclerView);
                scrollY += dy;
                if (scrollY > Utils.dp2px(AliianceOrderDetail.this, 500)) {
                    showBackTop();
                } else {
                    dismissBackTop();
                }
            }
        });
    }

    private void RefreshLoading(RecyclerView recyclerView) {
        if (loading || nomore) {
            return;
        }
        int lastPosition = -1;
        //当前状态为停止滑动状态SCROLL_STATE_IDLE时
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
            lastPosition = findMax(lastPositions);
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof GridLayoutManager) {
            //通过LayoutManager找到当前显示的最后的item的position
            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }

        //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
        //如果相等则说明已经滑动到最后了
        if (recyclerView.getLayoutManager().getItemCount() - 5 < 0) {
            return;
        }
        if (lastPosition >= recyclerView.getLayoutManager().getItemCount() - 5) {
            if (!loading) {
                pagerNum++;
                doNet();

            }
        }

    }

    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private void refresh() {
        nomore = false;
        pagerNum = 1;
        doNet();
    }

    @Override
    protected void initData() {
        doNet();
    }

    private void doNet() {
        RequestParams params = new RequestParams();
        String token = getToken();
        params.put("token", token);
        params.put("pageNumber", pagerNum);
        params.put("pageSize", pagerSize);
        if (GlobalDatas.DEBUG)
            Log.i(GlobalDatas.TAG, "doNet: " + NetUrl.ALLIANCE_DETAIL + "&" + params.toString());
        PublicUtils.postHttp(this, NetUrl.ALLIANCE_DETAIL, params, new HttpResultListener() {
            @Override
            public void finish() {
                loading = false;
            }

            @Override
            public void success(int code, String message, String resultCode) {
                if (pagerNum == 1 && resultCode == null) {
                    recyclerView.setEmptyView(View.inflate(AliianceOrderDetail.this, R.layout.empty_order, null));
                    return;
                }
                if (adapter == null) {
                    adapter = new AllianceDetailAdapter();
                    recyclerView.setAdapter(adapter);
                }
                Type type = new TypeToken<List<AllianceOrderDetailBean>>() {
                }.getType();
                List<AllianceOrderDetailBean> orderList = GSONUtils.parseJson(type, resultCode);

                if (pagerNum == 1) {
                    adapter.setList(orderList);
                    if (orderList == null || orderList.isEmpty()) {
                        recyclerView.setEmptyView(View.inflate(AliianceOrderDetail.this, R.layout.empty_order, null));
                    } else if (orderList.size() < pagerSize) {
                        nomore = true;
                    }
                } else {
                    if (orderList == null || orderList.isEmpty()) {
                        pagerNum--;
                        nomore = true;
                    } else if (orderList.size() < pagerSize) {
                        adapter.addList(orderList);
                        nomore = true;
                    } else {
                        adapter.addList(orderList);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void login() {
                Intent intent = new Intent(AliianceOrderDetail.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showBackTop() {
        if (back_top.getVisibility() == View.GONE) {
            Animation showAnim = new AlphaAnimation(0.0f, 1.0f);
            showAnim.setDuration(500);
            showAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    back_top.setVisibility(View.VISIBLE);
//                    top.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            back_top.startAnimation(showAnim);
        }
    }

    private void dismissBackTop() {
        if (back_top.getVisibility() == View.VISIBLE) {
            Animation dismissAnim = new AlphaAnimation(1.0f, 0.0f);
            dismissAnim.setDuration(500);
            dismissAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    back_top.setVisibility(View.GONE);
//                    top.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            back_top.startAnimation(dismissAnim);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //返回键
            case R.id.img_title_back:
                back();
                break;
        }
    }

}
