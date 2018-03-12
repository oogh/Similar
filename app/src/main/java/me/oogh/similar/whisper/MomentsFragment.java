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
 * Created by oogh on 18-3-6.
 */

public class MomentsFragment extends Fragment implements WhisperContract.View {

    private Unbinder mUnbinder;

    @Override
    public void setPresenter(@NonNull WhisperContract.Presenter presenter) {

    }

    public MomentsFragment() {
    }

    public static MomentsFragment newInstance() {
        MomentsFragment fragment = new MomentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.whisper_moments_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


}
