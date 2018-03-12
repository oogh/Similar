package me.oogh.similar.base;

import android.support.annotation.NonNull;

/**
 * Created by oogh on 18-3-2.
 */

public interface BaseView<T> {
    void setPresenter(@NonNull  T presenter);
}
