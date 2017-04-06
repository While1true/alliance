package adapter;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.saibaizi.alliance.In.Sheet.AliianceOrderDetail;
import com.saibaizi.alliance.In.Sheet.SheetF;
import com.saibaizi.alliance.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import Constance.GlobalDatas;
import Constance.NetUrl;
import Dialogs.AlliTXDialog;
import base.BaseUI;
import bean.AllTXBean;
import bean.AllianceBean;
import listener.HttpResultListener;
import util.PublicUtils;


/**
 * Created by S0005 on 2017/1/10.
 */

public class AllianceAdapter extends RecyclerView.Adapter {
    public static final int MONEY_DETAIL = 0, ACCOUNT_DETAIL = 1, NOTICE = 2, PRPRINCIPLE = 3;

    public static boolean MONEY_ANIMATION = true;
    private AllianceBean allianceBean;
    private BaseUI context;
    private SheetF sheetF;
    boolean isnomore, notice;
    AllTXBean TXInfo;
    private MoneyViewPagerAdapter adapter;

    public void clear(){
           allianceBean=null;
        context=null;
        sheetF=null;
        TXInfo=null;
        adapter=null;
    }
    public AllianceAdapter(SheetF sheetF) {
        this.sheetF=sheetF;
        this.context = (BaseUI) sheetF.getActivity();
    }


    public void setBean(AllianceBean allianceBean) {
        this.allianceBean = allianceBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MONEY_DETAIL:
                return new MoneyViewHolder(View.inflate(context, R.layout.sheet_tixian, null));
            default:
               return null;
        }

    }

    public void showNotice(boolean notice) {
        this.notice = notice;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (allianceBean == null)
            return;
        if (holder instanceof MoneyViewHolder) {
            setMoneyData((MoneyViewHolder) holder, position);
        }
    }



    public void startMoneyAnimation(final DecimalFormat format, String money, final MoneyViewHolder holder) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, Float.parseFloat(money));
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                final String str = FormatStr(format, (double) value);
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(GlobalDatas.TAG, "run: " + str);
                        holder.tv_tixian.setText(str);
                    }
                });

            }
        });
        valueAnimator.setStartDelay(50);
        valueAnimator.start();
    }

    private void setMoneyData(final MoneyViewHolder holder, int position) {

        if (adapter == null)
            adapter = new MoneyViewPagerAdapter(context.getSupportFragmentManager());
        holder.viewPager.setAdapter(adapter);
        holder.viewPager.setOffscreenPageLimit(1);
        holder.viewPager.setCurrentItem(0);
        holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        holder.rg_group.check(R.id.rb_today);
//                        holder.rg_indicate.check(R.id.rg_indicate01);
                        break;
                    case 1:
                        holder.rg_group.check(R.id.rb_yesterday);
//                        holder.rg_indicate.check(R.id.rg_indicate02);
                        break;
                    case 2:
                        holder.rg_group.check(R.id.rb_month);
//                        holder.rg_indicate.check(R.id.rg_indicate03);
                        break;
                    case 3:
                        holder.rg_group.check(R.id.rb_lastmonth);
//                        holder.rg_indicate.check(R.id.rg_indicate04);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        holder.rg_group.check(R.id.rb_today);

        if (notice) {
            holder.nn.setVisibility(View.VISIBLE);
            holder.bt_tixian.setText("已申请");
            holder.bt_tixian.setEnabled(false);
        } else {
            holder.nn.setVisibility(View.GONE);
            holder.bt_tixian.setText("立即提现");
            holder.bt_tixian.setEnabled(true);
        }
        String str = allianceBean.getWait_money();
        if (str == null)
            str = "0";
        str = String.format("%.1f", Double.parseDouble(str));
        final DecimalFormat format = new DecimalFormat(getSimple(str));
        if (MONEY_ANIMATION) {
            MONEY_ANIMATION = false;
            startMoneyAnimation(format, str, holder);
        } else
            holder.tv_tixian.setText(format.format(Double.valueOf(str)));
    }

    public String getSimple(String str) {
        switch (str.length()) {
            case 2:
                return "00";
            case 3:
                return "0.0";
            case 4:
                return "00.0";
            case 5:
                return "000.0";
            case 6:
                return "0,000.0";
            case 7:
                return "00,000.0";
            case 8:
                return "000,000.0";
            case 9:
                return "0,000,000.0";
            case 10:
                return "00,000,000.0";
            default:
                return "0.0";
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return MONEY_DETAIL;
            case 1:
                return ACCOUNT_DETAIL;
            case 2:
                return NOTICE;
            case 3:
                return PRPRINCIPLE;
            default:
                return -1;
        }
    }

    public void setTXInfo(AllTXBean TXInfo) {
        this.TXInfo = TXInfo;
    }


    class MoneyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
        TextView tv_tixian, bt_tixian, bt_mingxi;
        RadioGroup rg_group, rg_indicate;
        ViewPager viewPager;
        View nn;
        int lasti = -1;


        public MoneyViewHolder(View itemView) {
            super(itemView);
            tv_tixian = (TextView) itemView.findViewById(R.id.tv_tixian);
            bt_tixian = (TextView) itemView.findViewById(R.id.bt_tixian);
            bt_mingxi = (TextView) itemView.findViewById(R.id.bt_mingxi);

            bt_tixian.setOnClickListener(this);
            bt_mingxi.setOnClickListener(this);

            nn = itemView.findViewById(R.id.nnnn);
            rg_group = (RadioGroup) itemView.findViewById(R.id.rg_group);
            rg_group.setOnCheckedChangeListener(this);
            rg_indicate = (RadioGroup) itemView.findViewById(R.id.rg_indicate);

            viewPager = (ViewPager) itemView.findViewById(R.id.viewpager);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_tixian:
                    try {
                        if (TXInfo == null || TXInfo.getStatus().equals("1")|| Float.parseFloat(allianceBean.getWait_money())==0) {
                            Toast.makeText(view.getContext(), "您没有可提现额度", 0).show();
                            return;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(view.getContext(), "您没有可提现额度", 0).show();
                        return;
                    }
                    new AlliTXDialog(view.getContext())
                            .builder()
                            .setDate(TXInfo.getU_cardtype().equals("2") ? "支付宝账户" : "银行账户", TXInfo.getU_cardnumber(),
                                    TXInfo.getU_realname(), TXInfo.getMoney())
                            .setBtnPosListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (GlobalDatas.DEBUG)
                                        Log.i(GlobalDatas.TAG, "onClick: ");
                                    doTX(v);
                                }
                            })
                            .show();
//                    context.startActivityForResult(new Intent(context, DepositeUI.class), AllianceUI.DEPOSITE_RESULT);
                    break;
                case R.id.bt_mingxi:
                    context.startActivity(new Intent(context, AliianceOrderDetail.class));
                    break;
            }
        }

        private void doTX(final View v) {
            String token = ((BaseUI) v.getContext()).getToken();
            RequestParams params = new RequestParams();
            params.put("token", token);
            params.put("type", 2);
            PublicUtils.postHttp(v.getContext().getApplicationContext(), NetUrl.ALLIANCE_TX, params, new HttpResultListener() {
                @Override
                public void success(int code, String message, String resultCode) {
                    if (GlobalDatas.DEBUG)
                        Log.i(GlobalDatas.TAG, "success: " + resultCode);

                   sheetF.getTXmessage();
                }
            });
        }

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i == lasti) {
                return;
            }
            Log.i(GlobalDatas.TAG, "onCheckedChanged: " + i);
            setTextStyle(itemView, lasti, i);
            lasti = i;
            int temp = viewPager.getCurrentItem();
            switch (i) {
                case R.id.rb_today:
                    if (temp != 0)
                        viewPager.setCurrentItem(0);
                    rg_indicate.check(R.id.rg_indicate01);
                    break;
                case R.id.rb_yesterday:
                    if (temp != 1)
                        viewPager.setCurrentItem(1);
                    rg_indicate.check(R.id.rg_indicate02);
                    break;
                case R.id.rb_month:
                    if (temp != 2)
                        viewPager.setCurrentItem(2);
                    rg_indicate.check(R.id.rg_indicate03);
                    break;
                case R.id.rb_lastmonth:
                    if (temp != 3)
                        viewPager.setCurrentItem(3);
                    rg_indicate.check(R.id.rg_indicate04);
                    break;
            }
        }
    }

    public class NoMoreViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nomore;

        public NoMoreViewHolder(View itemView) {
            super(itemView);
//            tv_nomore = (TextView) itemView.findViewById(R.id.tv_nomore);
        }
    }


    public void setTextStyle(View item, int pre, int now) {
        //初次时设置选中为粗体
        if (pre != -1) {
            RadioButton prev = (RadioButton) item.findViewById(pre);
            prev.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
            prev.setTextColor(0xff888888);
            prev.getPaint().setFakeBoldText(false);
        }
        RadioButton nowv = (RadioButton) item.findViewById(now);
        nowv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        nowv.setTextColor(0xff535353);
        nowv.getPaint().setFakeBoldText(true);
        Log.i(GlobalDatas.TAG, "setTextStyle: ");

    }


    public String FormatStr(DecimalFormat format, Double str) {
        return format.format(str);
    }

    //保存qr图片
    private void saveQrBitmap(Bitmap bitmap, File file) {
        try {
            file.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            // 把文件插入到系统图库
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
            //发送广播
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            context.sendBroadcast(intent);
            Toast.makeText(context, "qr码保存成功，进入相册查看", 0).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "保存失败", 0).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(context, "保存失败", 0).show();
            e.printStackTrace();
        }

    }
}
