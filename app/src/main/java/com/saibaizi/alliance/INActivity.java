package com.saibaizi.alliance;

import android.support.annotation.IdRes;
import android.widget.RadioGroup;

import Views.NoScrollViewPager;
import adapter.InAdapter;
import base.BaseUI;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by S0005 on 2017/4/6.
 */

public class INActivity extends BaseUI {
    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.rg_in)
    RadioGroup rgIn;

    private long lastpress=0;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_in);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {

        InAdapter adapter=new InAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        rgIn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i){
                    case R.id.rb_home:
                        vp.setCurrentItem(0,false);
                        break;
                    case R.id.rb_sheet:
                        vp.setCurrentItem(1,false);
                        break;
                    case R.id.rb_mine:
                        vp.setCurrentItem(2,false);
                        break;
                }
            }
        });
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
}
