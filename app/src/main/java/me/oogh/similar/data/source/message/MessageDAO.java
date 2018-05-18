package me.oogh.similar.data.source.message;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import me.oogh.similar.data.entry.Message;

public class MessageDAO {
    private static final String TAG = MessageDAO.class.getSimpleName();

    private MessageHelper mHelper;

    public MessageDAO(MessageHelper helper) {
        mHelper = helper;
    }

    public long insert(Message msg) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", msg.getNickname());
        values.put("content", msg.getContent());
        values.put("datetime", msg.getDatetime());
        return db.insert("chat_message", null, values);
    }

    public List<Message> listMessage(String objectId){
        List<Message> messageList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String selection = "datetime = ?";
        String[] selectionArgs = {objectId};
        Cursor cursor = db.query("chat_message", null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String datetime = cursor.getString(cursor.getColumnIndex("datetime"));

            Message msg = new Message();
            msg.setNickname(username);
            msg.setContent(content);
            msg.setDatetime(datetime);
            messageList.add(msg);
        }

        return messageList;
    }
}
