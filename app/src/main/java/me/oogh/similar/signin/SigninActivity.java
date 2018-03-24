package me.oogh.similar.signin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.oogh.similar.R;
import me.oogh.similar.utils.ActivityUtils;

public class SigninActivity extends AppCompatActivity {

    private static final String TAG = SigninActivity.class.getSimpleName();

    private SigninPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        SigninFragment signinFragment = (SigninFragment) getSupportFragmentManager().findFragmentById(R.id.fl_sign_container);
        if (signinFragment == null) {
            signinFragment = SigninFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), signinFragment, R.id.fl_sign_container);
        }

        mPresenter = new SigninPresenter(this, signinFragment);
    }

}
