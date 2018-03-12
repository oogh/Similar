package me.oogh.similar.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.oogh.similar.R;
import me.oogh.similar.adapter.MessageListAdapter;
import me.oogh.similar.data.Message;

/**
 * Created by oogh on 18-3-2.
 */

public class MessageFragment extends Fragment implements MessageContract.View {

    private static final String TAG = MessageFragment.class.getSimpleName();

    @BindView(R.id.rv_message_list)
    RecyclerView mMessageView;
    @BindView(R.id.tv_message_no_content)
    TextView mNoMessageView;
    private Unbinder mUnbinder;
    private MessageContract.Presenter mPresenter;
    private MessageListAdapter mAdapter;

    public MessageFragment() {

    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MessageListAdapter(new ArrayList<>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.message_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mMessageView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMessageView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Log.i(TAG, "onSwiped: Swiped");
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeCallback);
        touchHelper.attachToRecyclerView(mMessageView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(@NonNull MessageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showMessage(List<Message> messages) {
        mAdapter.updateMessages(messages);
        mMessageView.setVisibility(View.VISIBLE);
        mNoMessageView.setVisibility(View.GONE);
    }

    @Override
    public void showNoMessage() {
        mMessageView.setVisibility(View.GONE);
        mNoMessageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
