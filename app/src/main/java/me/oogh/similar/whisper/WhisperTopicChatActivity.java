package me.oogh.similar.whisper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperTopicChatAdapter;
import me.oogh.similar.data.entry.Message;
import me.oogh.similar.data.source.message.TopicMessageDAO;
import me.oogh.similar.data.source.message.TopicMessageHelper;

public class WhisperTopicChatActivity extends AppCompatActivity {

    private static final String TAG = WhisperTopicChatActivity.class.getSimpleName();

    @BindView(R.id.tb_whisper_topic_chat_activity)
    Toolbar mToolbar;
    @BindView(R.id.rv_topic_chat_message_list)
    RecyclerView mChatListView;
    @BindView(R.id.btn_topic_chat_send_message)
    Button mSendView;
    @BindView(R.id.et_topic_chat_message_content)
    EditText mContentView;

    private List<Message> mDataSet;
    private WhisperTopicChatAdapter mAdapter;

    private TopicMessageDAO mMessageManager;
    private String mObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_topic_chat_activity);
        ButterKnife.bind(this);
        setupToolbar();

        mObjectId = getIntent().getStringExtra("objectId");
        mMessageManager = new TopicMessageDAO(TopicMessageHelper.getInstance(this));
        mAdapter = new WhisperTopicChatAdapter(mDataSet = new ArrayList<>());
        mChatListView.setLayoutManager(new LinearLayoutManager(this));
        mChatListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSet = mMessageManager.listTopicMessage(mObjectId);
        mAdapter.updateDataSet(mDataSet);

    }

    @OnClick(R.id.btn_topic_chat_send_message)
    public void onPostViewClicked(View view){
        String content = mContentView.getText().toString();
        if (TextUtils.isEmpty(content.trim())) {
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        Message msg = new Message();
        msg.setDatetime(mObjectId);
        msg.setContent(content);
        mMessageManager.insert(msg);
        mDataSet.add(msg);
        mAdapter.updateDataSet(mDataSet);
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(v -> finish());
    }
}
