package com.myeteam.muffinmad.myeteam;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.myeteam.muffinmad.myeteam.Adapters.MyETeamsPageAdapter;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamDiscFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamMembersFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamStratsFragment;
import com.myeteam.muffinmad.myeteam.fragments.MyETeamsFragment;

public class MyETeamActivity extends AppCompatActivity implements
        MyETeamsFragment.OnFragmentInteractionListener,
        MyETeamMembersFragment.OnFragmentInteractionListener,
        MyETeamDiscFragment.OnFragmentInteractionListener,
        MyETeamStratsFragment.OnFragmentInteractionListener{

    private MyETeamsPageAdapter pageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_eteam);

        pageAdapter = new MyETeamsPageAdapter(this.getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.myeteam_pager);
        mViewPager.setAdapter(pageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.myeteam_tabs);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
