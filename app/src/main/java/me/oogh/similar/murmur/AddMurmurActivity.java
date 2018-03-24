package me.oogh.similar.murmur;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.Event;
import me.oogh.similar.data.entry.Murmur;
import me.oogh.similar.data.entry.User;
import me.oogh.similar.utils.DateUtils;

public class AddMurmurActivity extends AppCompatActivity {

    @BindView(R.id.tb_add_murmur_today_activity)
    Toolbar mToolbar;
    @BindView(R.id.et_add_murmur_today)
    EditText mAddMurmurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.murmur_add_activity);
        ButterKnife.bind(this);
        setupToolbar();
    }

    private void setupToolbar() {
        mToolbar.setTitle("写给" + DateUtils.format(new Date()));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.murmur_add_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_murmur_done:
                EventBus.getDefault().postSticky(new Event.MurmurEvent(
                        new Murmur(
                                BmobUser.getCurrentUser(User.class),
                                new Date(),
                                mAddMurmurView.getText().toString(),
                                "today"),
                        Event.Tag.MURMUR_ADD_COMPLETED));
                finish();
                break;
        }
        return true;
    }
}
