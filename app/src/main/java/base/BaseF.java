package base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.saibaizi.alliance.R;

import bean.UserBean;
import util.GSONUtils;

/**
 * Created by S0005 on 2017/3/31.
 */

public abstract class BaseF extends Fragment {

    protected View view;
    private boolean isvisable;
    private boolean isinitview;
    private boolean isfirst=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = setContentView(inflater,container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        isinitview =true;
        loadLazyData();
    }

    /**
     * 初始化VIew
     */
    protected abstract void initView();

    /**
     * 非懒加载
     */
    protected void initData(){}

    protected abstract View setContentView(LayoutInflater inflater,ViewGroup container);

    /**
     * 懒加载数据
     */
    protected  void initLazyData(){}


    private void loadLazyData(){
        if(!isfirst||!isvisable||!isinitview){

            return;
        }
        initLazyData();
        isfirst=false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isvisable =true;
            loadLazyData();
        }else {
            isvisable=false;
        }
    }

    public App getApp(){

        return (App)getActivity().getApplication();
    }

    public void makeText(String context){
        Toast.makeText(getContext(),context,0).show();
    }
    public String getToken(){
        String userbean1 = getApp().getSp().getString(getString(R.string.userbean), "1");
        UserBean userBean = GSONUtils.parseJson(UserBean.class, userbean1);
        return userBean==null?"1":userBean.getToken();
    }
}
