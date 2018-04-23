package me.oogh.similar.whisper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperMyTopicRecycleAdapter;
import me.oogh.similar.common.ActionModeCallback;
import me.oogh.similar.common.Actionable;
import me.oogh.similar.common.OnItemClickListener;
import me.oogh.similar.data.entry.Event;
import me.oogh.similar.data.entry.MyTopic;
import me.oogh.similar.data.entry.User;
import me.oogh.similar.message.MessageActivity;
import me.oogh.similar.murmur.MurmurActivity;
import me.oogh.similar.my.MyActivity;
import me.oogh.similar.utils.ActivityUtils;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-06
 * @description 心愿单
 */

public class WhisperActivity extends AppCompatActivity implements Actionable {

    private static final String TAG = WhisperActivity.class.getSimpleName();

    @BindView(R.id.tb_whisper_main_activity)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tv_empty_whisper_main)
    TextView mEmptyView;
    @BindView(R.id.rv_whisper_main_activity)
    RecyclerView mWhisperListView;
    @BindView(R.id.btn_whisper_main_activity)
    Button mButtonView;

    private List<MyTopic> mDataSet;
    private WhisperMyTopicRecycleAdapter mAdapter;

    private ActionMode mActionMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_activity);
        ButterKnife.bind(this);
        setupToolbar();
        setupDrawerLayout();
        mAdapter = new WhisperMyTopicRecycleAdapter(this, mDataSet = new ArrayList<>());
        mAdapter.setClearedListener(() -> {
            mEmptyView.setVisibility(View.VISIBLE);
            mWhisperListView.setVisibility(View.GONE);
        });
        mWhisperListView.setLayoutManager(new LinearLayoutManager(this));
        mWhisperListView.setAdapter(mAdapter);
        mWhisperListView.addOnItemTouchListener(new OnItemClickListener(this, mWhisperListView, new OnItemClickListener.Handler() {
            @Override
            public void handleClick(View view, int position) {
                if (mActionMode != null) {
                    handleItemTouched(view, position);
                } else {
                    Intent intent = new Intent(WhisperActivity.this, WhisperTopicDetailActivity.class);
                    intent.putExtra("myTopic", mDataSet.get(position));
                    startActivity(intent);
                }
            }

            @Override
            public void handleLongPress(View view, int position) {
                handleItemTouched(view, position);
            }
        }));
    }

    private void handleItemTouched(View view, int position) {
        mAdapter.toggleSelectStatus(position);
        boolean hasItemSelected = mAdapter.getSelectedItemCount() > 0;
        if (hasItemSelected && mActionMode == null) {
            mActionMode = (startSupportActionMode(new ActionModeCallback(this, tag -> {
                switch (tag) {
                    case DELETE:
                        removeItems();
                        break;
                }
            })));
        } else if (!hasItemSelected && mActionMode != null) {
            mActionMode.finish();
        }

        if (mActionMode != null) {
            mActionMode.setTitle(mAdapter.getSelectedItemCount() + " 项被选中");
        }
    }

    /**
     * 删除选中的Items
     */
    private void removeItems() {
        SparseBooleanArray selectedItems = mAdapter.getSelectedItems();
        int count = selectedItems.size();
        for (int i = (count - 1); i >= 0; i--) {
            if (selectedItems.valueAt(i)) {
                MyTopic topic = mDataSet.get(selectedItems.keyAt(i));
                topic.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i(TAG, "done: 删除成功...");
                        } else {
                            Log.i(TAG, "done: 删除失败: " + e);
                        }
                    }
                });
                mDataSet.remove(selectedItems.keyAt(i));
            }
        }
        mAdapter.updateTopics(mDataSet);
        Toast.makeText(this, count + " 条数据已被删除", Toast.LENGTH_SHORT).show();
        mActionMode.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobQuery<MyTopic> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser().getObjectId());
        query.findObjects(new FindListener<MyTopic>() {
            @Override
            public void done(List<MyTopic> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        mAdapter.updateTopics(mDataSet = list);
                        mEmptyView.setVisibility(View.GONE);
                        mWhisperListView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(WhisperActivity.this, "获取信息失败", Toast.LENGTH_SHORT).show();
                    mEmptyView.setVisibility(View.VISIBLE);
                    mWhisperListView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_menu);
        mToolbar.setNavigationOnClickListener(view -> mDrawerLayout.openDrawer(GravityCompat.START));
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
                ActivityUtils.navigateToWithNoFinish(this, WhisperAddTopicActivity.class);
                break;
        }
        return true;
    }

    @OnClick(R.id.btn_whisper_main_activity)
    public void onViewClicked() {
        ActivityUtils.navigateToWithNoFinish(this, WhisperAllActivity.class);
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

    @Subscribe(sticky = true)
    public void onWhisperTopicSelected(Event.WhisperTopicSelectedEvent event) {
        if (event.tag == Event.Tag.WHISPER_TOPIC_SELECTED) {
            MyTopic topic = new MyTopic(event.topic.getTitle());
            BmobRelation relation = new BmobRelation();
            User user = BmobUser.getCurrentUser(User.class);
            relation.add(user);
            topic.setUser(relation);

            BmobQuery<MyTopic> query = new BmobQuery<>();
            query.addWhereEqualTo("title", event.topic.getTitle());
            query.findObjects(new FindListener<MyTopic>() {
                @Override
                public void done(List<MyTopic> list, BmobException e) {
                    if (e == null && list != null && list.size() > 0) {
                        Toast.makeText(WhisperActivity.this, "[" + event.topic.getTitle() + "] 已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        topic.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Log.i(TAG, "done: 保存成功...");
                                } else {
                                    Log.i(TAG, "done: 保存失败...");
                                }
                            }
                        });
                        mDataSet.add(topic);
                        mAdapter.updateTopics(mDataSet);
                    }
                }
            });
        }
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void destroyActionMode() {
        mActionMode = mActionMode == null ? mActionMode : null;
        mAdapter.clearSelectedItems();
    }
}
