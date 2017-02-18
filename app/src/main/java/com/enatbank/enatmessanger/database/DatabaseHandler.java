package com.enatbank.enatmessanger.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.enatbank.enatmessanger.models.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by btinsae on 17/02/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "enatMessanger";

    // Message table name
    private static final String TABLE_MSGS = "messages";

    private static final String KEY_ID = "id";
    private static final String KEY_MSG = "message";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_DATE = "msgDate";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MESSAGE_TABLE = "CREATE TABLE" + TABLE_MSGS + "(" + KEY_ID + "int primary key auto_increment,"
                + KEY_MSG + "text," + KEY_USERNAME + "varchar(30)," + KEY_DATE + "varchar(10))";
        db.execSQL(CREATE_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS" + DATABASE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addMessage(Chat chat) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MSG, chat.getChatMessage());
        contentValues.put(KEY_DATE, chat.getChatDate());
        contentValues.put(KEY_USERNAME, chat.getUsername());
        sqLiteDatabase.insert(TABLE_MSGS, null, contentValues);
        sqLiteDatabase.close();
    }

    public List<Chat> getMessageByUsername(String username) {
        List<Chat> chats = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_MSGS, new String[]{KEY_ID, KEY_DATE, KEY_MSG, KEY_USERNAME}, KEY_USERNAME + "=?", new String[]{username}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        do {
            Chat chat = new Chat();
            chat.setChatDate(cursor.getString(0));
            chat.setChatMessage(cursor.getString(1));
            chat.setUsername(cursor.getString(2));
            chats.add(chat);
        } while (cursor.moveToNext());
        return chats;
    }

    public void deleteMessage(int id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_MSGS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

    }

    public List<Chat> getMessageAllMessage() {
        List<Chat> chats = new ArrayList<>();
        String sql = "SELECT * FROM" + TABLE_MSGS;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        do {
            Chat chat = new Chat();
            chat.setChatDate(cursor.getString(0));
            chat.setUsername(cursor.getString(1));
            chat.setChatMessage(cursor.getString(2));
            chats.add(chat);
        } while (cursor.moveToNext());
        return chats;
    }
}
