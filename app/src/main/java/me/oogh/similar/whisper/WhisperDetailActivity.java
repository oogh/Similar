package me.oogh.similar.whisper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperCommentAdapter;
import me.oogh.similar.data.entry.Comment;
import me.oogh.similar.data.entry.Whisper;

public class WhisperDetailActivity extends AppCompatActivity {
    private static final String TAG = WhisperDetailActivity.class.getSimpleName();
    private static final int UPDATE_COMMENT_VIEW = 1;
    @BindView(R.id.tb_whisper_detail_activity)
    Toolbar mToolbar;
    @BindView(R.id.btn_whisper_detail_title_2)
    Button mTitleView;
    @BindView(R.id.tv_whisper_detail_content)
    TextView mContentView;
    @BindView(R.id.rv_whisper_container_2)
    RecyclerView mCommentListView;
    @BindView(R.id.et_comment)
    EditText mCommentView;
    @BindView(R.id.btn_send_comment)
    Button mPostView;
    @BindView(R.id.tv_comment_empty)
    TextView mEmptyView;

    private Whisper mWhisper;
    private List<Comment> mDataSet;
    private WhisperCommentAdapter mAdapter;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == UPDATE_COMMENT_VIEW) {
                List<Comment> list = (List<Comment>) msg.obj;
                if (list.size() > 0) {
                    mEmptyView.setVisibility(View.GONE);
                    mCommentListView.setVisibility(View.VISIBLE);
                    mDataSet = list;
                    mAdapter.updateDataSet(mDataSet);
                }
            }
            return true;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_detail_activity);
        ButterKnife.bind(this);
        mWhisper = (Whisper) getIntent().getSerializableExtra("whisper");
        String title = mWhisper.getTopic().getTitle();
        String content = mWhisper.getContent();
        mTitleView.setText(title);
        mContentView.setText(content);
        setupToolbar();
        mAdapter = new WhisperCommentAdapter(mDataSet = new ArrayList<>());
        initCommentData();

        mCommentListView.setLayoutManager(new LinearLayoutManager(this));
        mCommentListView.setAdapter(mAdapter);

    }

    private void initCommentData() {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("whisper", mWhisper.getObjectId());
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    Message msg = Message.obtain();
                    msg.what = UPDATE_COMMENT_VIEW;
                    msg.obj = list;
                    mHandler.sendMessage(msg);
                } else {
                    Log.e(TAG, "done: 获取数据失败", e);
                }
            }
        });
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whisper_content_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_whisper_content_chat) {
            Intent intent = new Intent(this, WhisperChatActivity.class);
            intent.putExtra("objectId", mWhisper.getObjectId());
            startActivity(intent);
        }
        return true;
    }

    @OnClick(R.id.btn_whisper_detail_title_2)
    public void onTitleViewClicked(View view){
        Intent intent = new Intent(this, WhisperTopicChatActivity.class);
        intent.putExtra("objectId", mWhisper.getTopic().getObjectId());
        startActivity(intent);

        
    }

    @OnClick(R.id.btn_send_comment)
    public void onViewClicked() {

        String comment = mCommentView.getText().toString();
        if (comment != null && comment.trim().length() > 0) {
            Comment cmt = new Comment(comment, mWhisper);
            mDataSet.add(cmt);
            cmt.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Log.i(TAG, "done: 评论成功");
                    }
                }
            });
            mAdapter.updateDataSet(mDataSet);
            if (mDataSet.size() > 0) {
                mEmptyView.setVisibility(View.GONE);
                mCommentListView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.VISIBLE);
                mCommentListView.setVisibility(View.GONE);
            }
            Toast.makeText(this, comment, Toast.LENGTH_SHORT).show();
            mCommentView.setText("");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
