package util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.zhy.autolayout.utils.ScreenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import Constance.GlobalDatas;
import Constance.NetUrl;
import cz.msebera.android.httpclient.Header;
import listener.HttpResultListener;

/**
 * Created by S0005 on 2017/3/30.
 */

public class PublicUtils {


    public static void loadPic(ImageView view,String url){
        Glide.with(view.getContext())
                .load(url)
                .into(view);
    }
    public static void loadRoundPic(ImageView view,String url,final int radius){
        Glide.with(view.getContext())
                .load(url)
                .transform(new BitmapTransformation(view.getContext()) {
                    @Override
                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                        return roundCrop(pool,toTransform);
                    }
                    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
                        if (source == null) return null;

                        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
                        if (result == null) {
                            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
                        }

                        Canvas canvas = new Canvas(result);
                        Paint paint = new Paint();
                        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
                        paint.setAntiAlias(true);
                        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
                        canvas.drawRoundRect(rectF, radius, radius, paint);
                        return result;
                    }
                    @Override
                    public String getId() {
                        return getClass().getName();
                    }
                })
                .into(view);
    }
    public static void loadCirclePic(ImageView view,String url){
        Glide.with(view.getContext())
                .load(url)
                .transform(new BitmapTransformation(view.getContext()) {
                    @Override
                    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                        return circleCrop(pool,toTransform);
                    }
                    private  Bitmap circleCrop(BitmapPool pool, Bitmap source) {
                        if (source == null) return null;

                        int size = Math.min(source.getWidth(), source.getHeight());
                        int x = (source.getWidth() - size) / 2;
                        int y = (source.getHeight() - size) / 2;

                        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

                        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
                        if (result == null) {
                            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                        }

                        Canvas canvas = new Canvas(result);
                        Paint paint = new Paint();
                        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
                        paint.setAntiAlias(true);
                        float r = size / 2f;
                        canvas.drawCircle(r, r, r, paint);
                        return result;
                    }
                    @Override
                    public String getId() {
                        return getClass().getName();
                    }
                })
                .into(view);
    }


    /**
     * 收入明细状态
     *
     * @param state
     */
    public static void setOrderState(TextView tv, String state, int type, int checkorder) {
        if (GlobalDatas.DEBUG)
            Log.i(GlobalDatas.TAG, "setOrderState: " + type);
        switch (state) {
            case "1":
                tv.setText("等待买家付款");
                break;
            case "2":
                tv.setText("等待卖家发货");
                break;
            case "3":
                tv.setText("已发货");
                break;
            case "6":
                tv.setText("交易成功");
                break;
            case "8":
                tv.setText("交易关闭");
                break;
            case "9":
                tv.setText("退款中");
                break;
            case "10":
                tv.setText("交易关闭");
                break;
            default:
                if(checkorder==1){
                    tv.setText("已结算");
                }else if (type == 1)
                    tv.setText("待结算");
                else if (type == 2)
                    tv.setText("已维权退款");
                break;
        }
    }
    /**
     * 图片显示风格
     *
     * @param url
     * @return
     */
    public static String getShowUrl(String url) {
        String url2;
        if (url.contains("/data/upfiles/")) {
            url2 = NetUrl.Host.trim() + url.trim();
        } else if (url.contains("alicdn.com/")) {
            url2 = url.trim();
            if (0 < GlobalDatas.SCACLE && GlobalDatas.SCACLE <= 2) {
                url2 = url + "_260x260q90.jpg";
            } else if (GlobalDatas.SCACLE > 2 && GlobalDatas.SCACLE <= 3) {
                url2 = url + "_300x300q90.jpg";
            } else if (GlobalDatas.SCACLE > 3) {
                url2 = url + "_400x400q90.jpg";
            }
        } else if (url.contains("img.saibaizi.com/")) {
            url2 = NetUrl.Domain + url.trim().split("@!")[0];
            if (0 < GlobalDatas.SCACLE && GlobalDatas.SCACLE <= 2) {
                url2 = url2 + "@!260";
            } else if (GlobalDatas.SCACLE > 2 && GlobalDatas.SCACLE <= 3) {
                url2 = url2 + "@!260";
            } else if (GlobalDatas.SCACLE > 3) {
                url2 = url2 + "@!400";
            }
        } else {
            url2 = url;
        }
        if (!url2.startsWith("http"))
            url2 = NetUrl.Domain + url2;
        return url2;
    }


    /**
     * 获取唯一码
     * @param context
     * @return
     */
    public static String getUniqueCode(Context context) {
        String tmDevice, tmSerial, tmPhone, androidId;
        TelephonyManager tm = null;
        try {
            tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            tmDevice = "" + tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
            tmDevice="";
        }
        if(!TextUtils.isEmpty(tmDevice)){
            return tmDevice;
        }
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     *
     * @param activity
     */
    public static void setSysBar(Activity activity) {
        if (Build.VERSION.SDK_INT > 19) {
            try {
//
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(0xFF353535);
                ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null)
                    ViewCompat.setFitsSystemWindows(mChildView, true);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if(Build.VERSION.SDK_INT == 19){
            try {
                Window window = activity.getWindow();
                ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

//First translucent status bar.
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                int statusBarHeight = ScreenUtils.getStatusBarHeight(activity);

                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                    //如果已经为 ChildView 设置过了 marginTop, 再次调用时直接跳过
                    if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                        //不预留系统空间
                        ViewCompat.setFitsSystemWindows(mChildView, false);
                        lp.topMargin += statusBarHeight;
                        mChildView.setLayoutParams(lp);
                    }
                }

                View statusBarView = mContentView.getChildAt(0);
                if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
                    //避免重复调用时多次添加 View
                    statusBarView.setBackgroundColor(0xff353535);
                    return;
                }
                statusBarView = new View(activity);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
                statusBarView.setBackgroundColor(0xff353535);
//向 ContentView 中添加假 View
                mContentView.addView(statusBarView, 0, lp);

            } catch (Exception e) {

            }
        }
    }


    /**
     * 网络接口总集
     *post请求
     * @param context
     * @param url
     * @param params
     * @param listener
     */
    public static void postHttp(Context context, String url, RequestParams params, final HttpResultListener listener) {
        HttpUtils.post(context, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                int code = 0;
                String message = "";
                String resultCode = "";
                try {
                    code = response.getInt("code");
                    message = response.getString("message");
                    resultCode = response.getString("resultCode");

                } catch (Exception e) {
                    listener.failed(response.toString());
                    e.printStackTrace();
                }
                if (code == 10000) {
                    listener.success(code, message, resultCode);
                } else if (code == 30000) {
                    listener.login();
                } else {
                    listener.failed(message);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.failed("网络错误");
            }

            @Override
            public void onStart() {
                listener.start();
            }

            @Override
            public void onFinish() {
                listener.finish();
            }
        });
    }

    /**
     * get请求
     * @param context
     * @param url
     * @param params
     * @param listener
     */
    public static void getHttp(Context context, String url, RequestParams params, final HttpResultListener listener) {
        HttpUtils.get(context, url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                int code = 0;
                String message = "";
                String resultCode = "";
                try {
                    code = response.getInt("code");
                    message = response.getString("message");
                    resultCode = response.getString("resultCode");

                } catch (JSONException e) {
                    listener.failed(response.toString());
                    e.printStackTrace();
                }
                if (code == 10000) {
                    listener.success(code, message, resultCode);
                } else if (code == 30000) {
                    listener.login();
                } else {
                    listener.failed(message);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                listener.failed("网络错误");
            }

            @Override
            public void onStart() {
                super.onStart();
                listener.start();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                listener.finish();
            }
        });
    }

}
