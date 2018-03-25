package me.oogh.similar.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author oogh <oogh216@163.com>
 * @date 2018-03-24
 * @description RecyclerView Item 点击事件
 */

public class OnItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private GestureDetector mGestureDetector;

    public OnItemClickListener(Context context, @NonNull final RecyclerView rv, @NonNull final Handler handler) {

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View itemView = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(itemView);
                if (itemView != null) {
                    handler.handleClick(itemView, position);
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View itemView = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(itemView);
                if (itemView != null) {
                    handler.handleLongPress(itemView, position);
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    public interface Handler {
        void handleClick(View view, int position);

        void handleLongPress(View view, int position);
    }

}
