package me.oogh.similar.data.source.message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TopicMessageHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "similar.db";
    private static final int DB_VERSION = 1;

    public TopicMessageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private static TopicMessageHelper INSTANCE = null;


    public static synchronized TopicMessageHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TopicMessageHelper(context);
        }
        return INSTANCE;
    }

    private static final String CREATE_TABLE_TOPIC_CHAT_MESSAGE =
            "CREATE TABLE topic_chat_message (" +
                    "objectId Text,"+
                    "content TEXT , " +
                    "username TEXT , " +
                    "datetime TEXT ); ";

    private static final String DROP_TABLE_CHAT_MESSAGE =
            "DROP TABLE IF EXISTS topic_chat_message";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TOPIC_CHAT_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_TABLE_CHAT_MESSAGE);
        onCreate(db);
    }
}
