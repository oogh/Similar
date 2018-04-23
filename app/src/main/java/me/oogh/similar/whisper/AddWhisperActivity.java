package me.oogh.similar.whisper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.Event;
import me.oogh.similar.data.entry.MyTopic;
import me.oogh.similar.data.entry.Whisper;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-16
 * @description 添加心愿
 */
public class AddWhisperActivity extends AppCompatActivity {


    @BindView(R.id.tb_whisper_add_activity)
    Toolbar mToolbar;
    @BindView(R.id.et_whisper_add_activity)
    EditText mEditWhisperView;

    private MyTopic mMyTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_add_activity);
        mMyTopic = (MyTopic) getIntent().getSerializableExtra("myTopic");
        ButterKnife.bind(this);
        setupToolbar();
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whisper_add_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_whisper_done:
                String content = mEditWhisperView.getText().toString();
                Whisper whisper = new Whisper(content, mMyTopic);
                EventBus.getDefault().postSticky(
                        new Event.WhisperEvent(whisper, Event.Tag.WHISPER_ADD_COMPLETED));
                finish();
                break;
        }
        return true;
    }
}
