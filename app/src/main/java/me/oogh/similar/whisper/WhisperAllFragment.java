package me.oogh.similar.whisper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import me.oogh.similar.R;
import me.oogh.similar.adapter.WhisperAllAdapter;
import me.oogh.similar.common.OnItemClickListener;
import me.oogh.similar.data.entry.AllWhisper;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-06
 * @description
 */

public class WhisperAllFragment extends Fragment implements WhisperContract.View {
    private static final String TAG = WhisperAllFragment.class.getSimpleName();

    private Unbinder mUnbinder;
    @BindView(R.id.rv_whisper_all_list)
    RecyclerView mWhisperListView;

    private List<AllWhisper> mDataSet;
    private WhisperAllAdapter mAdapter;

    @Override
    public void setPresenter(@NonNull WhisperContract.Presenter presenter) {

    }

    public WhisperAllFragment() {
    }

    public static WhisperAllFragment newInstance() {
        WhisperAllFragment fragment = new WhisperAllFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new WhisperAllAdapter(mDataSet = new ArrayList<>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.whisper_moments_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWhisperListView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWhisperListView.setAdapter(mAdapter);
        mWhisperListView.addOnItemTouchListener(new OnItemClickListener(getContext(), mWhisperListView, new OnItemClickListener.DelegateHandler() {
            @Override
            public void handleClick(View view, int position) {
                super.handleClick(view, position);
                Intent intent = new Intent(getActivity(), WhisperDetailActivity.class);
                intent.putExtra("whisper", mDataSet.get(position).getWhisper());
                startActivity(intent);
            }
        }));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BmobQuery<AllWhisper> query = new BmobQuery<>();
        query.include("whisper.topic.user");

        query.findObjects(new FindListener<AllWhisper>() {
            @Override
            public void done(List<AllWhisper> list, BmobException e) {
                mDataSet = list;
                mAdapter.updateDataSet(mDataSet);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


}
