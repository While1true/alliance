package com.saibaizi.alliance;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

import com.saibaizi.alliance.login.LoginF;
import com.saibaizi.alliance.login.SignF;
import com.zhy.autolayout.utils.AutoUtils;

import Constance.GlobalDatas;
import base.BaseUI;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseUI {

    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rl_rl)
    RelativeLayout rlRl;
    @BindView(R.id.rg_main)
    RadioGroup rgMain;
    @BindView(R.id.sanjiao)
    ImageView sanjiao;
    private ValueAnimator animator;
    private int measuredWidth;
    private int widthPixels;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private ViewTreeObserver.OnGlobalLayoutListener listener;
    private boolean ishide=true;
    private long lastpress=0;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        widthPixels = getResources().getDisplayMetrics().widthPixels;
        fm = getSupportFragmentManager();
        sanjiao.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sanjiao.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                measuredWidth = sanjiao.getMeasuredWidth();
                if (GlobalDatas.DEBUG)
                    Log.i(GlobalDatas.TAG, "onGlobalLayout: " + measuredWidth);
                int checkedRadioButtonId = rgMain.getCheckedRadioButtonId();
                switch (checkedRadioButtonId) {
                    case R.id.rb_login:
                        sanjiao.setPadding(widthPixels / 4 - measuredWidth / 2, 0, 0, 0);
                        break;
                    case R.id.rb_sign:
                        sanjiao.setPadding(3 * widthPixels / 4 - measuredWidth / 2, 0, 0, 0);
                        break;
                }
            }
        });
        changeLogin();
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.rb_login:
                        changeLogin();
                        break;
                    case R.id.rb_sign:
                        changeSign();
                        break;
                }
            }
        });

        /**
         * 监听软键盘
         */
        final View rl = findViewById(R.id.rl_rl);
        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                rl.getWindowVisibleDisplayFrame(rect);
                int rootInvisibleHeight = rl.getRootView().getHeight() - rect.bottom;
                if (rootInvisibleHeight <= 100) {
                    //软键盘隐藏啦
                    if(!ishide) {
                        ishide=true;
//                        ivTop.setVisibility(View.VISIBLE);
                        rlRl.scrollTo(0,0);
                        if (GlobalDatas.DEBUG)
                            Log.i(GlobalDatas.TAG, "run: "+rlBottom.getScrollY()+"tt"+rlBottom.getTranslationY()+rlBottom.getHeight());
                    }

                } else {
                    ////软键盘弹出啦
                    if(ishide) {
                        ishide=false;
                        rlRl.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int[]l=new int[2];
                                rlBottom.getLocationInWindow(l);
                                if (GlobalDatas.DEBUG)
                                    Log.i(GlobalDatas.TAG, "run: "+rlBottom.getScrollY()+"tt"+rlBottom.getTranslationY()+rlBottom.getHeight());
//                                ivTop.setVisibility(View.INVISIBLE);
                             rlRl.scrollTo(0,l[1]-(int)((50-107)*AutoUtils.getPercentHeight1px()));
                            }
                        }, 200);
                    }


                }
            }
        };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    private void changeSign() {
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_content, new SignF()).commit();
        startAnimation(3 * widthPixels / 4 - measuredWidth / 2);
    }

    private void changeLogin() {
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_content, new LoginF()).commit();
        startAnimation(widthPixels / 4 - measuredWidth / 2);
    }

    @Override
    protected void initData() {
        startActivity(new Intent(this,INActivity.class));

    }

    @Override
    protected void back() {
      if(System.currentTimeMillis()-lastpress>2000){
          lastpress=System.currentTimeMillis();
          makeText("再按一次退出应用");
      }else{
          finish();
      }

    }

    public void startAnimation(int to) {
        if (animator != null)
            animator.cancel();
        animator = ValueAnimator.ofInt(sanjiao.getPaddingLeft(), to);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int padding = (int) valueAnimator.getAnimatedValue();
                sanjiao.setPadding(padding, 0, 0, 0);
            }
        });
        animator.start();
    }

    @Override
    protected void onDestroy() {
        getWindow().getDecorView().findViewById(android.R.id.content).getRootView().getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        super.onDestroy();

    }
}
