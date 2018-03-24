package me.oogh.similar.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.oogh.similar.R;
import me.oogh.similar.ui.ExitDialog;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
    }

    @Override
    public void onBackPressed() {
        ExitDialog dialog = ExitDialog.getInstance("退出");
        dialog.show(getSupportFragmentManager(), "exit_dialog");
//        super.onBackPressed();

    }
}
