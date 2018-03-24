package me.oogh.similar.murmur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.oogh.similar.R;
import me.oogh.similar.adapter.MurmurDailyListViewAdapter;
import me.oogh.similar.data.entry.Event;

/**
 * Created by oogh on 18-3-4.
 */

public class DailyFragment extends Fragment {

    private static final String TAG = DailyFragment.class.getSimpleName();

    @BindView(R.id.rv_murmur_daily_list)
    RecyclerView mMurmurDailyLIstView;
    @BindView(R.id.srl_murmur_daily_fragment)
    SwipeRefreshLayout mSwipeRefreshView;
    private Unbinder mUnbinder;

    private MurmurDailyListViewAdapter mAdapter;

    public DailyFragment() {
    }

    public static DailyFragment newInstance() {
        DailyFragment fragment = new DailyFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MurmurDailyListViewAdapter(new ArrayList<>());
        mAdapter.setOnRecycleItemClickListener((view, murmur, position) -> {
            startActivity(new Intent(getContext(), EditMurmurActivity.class));
            EventBus.getDefault().postSticky(new Event.MurmurEvent(
                    murmur,
                    Event.Tag.MURMUR_PARAM));
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.murmur_daily_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMurmurDailyLIstView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMurmurDailyLIstView.setAdapter(mAdapter);

//        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//            }
//        };
//        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeCallback);
//        touchHelper.attachToRecyclerView(mMurmurDailyLIstView);

        // TODO: 下拉刷新
        mSwipeRefreshView.setOnRefreshListener(() -> {

            mSwipeRefreshView.setRefreshing(false);
        });
    }


    /**
     * 接收MurmurList
     *
     * @param event
     */
    @Subscribe(sticky = true)
    public void onReceiveMurmurList(Event.MurmurListEvent event) {
        mAdapter.updateDataSet(event.murmurs);
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
