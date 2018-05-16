package me.oogh.similar.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.Tencent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.User;
import me.oogh.similar.message.MessageActivity;

/**
 * Created by oogh on 18-3-16.
 */

public class SigninFragment extends Fragment implements SigninContract.View {
    private static final String TAG = SigninFragment.class.getSimpleName();
    @BindView(R.id.rb_user1)
    RadioButton mOneLoginView;
    @BindView(R.id.rb_user2)
    RadioButton mTwoLoginView;
    private Unbinder unbinder;
    private SigninContract.Presenter mPresenter;

    public SigninFragment() {
    }

    public static SigninFragment newInstance() {
        SigninFragment signinFragment = new SigninFragment();
        return signinFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.signin_fragment_2, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void setPresenter(@NonNull SigninContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, ((SigninPresenter) mPresenter).getLoginListener());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_bmob_login, R.id.btn_qq_login})
    public void onLoginViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bmob_login:
                User user = null;
                if (mOneLoginView.isChecked()) {
                    user = new User("user111", "123");
                } else if (mTwoLoginView.isChecked()) {
                    user = new User("user222", "123");
                }
                mPresenter.login(SigninContract.TYPE.BMOB, user);
                break;
            case R.id.btn_qq_login:
                mPresenter.login(SigninContract.TYPE.QQ);
                break;
        }
    }

    @Override
    public void showSucceed() {
        Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getContext(), MessageActivity.class));
        getActivity().finish();
    }

    @Override
    public void showFailed(String msg) {
        Log.i(TAG, "登录失败: " + msg);
        Toast.makeText(getContext(), "登录失败: " + msg, Toast.LENGTH_SHORT).show();
    }
}
