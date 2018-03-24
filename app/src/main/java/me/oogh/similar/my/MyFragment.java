package me.oogh.similar.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;
import me.oogh.similar.R;
import me.oogh.similar.signin.SigninActivity;

/**
 * Created by oogh on 18-3-4.
 */

public class MyFragment extends Fragment implements MyContract.View {
    @BindView(R.id.tv_current_user)
    TextView mCurrentUserView;
    private Unbinder unbinder;
    private MyContract.Presenter mPresenter;

    @Override
    public void setPresenter(@NonNull MyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public MyFragment() {
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.getCurrentUser(user -> mCurrentUserView.setText(user.getUsername()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_logout)
    public void onExitViewClicked() {
        BmobUser.logOut();
        startActivity(new Intent(getContext(), SigninActivity.class));
        getActivity().finish();
    }
}
