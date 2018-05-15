package me.oogh.similar.whisper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperSelectAdapter;
import me.oogh.similar.common.ActionModeCallback;
import me.oogh.similar.common.Actionable;
import me.oogh.similar.common.OnItemClickListener;
import me.oogh.similar.data.entry.AllWhisper;
import me.oogh.similar.data.entry.MyTopic;
import me.oogh.similar.data.entry.User;
import me.oogh.similar.data.entry.Whisper;


/**
 * @author oogh <oogh216@163.com>
 * @date 2018-04-06
 * @description 选择心语
 */
public class SelectWhisperActivity extends AppCompatActivity implements Actionable {
    private static final String TAG = SelectWhisperActivity.class.getSimpleName();

    @BindView(R.id.tb_whisper_select_activity)
    Toolbar mToolbar;
    @BindView(R.id.rv_whisper_list)
    RecyclerView mRecycleView;
    @BindView(R.id.pb_select_whisper_loading)
    ProgressBar mLoadingView;

    private List<Whisper> mDataSet;
    private WhisperSelectAdapter mAdapter;

    private Whisper mSelectedWhisper;

    private ActionMode mActionMode;

    private Handler mHandler = new Handler(msg -> {
        if (msg.obj instanceof MyTopic) {
            BmobQuery<Whisper> query = new BmobQuery<>();
            query.addWhereEqualTo("topic", (MyTopic) msg.obj);
            query.addWhereEqualTo("added", false);
            query.include("topic");
            query.findObjects(new FindListener<Whisper>() {
                @Override
                public void done(List<Whisper> list, BmobException e) {
                    if (e == null) {
                        mDataSet.addAll(list);
                        mAdapter.updateDataSet(mDataSet);
                    } else {
                        Toast.makeText(SelectWhisperActivity.this, "加载数据失败", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        return true;
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_select_activity);
        ButterKnife.bind(this);
        setupToolbar();
        mAdapter = new WhisperSelectAdapter(mDataSet = new ArrayList<>());
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnItemTouchListener(new OnItemClickListener(this, mRecycleView, new OnItemClickListener.DelegateHandler() {
            /* 改变选择方式，只通过长按者一种方式，长按选中，长按取消选中 */
//            @Override
//            public void handleClick(View view, int position) {
////                if (mActionMode != null) {
////                    handleItemTouched(view, position);
////                }
//            }

            @Override
            public void handleLongPress(View view, int position) {
                handleItemTouched(view, position);
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoadingView.setVisibility(View.VISIBLE);
        BmobQuery<MyTopic> query = new BmobQuery<>();
        query.addWhereEqualTo("user", BmobUser.getCurrentUser(User.class));
        query.findObjects(new FindListener<MyTopic>() {
            @Override
            public void done(List<MyTopic> list, BmobException e) {
                if (e == null) {
                    Message msg = null;
                    for (MyTopic topic : list) {
                        msg = new Message();
                        msg.obj = topic;
                        mHandler.sendMessage(msg);
                    }
                } else {
                    Toast.makeText(SelectWhisperActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "done: 获取数据失败", e);
                }
                mLoadingView.setVisibility(View.GONE);
            }
        });
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    private void handleItemTouched(View view, final int position) {

        if (mActionMode != null) {
            mActionMode.finish();
            return;
        }

        mAdapter.clearSelectedItems();
        mAdapter.toggleSelectStatus(position);
        mActionMode = startSupportActionMode(new ActionModeCallback(
                this,
                tag -> {
                    if (tag == ActionModeCallback.Tag.DONE) {
                        Whisper whisper = mDataSet.get(position);
                        whisper.setAdded(true);
                        whisper.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    /* 更新成功, 不做任何处理 */
                                } else {
                                    /* 更新失败，打印错误日志 */
                                    Toast.makeText(SelectWhisperActivity.this, "更新Whisper失败", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, e.getMessage(), e);
                                }
                            }
                        });

                        AllWhisper allWhisper = new AllWhisper(whisper);
                        allWhisper.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(SelectWhisperActivity.this, "掷入成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SelectWhisperActivity.this, "掷入失败", Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "done: 掷入失败", e);
                                }
                            }
                        });
                        finish();
                    }
                },
                R.menu.whisper_select_done));
    }

    @Override
    public void destroyActionMode() {
        mActionMode = mActionMode == null ? mActionMode : null;
        mAdapter.clearSelectedItems();
    }
}
