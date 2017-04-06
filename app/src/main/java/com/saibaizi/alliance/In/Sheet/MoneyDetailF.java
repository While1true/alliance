package com.saibaizi.alliance.In.Sheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.saibaizi.alliance.R;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import Constance.GlobalDatas;
import Constance.NetUrl;
import base.App;
import base.BaseF;
import base.BaseUI;
import listener.HttpResultListener;
import util.GSONUtils;
import util.HttpUtils;
import util.PublicUtils;

/**
 * Created by S0005 on 2017/1/10.
 */

public class MoneyDetailF extends BaseF {
    private int type = -1;
    private TextView tv_click_num, tv_order_num, tv_jihuo_num, tv_shouyi_num;


    public MoneyDetailF setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    protected void initData() {
        super.initData();
        if (type == -1)
            return;
        String token = ((BaseUI)getContext()).getToken();
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("time_type", type);
        if (GlobalDatas.DEBUG)
            Log.i(GlobalDatas.TAG, "prepareData: " + NetUrl.MEMBER_SBZ_ALL_2 + "&" + params.toString());
        PublicUtils.postHttp(getContext(), NetUrl.MEMBER_SBZ_ALL_2, params, new HttpResultListener() {
            @Override
            public void success(int code, String message, String resultCode) {
                ResultBean resultBean = GSONUtils.parseJson(ResultBean.class, resultCode);
//                    tv_click_num.setText(resultBean.getClick()+"");
                if (GlobalDatas.DEBUG)
                    Log.i(GlobalDatas.TAG, "onSuccess: "+resultBean);
                tv_order_num.setText(resultBean.getOrder_num()==null?"0":resultBean.getOrder_num());
                tv_jihuo_num.setText(resultBean.getDevice_num()==null?"0":resultBean.getDevice_num());
                tv_shouyi_num.setText(resultBean.getPre_money()==null?"0":resultBean.getPre_money());
            }
        });
    }

    @Override
    protected void initView() {
        tv_order_num = (TextView) view.findViewById(R.id.tv_order_num);
        tv_jihuo_num = (TextView) view.findViewById(R.id.tv_jihuo_num);
        tv_shouyi_num = (TextView) view.findViewById(R.id.tv_shouyi_num);
    }

    @Override
    protected View setContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.alliance_moneydf, container, false);
    }

    class ResultBean implements Serializable {

        /**
         * click : 0
         * order_num : 0
         * device_num : 0
         * pre_money : null
         */

        private int click;
        private String order_num="0";
        private String device_num="0";
        private String pre_money="0";

        @Override
        public String toString() {
            return "ResultBean{" +
                    "click=" + click +
                    ", order_num='" + order_num + '\'' +
                    ", device_num='" + device_num + '\'' +
                    ", pre_money='" + pre_money + '\'' +
                    '}';
        }

        public int getClick() {
            return click;
        }

        public void setClick(int click) {
            this.click = click;
        }

        public String getOrder_num() {
            return order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getDevice_num() {
            return device_num;
        }

        public void setDevice_num(String device_num) {
            this.device_num = device_num;
        }

        public String getPre_money() {
            return pre_money;
        }

        public void setPre_money(String pre_money) {
            this.pre_money = pre_money;
        }
    }
}
