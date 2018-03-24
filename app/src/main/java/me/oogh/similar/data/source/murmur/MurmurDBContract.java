package me.oogh.similar.data.source.murmur;

import android.provider.BaseColumns;

/**
 * Created by oogh on 18-3-19.
 */

public final class MurmurDBContract {
    private MurmurDBContract() {
    }

    public static class MurmurEntry implements BaseColumns {
        public static final String TABLE_NAME = "murmur";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TAG = "tag";
    }
}
