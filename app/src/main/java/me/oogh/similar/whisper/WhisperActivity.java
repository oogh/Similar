package me.oogh.similar.whisper;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperViewPagerAdapter;
import me.oogh.similar.message.MessageActivity;
import me.oogh.similar.murmur.MurmurActivity;
import me.oogh.similar.my.MyActivity;
import me.oogh.similar.utils.ActivityUtils;

public class WhisperActivity extends AppCompatActivity {

    @BindView(R.id.tb_whisper_activity)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.vp_whisper)
    ViewPager mViewPager;
    @BindView(R.id.btm_nav_whisper)
    BottomNavigationView mBottomNavigationView;

    private WhisperViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_activity);
        ButterKnife.bind(this);
        setupToolbar();
        setupDrawerLayout();
        mAdapter = new WhisperViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        setupBottomNavigationWithViewPager();
    }

    /**
     * connect BottomNavigation to ViewPager
     */
    private void setupBottomNavigationWithViewPager() {


        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_message:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.action_murmur:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.action_whisper:
                    mViewPager.setCurrentItem(2);
                    break;
                default:
                    break;
            }
            return true;
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            MenuItem preMenu = null;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (preMenu != null) {
                    preMenu.setChecked(false);
                } else {
                    mBottomNavigationView.getMenu().getItem(0).setChecked(true);
                }

                mBottomNavigationView.getMenu().getItem(position).setChecked(true);
                preMenu = mBottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationOnClickListener(view -> mDrawerLayout.openDrawer(GravityCompat.START));
    }

    private void setupDrawerLayout() {
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_message:
                    ActivityUtils.navigateTo(WhisperActivity.this, MessageActivity.class);
                    break;
                case R.id.action_murmur:
                    ActivityUtils.navigateTo(WhisperActivity.this, MurmurActivity.class);
                    break;
                case R.id.action_whisper:
                    break;
                case R.id.action_my:
                    ActivityUtils.navigateTo(WhisperActivity.this, MyActivity.class);
                    break;
                default:
                    break;
            }
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
    }
}
