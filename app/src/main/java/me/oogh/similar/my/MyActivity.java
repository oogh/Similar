package me.oogh.similar.my;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.oogh.similar.R;
import me.oogh.similar.message.MessageActivity;
import me.oogh.similar.murmur.MurmurActivity;
import me.oogh.similar.utils.ActivityUtils;
import me.oogh.similar.whisper.WhisperActivity;

public class MyActivity extends AppCompatActivity {

    @BindView(R.id.tv_my_activity_title)
    TextView mTitleView;
    @BindView(R.id.tb_my_activity)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private MyPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        ButterKnife.bind(this);
        setupToolbar();
        setupDrawerLayout();

        MyFragment myFragment = (MyFragment) getSupportFragmentManager().findFragmentById(R.id.fl_my_container);
        if (myFragment == null) {
            myFragment = MyFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), myFragment, R.id.fl_my_container);
        }

        mPresenter = new MyPresenter(myFragment);
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
                    ActivityUtils.navigateTo(MyActivity.this, MessageActivity.class);
                    break;
                case R.id.action_murmur:
                    ActivityUtils.navigateTo(MyActivity.this, MurmurActivity.class);
                    break;
                case R.id.action_whisper:
                    ActivityUtils.navigateTo(MyActivity.this, WhisperActivity.class);
                    break;
                case R.id.action_my:

                    break;
                default:
                    break;
            }
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
    }


    @OnClick(R.id.tv_my_activity_title)
    public void onViewClicked() {
    }
}
