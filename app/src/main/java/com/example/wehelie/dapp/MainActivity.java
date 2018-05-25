package com.example.wehelie.dapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements MatchesFragment.OnListFragmentInteractionListener {

    private static String name, username, email, age, occupation, description;
    private static String Name;
    private static String Username;
    private static String Age;
    private static String Email;
    private static String Occupation;
    private static String Description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        Intent intent = getIntent();
        if(intent != null) {
            Bundle b = intent.getExtras();

            assert b != null;
            if (b != null) {
                name = b.getString(Constants.KEY_NAME);
                username = b.getString(Constants.KEY_USERNAME);
                email = b.getString(Constants.KEY_EMAIL);
                age = b.getString(Constants.KEY_AGE);
                occupation = b.getString(Constants.KEY_OCCUPATION);
                description = b.getString(Constants.KEY_DESCRIPTION);
            }
        }

        setupViewPager(viewPager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }



    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager(), Name, Username, Age, Email, Occupation, Description);
        adapter.addFragment(new ProfileFragment(), Constants.KEY_PROFILE);
        adapter.addFragment(new MatchesFragment(), Constants.KEY_MATCHES);
        adapter.addFragment(new SettingFragment(), Constants.KEY_SETTINGS);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onListFragmentInteraction(MatchesObject matches) {

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager, String name, String username, String age, String email, String occupation, String description) {
            super(manager);
            Name = name;
            Username = username;
            Age = age;
            Email = email;
            Occupation = occupation;
            Description = description;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            Bundle bundle = new Bundle();

            bundle.putString(Constants.KEY_NAME, name);
            bundle.putString(Constants.KEY_USERNAME, username);
            bundle.putString(Constants.KEY_AGE, age);
            bundle.putString(Constants.KEY_EMAIL, email);
            bundle.putString(Constants.KEY_OCCUPATION, occupation);
            bundle.putString(Constants.KEY_DESCRIPTION, description);
            fragment.setArguments(bundle);

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
