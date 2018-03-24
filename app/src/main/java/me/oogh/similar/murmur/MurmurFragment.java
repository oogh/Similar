package me.oogh.similar.murmur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import me.oogh.similar.R;
import me.oogh.similar.adapter.MurmurViewPagerAdapter;
import me.oogh.similar.data.entry.Event;
import me.oogh.similar.data.entry.Murmur;
import me.oogh.similar.data.entry.User;

/**
 * Created by oogh on 18-3-4.
 */

public class MurmurFragment extends Fragment implements MurmurContract.View {
    private static final String TAG = MurmurFragment.class.getSimpleName();

    private static final String MURMUR_TAG_TODAY = "today";
    private static final String MURMUR_TAG_FUTURE = "future";
    @BindView(R.id.vp_murmur)
    ViewPager mViewPager;
    private Unbinder mUnbinder;

    private MurmurContract.Presenter mPresenter;

    @Override
    public void setPresenter(@NonNull MurmurContract.Presenter presenter) {
        mPresenter = presenter;
    }

    private MurmurViewPagerAdapter mAdapter;

    private FloatingActionButton mFloatingActionView;

    private MurmurDialog mDialog;


    public MurmurFragment() {
    }

    public static MurmurFragment newInstance() {
        MurmurFragment fragment = new MurmurFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MurmurViewPagerAdapter(getContext(), getChildFragmentManager());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.murmur_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        mViewPager.setAdapter(mAdapter);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((TabLayout) getActivity().findViewById(R.id.tl_murmur)).setupWithViewPager(mViewPager);
        mFloatingActionView = getActivity().findViewById(R.id.fab_add_murmur);
        mFloatingActionView.setOnClickListener(view -> {
            mDialog = MurmurDialog.newInstance("写给");
            mDialog.addOnActionClickedListener((action, radio) -> {
                switch (action.getId()) {
                    case R.id.btn_dialog_ok:
                        doOkClicked(radio);
                        break;
                    case R.id.btn_dialog_cancel:
                        mDialog.dismiss();
                        break;
                    default:
                        break;
                }
            });
            mDialog.show(getFragmentManager(), "murmur_dialog_fragment");
        });
    }


    /**
     * 确定按钮点击时
     *
     * @param radio
     */
    private void doOkClicked(RadioButton radio) {
        switch (radio.getId()) {
            case R.id.rb_dialog_today:
                onAddClick("today");
                break;
            case R.id.rb_dialog_future:
                onAddClick("future");
                break;
            default:
                break;
        }
    }

    private void onAddClick(String tag) {
        switch (tag) {
            case MURMUR_TAG_TODAY:
                startActivity(new Intent(getContext(), AddMurmurActivity.class));
                break;
            case MURMUR_TAG_FUTURE:
                Toast.makeText(getContext(), "future", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        mDialog.dismiss();
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        mPresenter.listMurmur(BmobUser.getCurrentUser(User.class).getObjectId());
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

    /**
     * 保存Murmur
     *
     * @param event
     */
    @Subscribe(sticky = true)
    public void onSaveMurmur(Event.MurmurEvent event) {
        Log.i(TAG, "onSaveMurmur: 执行了...");
        if (event.tag == Event.Tag.MURMUR_ADD_COMPLETED) {
            mPresenter.saveMurmur(event.murmur);
            mPresenter.listMurmur(BmobUser.getCurrentUser(User.class).getObjectId());
        }
    }

    /**
     * 更新Murmur
     *
     * @param event
     */
    @Subscribe(sticky = true)
    public void onUpdateMurmur(Event.MurmurEvent event) {
        if (event.tag == Event.Tag.MURMUR_EDIT_COMPLETED) {
            mPresenter.updateMurmur(event.murmur);
            mPresenter.listMurmur(BmobUser.getCurrentUser(User.class).getObjectId());
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Override
    public void showMurmurList(List<Murmur> murmurs) {
        EventBus.getDefault().postSticky(new Event.MurmurListEvent(murmurs));
    }

}
