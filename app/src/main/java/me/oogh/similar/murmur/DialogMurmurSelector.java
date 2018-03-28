package me.oogh.similar.murmur;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.oogh.similar.R;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-18
 * @description
 */
public class DialogMurmurSelector extends DialogFragment {
    @BindView(R.id.rb_dialog_today)
    RadioButton mTodayView;
    @BindView(R.id.rb_dialog_future)
    RadioButton mFutureView;

    private Unbinder mUnbinder;
    private OnActionClickedListener mListener;
    private String mDialogTitleText;

    public void addOnActionClickedListener(OnActionClickedListener listener) {
        mListener = listener;
    }

    public DialogMurmurSelector() {
    }

    public static DialogMurmurSelector newInstance(String title) {
        DialogMurmurSelector dialog = new DialogMurmurSelector();
        Bundle args = new Bundle();
        args.putString("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mDialogTitleText = args.getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.murmur_dialog_fragment, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle(mDialogTitleText);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick({R.id.btn_dialog_cancel, R.id.btn_dialog_ok})
    public void onButtonClicked(View view) {
        mListener.onActionClicked((Button) view, getCurrentRadio());
    }

    private RadioButton getCurrentRadio() {
        return mTodayView.isChecked() ? mTodayView : mFutureView;
    }

    interface OnActionClickedListener {
        void onActionClicked(Button action, RadioButton radio);
    }
}
