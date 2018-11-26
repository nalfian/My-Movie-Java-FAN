package com.example.toshiba.mymovie.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.toshiba.mymovie.R;
import com.example.toshiba.mymovie.view.fragment.NowPlayFragment;
import com.example.toshiba.mymovie.view.fragment.UpComingFragment;

public class MoviePager extends FragmentPagerAdapter {

    private static final String TAG = MoviePager.class.getSimpleName();

    private static final int FRAGMENT_COUNT = 2;
    private Context con;

    public MoviePager(FragmentManager fm, Context context) {
        super(fm);
        con = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UpComingFragment();
            case 1:
                return new NowPlayFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return con.getString(R.string.upcoming);
            case 1:
                return  con.getString(R.string.nowplay);
        }
        return null;
    }
}
