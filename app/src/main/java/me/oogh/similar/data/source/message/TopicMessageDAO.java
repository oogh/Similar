package me.oogh.similar.data.source.message;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import me.oogh.similar.data.entry.Message;

public class TopicMessageDAO {

    private static final String TAG = TopicMessageDAO.class.getSimpleName();

    private TopicMessageHelper mHelper;

    public TopicMessageDAO(TopicMessageHelper helper){
        mHelper = helper;
    }

    public long insert(Message message) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", message.getNickname());
        values.put("content", message.getContent());
        values.put("objectId", message.getDatetime());
        return db.insert("topic_chat_message", null, values);
    }

    public List<Message> listTopicMessage(String objectId) {
        List<Message> messages = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        String selection = "datetime = ?";
        String[] selectionArgs = {objectId};

        Cursor cursor = db.query("topic_chat_message", null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String datetime = cursor.getString(cursor.getColumnIndex("datetime"));
            Message msg = new Message();
            msg.setDatetime(datetime);
            msg.setContent(content);
            messages.add(msg);
        }

        return messages;
    }
}
