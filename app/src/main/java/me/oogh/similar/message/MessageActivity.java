package me.oogh.similar.message;

import android.graphics.drawable.Drawable;
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
import me.oogh.similar.murmur.MurmurActivity;
import me.oogh.similar.my.MyActivity;
import me.oogh.similar.utils.ActivityUtils;
import me.oogh.similar.whisper.WhisperMainActivity;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-04
 * @description
 */
public class MessageActivity extends AppCompatActivity {

    @BindView(R.id.tv_message_activity_title)
    TextView mTitleView;
    @BindView(R.id.tb_message_activity)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private MessagePresenter mMessagePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        ButterKnife.bind(this);
        setupToolbar();
        setupDrawerLayout();

        // 创建Fragment
        MessageFragment messageFragment = (MessageFragment) getSupportFragmentManager().findFragmentById(R.id.fl_message_container);
        if (messageFragment == null) {
            messageFragment = MessageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), messageFragment, R.id.fl_message_container);
        }

        // 创建Presenter
        mMessagePresenter = new MessagePresenter(messageFragment);


    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        switchStatusDrawable(R.drawable.ic_status);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationOnClickListener(view -> mDrawerLayout.openDrawer(GravityCompat.START));
    }

    /**
     * 状态图标的切换
     *
     * @param resId
     */
    private void switchStatusDrawable(int resId) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = getResources().getDrawable(resId, null);
            mTitleView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }

    private void setupDrawerLayout() {
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_message:
                    break;
                case R.id.action_murmur:
                    ActivityUtils.navigateTo(MessageActivity.this, MurmurActivity.class);
                    break;
                case R.id.action_whisper:
                    ActivityUtils.navigateTo(MessageActivity.this, WhisperMainActivity.class);
                    break;
                case R.id.action_my:
                    ActivityUtils.navigateTo(MessageActivity.this, MyActivity.class);
                    break;
                default:
                    break;
            }
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
    }


    /**
     * 标题栏 登录状态切换
     */
    @OnClick(R.id.tv_message_activity_title)
    public void onViewClicked() {

    }
}
