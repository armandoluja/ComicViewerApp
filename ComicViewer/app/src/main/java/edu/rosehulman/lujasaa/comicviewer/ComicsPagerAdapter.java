package edu.rosehulman.lujasaa.comicviewer;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import edu.rosehulman.lujasaa.comicviewer.ComicFragment;
import edu.rosehulman.lujasaa.comicviewer.Utils;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ComicsPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Integer> mColors = new ArrayList<>();
    private ArrayList<ComicWrapper> mWrappers = new ArrayList<>();
    private Context mContext;
    private int mCurrentColor = 0;

    public ComicsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        mColors.add(Color.GREEN);
        mColors.add(Color.BLUE);
        mColors.add(Color.YELLOW);
        mColors.add(Color.RED);
        //initialize with 5 random comics using Dr. B's list of clean xkcd issues
        for(int i = 0 ; i < 5 ; i ++) {
            mWrappers.add(new ComicWrapper(Utils.getRandomCleanIssue(), mColors.get(mCurrentColor % mColors.size())));
            mCurrentColor++;
        }
    }

    public void addComic(){
        mWrappers.add(new ComicWrapper(Utils.getRandomCleanIssue(), mColors.get(mCurrentColor % mColors.size())));
        mCurrentColor++;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a ComicFragment (defined as a static inner class below).
        return ComicFragment.newInstance(mWrappers.get(position));
    }

    @Override
    public int getCount() {
        return mWrappers.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getString(R.string.issue)+ mWrappers.get(position).getXkcdIssue();
    }
}