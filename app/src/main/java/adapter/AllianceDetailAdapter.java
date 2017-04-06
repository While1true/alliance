/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saibaizi.alliance.R;

import java.util.ArrayList;
import java.util.List;

import Constance.GlobalDatas;
import bean.AllianceOrderDetailBean;
import util.PublicUtils;

/**
 * Created by S0005 on 2017/3/17.
 */

public class AllianceDetailAdapter extends RecyclerView.Adapter {
    public static final int ITEM = 1,EMPTY=0;

    List<AllianceOrderDetailBean> list = new ArrayList<>();


    public void setList(List<AllianceOrderDetailBean> list) {
        this.list = list;
    }

    public void addList(List<AllianceOrderDetailBean> list) {
        if (list != null)
            list.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(list!=null) {
            switch (viewType) {
                case ITEM:
                    return new BaseItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.alliance_order_details_item, parent, false));
                default:
                    return null;
            }
        }else{
            if(viewType==EMPTY){
                return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_order, parent, false)) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BaseItemViewHolder){
            final AllianceOrderDetailBean bean=list.get(position);
            PublicUtils.loadPic( ((BaseItemViewHolder) holder).iv_pic,PublicUtils.getShowUrl(bean.getPic()));
            String price = String.format("%.2f", Double.parseDouble(bean.getReal_pay())/ Integer.parseInt(bean.getNum()));
            ((BaseItemViewHolder) holder).tv_price.setText(price);

            ((BaseItemViewHolder) holder).num.setText("x"+bean.getNum());
            ((BaseItemViewHolder) holder).tv_title.setText(bean.getName());
            PublicUtils.setOrderState(((BaseItemViewHolder) holder).tv_paystate,bean.getOrder_status(),bean.getAlimama_status(),bean.getIs_check_order());

            String price2 = String.format("%.2f",bean.getPre_money());
            if (bean.getOrder_status().equals("8") || bean.getOrder_status().equals("10")) {
                ((BaseItemViewHolder) holder).tv_shouyi.setText("--");
                ((BaseItemViewHolder) holder).tv_total_money.setText("--");
            } else {
                ((BaseItemViewHolder) holder).tv_shouyi.setText(price2);
                ((BaseItemViewHolder) holder).tv_total_money.setText(bean.getReal_pay());
            }
            ((BaseItemViewHolder) holder).tv_percentage.setText(bean.getId_code_per()+"%");
            ((BaseItemViewHolder) holder).tv_time.setText(bean.getCreate_order_time());
        }


    }

    public class NoMoreViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nomore;

        public NoMoreViewHolder(View itemView) {
            super(itemView);
            tv_nomore = (TextView) itemView.findViewById(R.id.tv_nomore);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(list==null)
            return EMPTY;

        if (position < getItemCount())
            return ITEM;
        else
            return -1;
    }

    @Override
    public int getItemCount() {
        if(list==null)
            return 1;
        return list.size();
    }



    class BaseItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_paystate, tv_title, num, tv_price, tv_shouyi, tv_total_money, tv_percentage, tv_time,tv_from;
        ImageView iv_pic;


        public BaseItemViewHolder(View itemView) {
            super(itemView);
            tv_from = (TextView) itemView.findViewById(R.id.tv_from);
            tv_from.setVisibility(View.GONE);
            tv_paystate = (TextView) itemView.findViewById(R.id.tv_paystate);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            num = (TextView) itemView.findViewById(R.id.num);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_shouyi = (TextView) itemView.findViewById(R.id.tv_shouyi);
            tv_total_money = (TextView) itemView.findViewById(R.id.tv_total_money);
            tv_percentage = (TextView) itemView.findViewById(R.id.tv_percentage);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);

            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);

        }
    }
}
