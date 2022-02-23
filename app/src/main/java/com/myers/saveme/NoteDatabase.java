package com.myers.saveme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION= 2;
    private static final String DATABASE_NAME = "notedb";
    private static final String DATABASE_TABLE = "notestable";

    //column date for database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    NoteDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+DATABASE_TABLE+"("+KEY_ID+" INT PRIMARY KEY,"+
                KEY_TITLE+" TEXT,"+
                KEY_CONTENT+" TEXT,"+
                KEY_DATE+" TEXT,"+
                KEY_TIME+" TEXT"+
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion>=newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
        onCreate(db);
    }

    public long addNote(ListElement listElement){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE,listElement.getTitle());
        c.put(KEY_CONTENT,listElement.getDescription());
        c.put(KEY_DATE,listElement.getDate());
        c.put(KEY_TIME,listElement.getTime());

        long id = db.insert(DATABASE_TABLE,null,c);
        Log.d("inserted","ID -> "+id);
        return id;
    }

    public ListElement getNote(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE,new String[]{KEY_ID,KEY_TITLE,KEY_CONTENT,KEY_DATE,KEY_TIME},KEY_ID+"=?",
                new String[]{String.valueOf(id)},null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        return new ListElement(cursor.getLong(0),cursor.getString(1),cursor.getString(2),
                cursor.getString(3),cursor.getString(4));
    }

    public List<ListElement> getNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<ListElement> allNotes = new ArrayList<>();
        String query = "SELECT * FROM "+DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                ListElement listElement = new ListElement();
                listElement.setId(cursor.getLong(0));
                listElement.setTitle(cursor.getString(1));
                listElement.setDescription(cursor.getString(2));
                listElement.setDate(cursor.getString(3));
                listElement.setTime(cursor.getString(4));

                allNotes.add(listElement);

            }while(cursor.moveToNext());
        }
        return allNotes;
    }
}
