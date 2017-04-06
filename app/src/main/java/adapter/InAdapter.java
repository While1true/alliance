package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.saibaizi.alliance.In.Home.HomeF;
import com.saibaizi.alliance.In.Mine.MineF;
import com.saibaizi.alliance.In.Sheet.SheetF;

/**
 * Created by S0005 on 2017/4/6.
 */

public class InAdapter extends FragmentPagerAdapter {
    public InAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new HomeF();
        else if (position == 1)
            return new SheetF();
        else return new MineF();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
