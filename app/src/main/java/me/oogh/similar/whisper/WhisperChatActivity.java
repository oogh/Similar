package me.oogh.similar.whisper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.listener.MessageListHandler;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperChatAdapter;
import me.oogh.similar.data.entry.Message;
import me.oogh.similar.data.entry.User;
import me.oogh.similar.data.source.message.MessageDAO;
import me.oogh.similar.data.source.message.MessageHelper;

public class WhisperChatActivity extends AppCompatActivity implements MessageListHandler{

    private static final String TAG = WhisperChatActivity.class.getSimpleName();

    @BindView(R.id.tb_whisper_chat_activity)
    Toolbar mToolbar;
    @BindView(R.id.rv_chat_message_list)
    RecyclerView mMessageListView;
    @BindView(R.id.btn_chat_send_message)
    Button mSendView;
    @BindView(R.id.et_chat_message_content)
    EditText mContentView;

    private List<Message> mDataSet;
    private WhisperChatAdapter mAdapter;

    private MessageDAO mMessageManager;

    private BmobIMConversation mConversationManager;

    private String mObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_chat_activity);
        ButterKnife.bind(this);

        setupToolbar();

        mAdapter = new WhisperChatAdapter(mDataSet = new ArrayList<>());
        mMessageManager = new MessageDAO(MessageHelper.getInstance(this));

         mObjectId = getIntent().getStringExtra("objectId");

        mMessageListView.setLayoutManager(new LinearLayoutManager(this));
        mMessageListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BmobIM.getInstance().addMessageListHandler(this);
        List<Message> messages = mMessageManager.listMessage(mObjectId);
        mAdapter.updateDataSet(mDataSet = messages);

    }

    private void preSend(){
        BmobIMUserInfo info = new BmobIMUserInfo();
        info.setUserId(BmobUser.getCurrentUser(User.class).getObjectId());
        BmobIMConversation conversation = BmobIM.getInstance().startPrivateConversation(info, null);

        conversation.sendMessage(null, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                if (e == null) {
                    Message message = new Message();
                    message.setContent(null);
                    mDataSet.add(message);
                    mAdapter.updateDataSet(mDataSet);
                }else{
                    Toast.makeText(WhisperChatActivity.this, "消息发送失败", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "done: 消息发送失败", e);
                }
            }
        });
    }

    /**
     * 发送消息
     * @param view
     */
    @OnClick(R.id.btn_chat_send_message)
    public void onSendMessageClicked(View view) {
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


    @Override
    public void onMessageReceive(List<MessageEvent> list) {
        if (list != null && list.size() > 0) {
            for (MessageEvent event : list) {
                String content = event.getMessage().getContent();
                Message msg = new Message();
                msg.setContent(content);
                mDataSet.add(msg);
                mAdapter.updateDataSet(mDataSet);
            }
        }
    }

    @Override
    protected void onPause() {
        BmobIM.getInstance().removeMessageListHandler(this);
        super.onPause();
    }
}
