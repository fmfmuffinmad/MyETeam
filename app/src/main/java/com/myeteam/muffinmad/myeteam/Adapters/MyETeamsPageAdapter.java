package com.myeteam.muffinmad.myeteam.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.myeteam.muffinmad.myeteam.fragments.MyETeamDiscFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamMembersFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamStratsFragment;

/**
 * Created by muffinmad on 26/10/2016.
 */

public class MyETeamsPageAdapter extends FragmentStatePagerAdapter {
    public MyETeamsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
//        Fragment fragment = new MyETeamMembersFragment();
//        Bundle args = new Bundle();
//        // Our object is just an integer :-P
//        args.putInt(MyETeamMembersFragment.ARG_OBJECT, i + 1);
//        fragment.setArguments(args);
        Fragment fragment = null;
        switch (i){
            case 0:
                fragment = new MyETeamMembersFragment();
                break;
            case 1:
                fragment = new MyETeamStratsFragment();
                break;
            case 2:
                fragment = new MyETeamDiscFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = "";
        switch (position){
            case 0:
                title = "Members";
                break;
            case 1:
                title = "Strats";
                break;
            case 2:
                title = "Discution";
        }
        return title;
    }

}
