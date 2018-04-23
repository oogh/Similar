package me.oogh.similar.whisper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.Whisper;

public class EditWhisperActivity extends AppCompatActivity {

    @BindView(R.id.tb_whisper_edit_activity)
    Toolbar mToolbar;
    @BindView(R.id.et_edit_whisper_today)
    EditText mEditView;
    private Whisper mWhisper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whisper_edit_activity);
        ButterKnife.bind(this);
        mWhisper = (Whisper) getIntent().getSerializableExtra("whisper");
        setupToolbar();
        mEditView.setText(mWhisper.getContent());
    }

    private void setupToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.whisper_edit_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_whisper_done:
                String content = mEditView.getText().toString();
                mWhisper.setContent(content);
                Intent intent = new Intent(EditWhisperActivity.this, WhisperTopicDetailActivity.class);
                intent.putExtra("whisper", mWhisper);
                setResult(Activity.RESULT_OK, intent);
                finish();
                break;
        }
        return true;
    }

    /**
     * 点击空白位置 隐藏软键盘
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
