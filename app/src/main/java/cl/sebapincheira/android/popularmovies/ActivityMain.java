package cl.sebapincheira.android.popularmovies;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    private Toolbar vToolbar_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup toolbar
        vToolbar_main = (Toolbar) findViewById(R.id.xml_activity_main_toolbar); // Attaching the layout to the vToolbar_main object
        setSupportActionBar(vToolbar_main); // Setting vToolbar_main as the ActionBar with setSupportActionBar() call

        //Setup viewPager + Adapter + Fragments for this viewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.xml_activity_main_viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentMainMovieGrid(), "POPULAR");
        viewPagerAdapter.addFragment(new FragmentMainMovieGrid(), "POPULAR2");
        viewPagerAdapter.addFragment(new FragmentMainMovieGrid(), "POPULAR3");
        viewPager.setAdapter(viewPagerAdapter);

        //Setup Tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.xml_activity_main_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
