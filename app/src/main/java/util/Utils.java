package util;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.attr.TextSizeAttr;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * Created by S0005 on 2017/4/6.
 */

public class Utils {
    public static int dp2px(Context context,float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
    /**
     * autoLayout TextView 的字体大小
     *
     * @param tv
     * @param px
     */
    public static void setTextViewSize(TextView tv, int px) {
        AutoLayoutHelper.AutoLayoutParams layoutParams =
                (AutoLayoutHelper.AutoLayoutParams) tv.getLayoutParams();
        layoutParams.getAutoLayoutInfo().addAttr(new TextSizeAttr(px, 0, 0));
        ViewGroup.LayoutParams layoutParamsTwo = tv.getLayoutParams();
        tv.setLayoutParams(layoutParamsTwo);
    }
}
