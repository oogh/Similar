package me.oogh.similar.murmur;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
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
 * @author oogh <oogh216@163.com>
 * @date 2018-03-04
 * @description
 */

public class MurmurFragment extends Fragment implements MurmurContract.View {
    private static final String TAG = MurmurFragment.class.getSimpleName();

    private static final String MURMUR_TYPE_TODAY = "daily";
    private static final String MURMUR_TYPE_FUTURE = "future";
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

    private DialogMurmurSelector mDialog;


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
            mDialog = DialogMurmurSelector.newInstance("写给");
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
                onAddClick(MURMUR_TYPE_TODAY);
                break;
            case R.id.rb_dialog_future:
                onAddClick(MURMUR_TYPE_FUTURE);
                break;
            default:
                break;
        }
    }

    private void onAddClick(String type) {
        Intent intent = new Intent(getContext(), AddMurmurActivity.class);
        intent.putExtra("type", type);

        if (MURMUR_TYPE_FUTURE.equals(type)) {
            DialogDatePicker dialog = DialogDatePicker.newInstance();
            dialog.setOnDateSetListener((date, valid) -> {
                if (valid) {
                    intent.putExtra("date", date.getTime());
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "请选择未来的时间节点", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            });
            mDialog.dismiss();
            dialog.show(getFragmentManager(), "date_picker_dialog");
        } else {
            intent.putExtra("date", new Date().getTime());
            startActivity(intent);
            mDialog.dismiss();
        }

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
        if (event.tag == Event.Tag.MURMUR_ADD_COMPLETED) {
            mPresenter.saveMurmur(event.murmur);
            mPresenter.listMurmur(BmobUser.getCurrentUser(User.class).getObjectId());
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Subscribe
    public void onRemoveMurmur(Event.MurmurEvent event) {
        if (event.tag == Event.Tag.MURMUR_REMOVE) {
            mPresenter.removeMurmur(event.murmur);
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
        EventBus.getDefault().postSticky(new Event.MurmurListEvent(murmurs, Event.Tag.MURMUR_SHOW_LIST));
    }

}
