package me.oogh.similar.murmur;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.data.entry.Event;
import me.oogh.similar.data.entry.Murmur;
import me.oogh.similar.utils.DateUtils;

public class EditMurmurActivity extends AppCompatActivity {
    private static final String TAG = EditMurmurActivity.class.getSimpleName();

    @BindView(R.id.tb_edit_murmur_today_activity)
    Toolbar mToolbar;
    @BindView(R.id.et_edit_murmur_today)
    EditText mTextAreaView;

    private MenuItem mEditMenu, mDoneMenu;
    private Murmur mMurmur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.murmur_edit_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setupToolbar();
        mTextAreaView.setText(mMurmur.getContent());
    }

    private void setupToolbar() {
        mToolbar.setTitle(DateUtils.format(mMurmur.getDate()));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.murmur_edit_done, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mEditMenu = menu.findItem(R.id.action_edit_murmur_edit);
        mDoneMenu = menu.findItem(R.id.action_edit_murmur_done);
        mDoneMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit_murmur_edit:
                mEditMenu.setVisible(false);
                mDoneMenu.setVisible(true);
                mTextAreaView.setEnabled(true);
                break;
            case R.id.action_edit_murmur_done:
                EventBus.getDefault().postSticky(new Event.MurmurEvent(new Murmur(
                        mMurmur.getUser(),
                        mMurmur.getDate(),
                        mTextAreaView.getText().toString(),
                        mMurmur.getTag()), Event.Tag.MURMUR_EDIT_COMPLETED));
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Subscribe(sticky = true)
    public void onReceiveMurmur(Event.MurmurEvent event) {
        if (event.tag == Event.Tag.MURMUR_PARAM) {
            mMurmur = event.murmur;
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
