package me.oogh.similar.common;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import me.oogh.similar.R;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-24
 * @description
 */

public class ActionModeCallback implements ActionMode.Callback {
    private Handler mHandler;
    private Actionable mActionable;

    public ActionModeCallback(Actionable actionable, Handler handler) {
        mActionable = actionable;
        mHandler = handler;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.murmur_action_mode_items, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_murmur_today:
                mHandler.handle(Tag.DELETE);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionable.destroyActionMode();
    }

    public enum Tag {
        DELETE
    }

    public interface Handler {
        void handle(Tag tag);
    }
}
