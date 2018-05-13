package me.oogh.similar.whisper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperMyAdapter;
import me.oogh.similar.data.entry.MyTopic;
import me.oogh.similar.data.entry.User;
import me.oogh.similar.data.entry.Whisper;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-06
 * @description 我的心语
 */

public class WhisperMyFragment extends Fragment implements WhisperContract.View {
    private static final String TAG = WhisperMyFragment.class.getSimpleName();

    public Unbinder mUnbinder;
    @BindView(R.id.rv_whisper_my_list)
    RecyclerView mWhisperMyListView;

    private List<Whisper> mDataSet;
    private WhisperMyAdapter mAdapter;

    private Handler mHandler = new Handler(msg -> {
        if (msg.obj instanceof MyTopic) {
            BmobQuery<Whisper> query = new BmobQuery<>();
            query.addWhereEqualTo("topic", (MyTopic) msg.obj);
            query.addWhereEqualTo("added", true);
            query.include("topic");
            query.findObjects(new FindListener<Whisper>() {
                @Override
                public void done(List<Whisper> list, BmobException e) {
                    if (e == null) {
                        mDataSet.addAll(list);
                        mAdapter.updateDataSet(mDataSet);
                    } else {
                        Toast.makeText(getContext(), "加载数据失败", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        return true;
    });

    @Override
    public void setPresenter(@NonNull WhisperContract.Presenter presenter) {

    }

    public WhisperMyFragment() {
    }

    public static WhisperMyFragment newInstance() {
        WhisperMyFragment fragment = new WhisperMyFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new WhisperMyAdapter(mDataSet = new ArrayList<>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.whisper_my_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWhisperMyListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWhisperMyListView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                    Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "done: 获取数据失败", e);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
