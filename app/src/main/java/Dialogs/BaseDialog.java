/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by S0005 on 2017/3/18.
 */

public abstract class BaseDialog {
    protected Context context;
    protected Dialog dialog;
    protected boolean register;

    public BaseDialog(Context context) {
        this.context = context;
        dialog=new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public abstract BaseDialog builder();

    public BaseDialog show() {
        if(register)
        EventBus.getDefault().register(this);
        dialog.show();
        return this;
    }
    public BaseDialog registerEvenbus(boolean register ){
        this.register=register;
        return this;
    }

    public void dismiss() {
        if(register)
        EventBus.getDefault().unregister(this);
        dialog.dismiss();
    }
}
