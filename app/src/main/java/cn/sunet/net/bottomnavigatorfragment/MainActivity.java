package cn.sunet.net.bottomnavigatorfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:  Leland
 * Emial:   AC_Dreamer@163.com
 * Website: www.sunet.net.cn
 * Date:    2017/8/15
 * Function: main activity
 */


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private MenuItem menuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.navigation_me:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    //when user click the bottom navigator view change the view's focus
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //if the item is not null , now set it check false
            if (menuItem != null)
                menuItem.setChecked(false);
            else //set the first bottom view check false
                navigation.getMenu().getItem(0).setChecked(false);

            // get the current select menu item and then focus it
            menuItem = navigation.getMenu().getItem(position);
            menuItem.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //disable sliding page
        viewPager.setSlidingEnable(false);

        //remove shiftMode animation
        BottomNavigationViewHelper.removeShifMode(navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    private void setupViewPager(ViewPager viewPager) {
        List<Fragment> fragmentList = new ArrayList<>();

        // add four fragment page to list as the viewpager content
        fragmentList.add(NewFragment.newInstance(getString(R.string.title_home)));
        fragmentList.add(NewFragment.newInstance(getString(R.string.title_dashboard)));
        fragmentList.add(NewFragment.newInstance(getString(R.string.title_notifications)));
        fragmentList.add(NewFragment.newInstance(getString(R.string.title_me)));

        FragmentPageAdapter adapter = new FragmentPageAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
    }
}
