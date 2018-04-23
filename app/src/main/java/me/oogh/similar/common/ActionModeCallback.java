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
    private Integer mMenuId;

    public ActionModeCallback(Actionable actionable, Handler handler) {
        this(actionable, handler, R.menu.murmur_action_mode_items);
    }

    public ActionModeCallback(Actionable actionable, Handler handler, int menuId) {
        mActionable = actionable;
        mHandler = handler;
        mMenuId = menuId;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(mMenuId, menu);
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
            case R.id.action_select_whisper_done:
                mHandler.handle(Tag.DONE);
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
        DELETE, DONE
    }

    public interface Handler {
        void handle(Tag tag);
    }
}
