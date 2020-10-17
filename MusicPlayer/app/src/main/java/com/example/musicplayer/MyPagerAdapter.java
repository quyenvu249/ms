package com.example.musicplayer;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES =
            new int[] { R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3 };
    private final Context mContext;
    public MyPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }
    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SongsFragment songsFragment=SongsFragment.newInstance();
                return songsFragment;
            case 1:
                PlaylistFragment playlistFragment=PlaylistFragment.newInstance();
                return playlistFragment;
            case 2:
                ArtistsFragment artistsFragment=ArtistsFragment.newInstance();
                return artistsFragment;
            default:
                return null;
        }
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

}
