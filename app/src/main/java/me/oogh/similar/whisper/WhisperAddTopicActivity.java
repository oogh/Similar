package me.oogh.similar.whisper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperTopicRecycleAdapter;
import me.oogh.similar.common.OnItemClickListener;
import me.oogh.similar.data.entry.Event;
import me.oogh.similar.data.entry.Topic;

import static me.oogh.similar.data.entry.Event.Tag.WHISPER_TOPIC_SELECTED;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-06
 * @description 创建心愿主题
 */

public class WhisperAddTopicActivity extends AppCompatActivity {
    private static final String TAG = WhisperAddTopicActivity.class.getSimpleName();

    @BindView(R.id.tb_whisper_add_topic_activity)
    Toolbar mToolbar;
    @BindView(R.id.et_add_whisper_topic)
    EditText mTopicEditView;
    @BindView(R.id.rv_whisper_add_topic_list)
    RecyclerView mTopicListView;

    private WhisperTopicRecycleAdapter mAdapter;
    private List<Topic> mDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_add_topic_activity);
        ButterKnife.bind(this);
        setupToolbar();
        mAdapter = new WhisperTopicRecycleAdapter(mDataSet = new ArrayList<>());
        mTopicListView.setLayoutManager(new LinearLayoutManager(this));
        mTopicListView.setAdapter(mAdapter);
        mTopicListView.addOnItemTouchListener(new OnItemClickListener(
                this,
                mTopicListView,
                new OnItemClickListener.DelegateHandler() {
                    @Override
                    public void handleClick(View view, int position) {
                        EventBus.getDefault()
                                .postSticky(new Event.WhisperTopicSelectedEvent(mDataSet.get(position), WHISPER_TOPIC_SELECTED));
                        finish();
                    }
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobQuery<Topic> query = new BmobQuery<>();
        query.findObjects(new FindListener<Topic>() {
            @Override
            public void done(List<Topic> list, BmobException e) {
                if (e == null) {
                    mAdapter.updateTopics(mDataSet = list);
                } else {
                    Toast.makeText(WhisperAddTopicActivity.this, "获取心愿单失败", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "done: 获取心愿单失败: " + e);
                }
            }
        });
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whisper_topic_action_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_whisper_topic_done:
                EventBus.getDefault()
                        .postSticky(new Event.WhisperTopicSelectedEvent(
                                new Topic(mTopicEditView.getText().toString()),
                                WHISPER_TOPIC_SELECTED));
                finish();
                break;
        }
        return true;
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
