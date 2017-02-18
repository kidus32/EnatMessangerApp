package com.enatbank.enatmessanger.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.enatbank.enatmessanger.fragements.Chats;
import com.enatbank.enatmessanger.fragements.Friends;

/**
 * Created by btinsae on 14/02/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    private int tabCount;

    public ViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Chats();
            case 1:
                return new Friends();
        }

        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
