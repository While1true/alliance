package base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.Toast;

import com.saibaizi.alliance.R;
import com.zhy.autolayout.AutoLayoutActivity;

import bean.UserBean;
import listener.PermissionListener;
import util.GSONUtils;

/**
 * Created by S0005 on 2017/3/30.
 */

public abstract class BaseUI extends AutoLayoutActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PublicUtils.setSysBar(this);
        initView();
        initData();
    }

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据，在View之后调用
     */
    protected abstract void initData();

    /**
     * 返回键的控制
     */
    protected abstract void back();
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                back();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    /**
     * 如果已经授权过了回调
     * 未授权如下调取获取授权结果
     *  调用@PermissionSuccess(requestCode = 100)
     *  调用@PermissionFail(requestCode = 100)
     * @param permission
     * @param requestCode
     */
    protected void requestPermissions(String permission, int requestCode, PermissionListener listener){
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
        } else
        {
            listener.alreadyGranted();
        }
    }

    public void makeText(String context){
        Toast.makeText(this,context,0).show();
    }

    public App getApp(){

        return (App)getApplication();
    }

    public String getToken(){
        String userbean1 = ((App) getApplication()).getSp().getString(getString(R.string.userbean), "1");
        UserBean userBean = GSONUtils.parseJson(UserBean.class, userbean1);
//        return userBean==null?"1":userBean.getToken();
        return "M2JlMGU5NjllODI5ODVhOGRjMjBjNGNkNzJlMjgwZjE=";
    }
}
