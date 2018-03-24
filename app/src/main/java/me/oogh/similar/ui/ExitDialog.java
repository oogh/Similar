package me.oogh.similar.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * Created by oogh on 18-3-20.
 */

public class ExitDialog extends DialogFragment {
    private String mTitle;

    public ExitDialog() {

    }

    public static ExitDialog getInstance(String title) {
        ExitDialog dialog = new ExitDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        mTitle = args.getString("title");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("您真的要狠心离开么～")
                .setPositiveButton("狠心离开", (dialog, which) -> {
                    getActivity().finish();
                })
                .setNegativeButton("不走了", (dialog, which) -> {
                    dismiss();
                });
        return builder.create();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setTitle(mTitle);
    }
}
