package util;

import android.graphics.Rect;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.saibaizi.alliance.R;
import com.zhy.autolayout.utils.AutoUtils;

import Constance.GlobalDatas;

/**
 * Created by S0005 on 2017/4/6.
 */

public class Back {
    /**
     * 监听软键盘
     */
//    final View rl = findViewById(R.id.rl_rl);
//    final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
//    listener = new ViewTreeObserver.OnGlobalLayoutListener() {
//        @Override
//        public void onGlobalLayout() {
//            Rect rect = new Rect();
//            rl.getWindowVisibleDisplayFrame(rect);
//            int rootInvisibleHeight = rl.getRootView().getHeight() - rect.bottom;
//            if (rootInvisibleHeight <= 100) {
//                //软键盘隐藏啦
//                if(!ishide) {
//                    ishide=true;
////                        ivTop.setVisibility(View.VISIBLE);
//                    rlRl.scrollTo(0,0);
//                    if (GlobalDatas.DEBUG)
//                        Log.i(GlobalDatas.TAG, "run: "+rlBottom.getScrollY()+"tt"+rlBottom.getTranslationY()+rlBottom.getHeight());
//                }
//
//            } else {
//                ////软键盘弹出啦
//                if(ishide) {
//                    ishide=false;
//                    rlRl.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            int[]l=new int[2];
//                            rlBottom.getLocationInWindow(l);
//                            if (GlobalDatas.DEBUG)
//                                Log.i(GlobalDatas.TAG, "run: "+rlBottom.getScrollY()+"tt"+rlBottom.getTranslationY()+rlBottom.getHeight());
////                                ivTop.setVisibility(View.INVISIBLE);
//                            rlRl.scrollTo(0,l[1]-(int)((50-107)* AutoUtils.getPercentHeight1px()));
//                        }
//                    }, 200);
//                }
//
//
//            }
//        }
//    };
//        rootView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
}
