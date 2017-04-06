package base;

import android.app.Application;
import android.content.SharedPreferences;

import Constance.GlobalDatas;

/**
 * Created by S0005 on 2017/4/1.
 */

public class App extends Application {
    public static final String SPTAG="BAIXIU";
    @Override
    public void onCreate() {
        super.onCreate();
        //获取当前设备屏幕大小
        GlobalDatas.SCACLE = getResources().getDisplayMetrics().density;
    }

    public SharedPreferences getSp(){

        return getSharedPreferences(SPTAG,MODE_PRIVATE);
    }
}
