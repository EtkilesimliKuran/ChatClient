package smyy.qsearch.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import smyy.qsearch.model.Message;

/**
 * Created by Sümeyye on 10.1.2017.
 */

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "qsearch.message";
    private static final String TABLE_MESSAGES = "messages";
    private static final String TABLE_BOOKMARK = "bookmark";
    private static String MESSAGE_CONTENT = "message_content";
    private static String MESSAGE_ID = "message_id";
    private static String MESSAGE_ISMINE = "message_isMine";
    private static String SOURCE_TYPE = "source_type";
    private SQLiteDatabase myDataBase;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_MESSAGES + "("
                + MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MESSAGE_CONTENT + " TEXT,"
                + MESSAGE_ISMINE + " BOOLEAN,"
                + SOURCE_TYPE + " INTEGER" +
                ")";
        String CREATE_TABLE_BOOKMARK = "CREATE TABLE " + TABLE_BOOKMARK + "("
                + MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MESSAGE_CONTENT + " TEXT,"
                + MESSAGE_ISMINE + " BOOLEAN" + ")";
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_BOOKMARK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    public void insertMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("message_id", message.getMessageID());
        values.put("message_content", message.getMessageContent());
        values.put("message_isMine", message.getisMine());
        values.put("source_type", message.getSourceType());

        db.insert(TABLE_MESSAGES, null, values);
        db.close();
    }

    public void insertBookmark(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("message_id", message.getMessageID());
        values.put("message_content", message.getMessageContent());
        values.put("message_isMine", message.getisMine());

        db.insert(TABLE_BOOKMARK, null, values);
        db.close();
    }

    public List<Message> getAllBookMarks() {
        List<Message> messages = new ArrayList<Message>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_BOOKMARK, new String[]{"message_id", "message_content", "message_isMine"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
            Message message = new Message();
            message.setMessageID(cursor.getInt(0));
            message.setMessageContent(cursor.getString(1));
            message.setisMine(cursor.getInt(2) > 0);
            messages.add(message);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return messages;
    }

    public List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<Message>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_MESSAGES, new String[]{"message_id", "message_content", "message_isMine","source_type"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Message message = new Message();
                message.setMessageID(cursor.getInt(0));
                message.setMessageContent(cursor.getString(1));
                message.setisMine(cursor.getInt(2) > 0);
                message.setSourceType(cursor.getInt(3));
                messages.add(message);
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return messages;
    }

    public void removeBookMark(int message_id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_BOOKMARK + " WHERE " + MESSAGE_ID + "= '" + message_id + "'");
        database.close();
    }

    public void removeMessage(int message_id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_MESSAGES + " WHERE " + MESSAGE_ID + "= '" + message_id + "'");
        database.close();
    }

    public void resetTableBookMark() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BOOKMARK, null, null);
        db.close();
    }

    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGES, null, null);
        db.close();
    }
}
