/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package Dialogs;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saibaizi.alliance.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.Utils;

/**
 * Created by S0005 on 2017/3/20.
 */

public class AlliTXDialog extends BaseDialog {

    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.btn_neg)
    Button btnNeg;
    @BindView(R.id.btn_pos)
    Button btnPos;

    public AlliTXDialog(Context context) {
        super(context);
    }

    @Override
    public AlliTXDialog builder() {
        View view = View.inflate(context, R.layout.dialog_alli_tx, null);
        ButterKnife.bind(this,view);
        dialog.setContentView(view);
        dialog.getWindow().getAttributes().width= Utils.dp2px(context,258);
        return this;
    }

    public AlliTXDialog setDate(String AccountType, String AccountNum, String AccountName, String moneyx){
        tvAccount.setText(AccountType);
        account.setText(AccountNum);
        name.setText(AccountName);
        money.setText(moneyx);
        return this;
    }
    public AlliTXDialog setBtnPosListener(final View.OnClickListener listener){
        btnPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }


    @OnClick({R.id.btn_neg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_neg:
                dismiss();
                break;
        }
    }
}
