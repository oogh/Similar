package me.oogh.similar.murmur;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.oogh.similar.R;

/**
 * Created by oogh on 18-3-4.
 */

public class FutureFragmentSub extends Fragment {
    public FutureFragmentSub() {

    }

    public static FutureFragmentSub newInstance() {
        FutureFragmentSub fragment = new FutureFragmentSub();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.murmur_future_fragment, container, false);
        return rootView;
    }
}
