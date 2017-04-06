package util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by S0005 on 2017/4/6.
 */

public class Utils {
    public static int dp2px(Context context,float dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
}
