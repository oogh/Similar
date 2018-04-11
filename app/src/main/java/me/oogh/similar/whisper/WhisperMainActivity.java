package me.oogh.similar.whisper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.oogh.similar.R;
import me.oogh.similar.utils.ActivityUtils;

public class WhisperMainActivity extends AppCompatActivity {

    @BindView(R.id.tb_whisper_main_activity)
    Toolbar mToolbar;
    @BindView(R.id.tv_empty_whisper_main)
    TextView mEmptyView;
    @BindView(R.id.rv_whisper_main_activity)
    RecyclerView mWhisperListView;
    @BindView(R.id.btn_whisper_main_activity)
    Button mButtonView;
    @BindView(R.id.tv_whisper_main_activity_title)
    TextView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_main_activity);
        ButterKnife.bind(this);
        setupToolbar();
    }


    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whisper_main_action_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_whisper_main_create:
                ActivityUtils.navigateToWithNoFinish(this, WhisperTopicActivity.class);
                break;
        }
        return true;
    }

    @OnClick(R.id.btn_whisper_main_activity)
    public void onViewClicked() {
        ActivityUtils.navigateToWithNoFinish(this, WhisperActivity.class);
    }
}
