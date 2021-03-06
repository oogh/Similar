package me.oogh.similar.murmur;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.message.MessageActivity;
import me.oogh.similar.my.MyActivity;
import me.oogh.similar.utils.ActivityUtils;
import me.oogh.similar.whisper.WhisperActivity;

public class MurmurActivity extends AppCompatActivity {

    @BindView(R.id.tb_murmur_activity)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private MurmurPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.murmur_activity);
        ButterKnife.bind(this);
        setupToolbar();
        setupDrawerLayout();

        // 创建 Murmur Fragment
        MurmurFragment murmurFragment = (MurmurFragment) getSupportFragmentManager().findFragmentById(R.id.fl_murmur_container);
        if (murmurFragment == null) {
            murmurFragment = MurmurFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), murmurFragment, R.id.fl_murmur_container);
        }

        // 创建 Murmur Presenter
        mPresenter = new MurmurPresenter(murmurFragment);
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
                    ActivityUtils.navigateTo(MurmurActivity.this, MessageActivity.class);
                    break;
                case R.id.action_murmur:
                    break;
                case R.id.action_whisper:
                    ActivityUtils.navigateTo(MurmurActivity.this, WhisperActivity.class);
                    break;
                case R.id.action_my:
                    ActivityUtils.navigateTo(MurmurActivity.this, MyActivity.class);
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
