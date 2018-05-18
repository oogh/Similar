package me.oogh.similar.signin;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import me.oogh.similar.data.entry.User;
import me.oogh.similar.message.MessageActivity;

import static cn.bmob.v3.BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ;

/**
 * Created by oogh on 18-3-16.
 */

public class SigninPresenter implements SigninContract.Presenter {
    private static final String TAG = SigninPresenter.class.getSimpleName();

    public Tencent mTencent;
    private Context mContext;
    private SigninContract.View mView;

    private IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            initOpenidAndToken(values);
            Log.i(TAG, "doComplete: "+values);
        }
    };

    public SigninPresenter(Context context, SigninContract.View view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
    }

    public IUiListener getLoginListener() {
        return loginListener;
    }

    @Override
    public void start() {
        if (mTencent == null) {
            mTencent = Tencent.createInstance("1106699661", mContext);
        }
    }

    @Override
    public void login(SigninContract.TYPE type, User... users) {
        switch (type) {
            case QQ:
                mTencent.login((Fragment) mView, "all", loginListener);
                break;
            case BMOB:
                loginWithBmob(users[0]);
                break;
            case WECHAT:
                break;
            case WEIBO:
                break;
            default:
                break;
        }
    }

    /**
     * Bmob 帐号密码登录
     *
     * @param user
     */
    private void loginWithBmob(User user) {
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    mView.showSucceed();
                } else {
                    mView.showFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);

                BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(SNS_TYPE_QQ, token, expires, openId);
                BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {

                    @Override
                    public void done(JSONObject userAuth, BmobException e) {
                        if (e == null) {
                            mView.showSucceed();
                            mContext.startActivity(new Intent(mContext, MessageActivity.class));
                        } else {
                            mView.showFailed(e.getLocalizedMessage());
                        }
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    /**
     * QQ 登录回调接口
     */
    public class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                return;
            }
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onCancel() {
        }
    }
}
