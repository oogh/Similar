package me.oogh.similar.whisper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.oogh.similar.R;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-06
 * @description
 */

public class HotFragment extends Fragment implements WhisperContract.View {
    public Unbinder mUnbinder;

    @Override
    public void setPresenter(@NonNull WhisperContract.Presenter presenter) {

    }

    public HotFragment() {
    }

    public static HotFragment newInstance() {
        HotFragment fragment = new HotFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.whisper_hot_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
