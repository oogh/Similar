package me.oogh.similar.whisper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperAdapter;
import me.oogh.similar.common.OnItemClickListener;
import me.oogh.similar.common.Swipe2DeleteCallback;
import me.oogh.similar.data.entry.Event;
import me.oogh.similar.data.entry.MyTopic;
import me.oogh.similar.data.entry.Whisper;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-06
 * @description 某心愿主题详情
 */

public class WhisperTopicDetailActivity extends AppCompatActivity {
    private static final String TAG = WhisperTopicDetailActivity.class.getSimpleName();
    private static final Integer REQUEST_CODE = 1;

    @BindView(R.id.tb_whisper_topic_detail_activity)
    Toolbar mToolbar;
    @BindView(R.id.tv_whisper_topic_detail_title)
    TextView mTitleView;
    @BindView(R.id.tv_whisper_topic_detail_count)
    TextView mCountView;
    @BindView(R.id.rv_whisper_topic_detail_list)
    RecyclerView mWhisperListView;

    private MyTopic mMyTopic;
    private List<Whisper> mDataSet;
    private WhisperAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_topic_detail_activity);
        ButterKnife.bind(this);
        setupToolbar();

        Intent intent = getIntent();
        mMyTopic = (MyTopic) intent.getSerializableExtra("myTopic");
        mTitleView.setText(mMyTopic.getTitle());
        mCountView.setText("-2个心愿-");

        mAdapter = new WhisperAdapter(mDataSet = new ArrayList<>());
        mWhisperListView.setLayoutManager(new LinearLayoutManager(this));
        mWhisperListView.setAdapter(mAdapter);
        mWhisperListView.addOnItemTouchListener(new OnItemClickListener(this, mWhisperListView, new OnItemClickListener.DelegateHandler() {
            @Override
            public void handleClick(View view, int position) {
                // 单击跳转到编辑页面
                Intent intent = new Intent(WhisperTopicDetailActivity.this, EditWhisperActivity.class);
                intent.putExtra("whisper", mDataSet.get(position));
                startActivityForResult(intent, REQUEST_CODE);
            }
        }));
        ItemTouchHelper helper = new ItemTouchHelper(new Swipe2DeleteCallback(this) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 滑动删除
                Whisper whisper = mDataSet.get(viewHolder.getAdapterPosition());
                mAdapter.removeAt(viewHolder.getAdapterPosition());
                whisper.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(WhisperTopicDetailActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(WhisperTopicDetailActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "done: 删除失败", e);
                        }
                    }
                });
            }
        });
        helper.attachToRecyclerView(mWhisperListView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listWhisper();
    }

    private void listWhisper() {
        BmobQuery<Whisper> query = new BmobQuery<>();
        query.addWhereEqualTo("topic", mMyTopic);
        query.include("topic");
        query.findObjects(new FindListener<Whisper>() {
            @Override
            public void done(List<Whisper> list, BmobException e) {

                if (e == null) {
                    if (list != null && list.size() > 0) {
                        mAdapter.updateDataSet(mDataSet = list);
                    } else {
                        showEmpty();
                    }
                } else {
                    Toast.makeText(WhisperTopicDetailActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "done: 获取数据失败: " + e);
                }
            }
        });
    }

    private void showEmpty() {
        Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whisper_main_action_items, menu);
        return true;
    }

    /**
     * 添加心愿
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_whisper_main_create:
                Intent intent = new Intent(this, AddWhisperActivity.class);
                intent.putExtra("myTopic", mMyTopic);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Whisper whisper = (Whisper) data.getSerializableExtra("whisper");
                whisper.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e != null) {
                            Toast.makeText(WhisperTopicDetailActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "done: 更新失败", e);
                        }

                    }
                });
                Toast.makeText(this, whisper.getContent(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Subscribe(sticky = true)
    public void onSaveWhisper(Event.WhisperEvent event) {
        if (event.tag == Event.Tag.WHISPER_ADD_COMPLETED) {
            event.whisper.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Toast.makeText(WhisperTopicDetailActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(WhisperTopicDetailActivity.this, "保存失败 [" + e.getErrorCode() + "]", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "done: 保存whisper失败: " + e);
                    }
                }
            });
            // 如果没有移除 sticky event 会导致该方法重复执行
            EventBus.getDefault().removeStickyEvent(event);
            listWhisper();
        }
    }
}
