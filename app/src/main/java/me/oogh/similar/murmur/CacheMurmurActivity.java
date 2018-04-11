package me.oogh.similar.murmur;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.oogh.similar.R;
import me.oogh.similar.adapter.MurmurRecycleAdapter;
import me.oogh.similar.data.entry.Event;
import me.oogh.similar.data.entry.Murmur;

public class CacheMurmurActivity extends AppCompatActivity {

    @BindView(R.id.tb_murmur_cache_activity)
    Toolbar mToolbar;
    @BindView(R.id.rv_murmur_cache_list)
    RecyclerView mCacheListView;

    private List<Murmur> mDataSet;
    private MurmurRecycleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.murmur_cache_activity);
        ButterKnife.bind(this);
        setupToolbar();
        mAdapter = new MurmurRecycleAdapter(this, mDataSet = new ArrayList<>());
        mCacheListView.setLayoutManager(new LinearLayoutManager(this));
        mCacheListView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void setupToolbar() {
        mToolbar.setTitle("草稿箱");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_nav_back);
        mToolbar.setNavigationOnClickListener(view -> finish());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.murmur_action_sync, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_murmur_sync:
                Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Subscribe(sticky = true)
    public void onCachedMurmurListRecieved(Event.MurmurListEvent event) {
        if (event.tag == Event.Tag.MURMUR_CACHE_LIST) {
            mDataSet = event.murmurs;
            mAdapter.updateDataSet(mDataSet);
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
