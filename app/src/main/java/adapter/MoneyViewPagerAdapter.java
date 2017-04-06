package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.saibaizi.alliance.In.Sheet.MoneyDetailF;

/**
 * Created by S0005 on 2017/1/11.
 */

public class MoneyViewPagerAdapter extends FragmentPagerAdapter {
    public static final int NUM = 4;

    public MoneyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return NUM;
    }

    @Override
    public Fragment getItem(int position) {
        return new MoneyDetailF().setType(position+1);
    }
}
