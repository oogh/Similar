package me.oogh.similar.whisper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperViewPagerAdapter;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-06
 * @description 心愿星河
 */

public class WhisperAllActivity extends AppCompatActivity {

    @BindView(R.id.tb_whisper_activity)
    Toolbar mToolbar;
    @BindView(R.id.vp_whisper)
    ViewPager mViewPager;
    @BindView(R.id.btm_nav_whisper)
    BottomNavigationView mBottomNavigationView;

    private WhisperViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_main_activity);
        ButterKnife.bind(this);
        setupToolbar();
        mAdapter = new WhisperViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        setupBottomNavigationWithViewPager();
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whisper_add_to_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 掷入心愿星河
            case R.id.action_add_to_whisper_list:
                // 掷入心愿星河
                startActivity(new Intent(WhisperAllActivity.this, SelectWhisperActivity.class));
                break;
        }
        return true;
    }


    /**
     * 连接 BottomNavigation 和 ViewPager
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

}
