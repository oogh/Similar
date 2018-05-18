package me.oogh.similar.data.source.message;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MessageHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "similar.db";
    private static final int DB_VERSION = 1;


    private static MessageHelper INSTANCE = null;


    public static synchronized MessageHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MessageHelper(context);
        }
        return INSTANCE;
    }

    private MessageHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    private static final String CREATE_TABLE_CHAT_MESSAGE =
            "CREATE TABLE chat_message (" +
                    "objectId Text,"+
                     "content TEXT , " +
                     "username TEXT , " +
                    "datetime TEXT ); ";

    private static final String DROP_TABLE_CHAT_MESSAGE =
            "DROP TABLE IF EXISTS chat_message";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CHAT_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CHAT_MESSAGE);
        onCreate(db);
    }
}
