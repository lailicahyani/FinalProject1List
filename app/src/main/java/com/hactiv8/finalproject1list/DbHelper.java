package com.hactiv8.finalproject1list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "ListDatabase";
    public static final String TABLE = "Task";
    public static final String COLUMN = "TaskName";

    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s " +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);",
                TABLE, COLUMN);
        db.execSQL(query);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the older tables
        String query = String.format("DELETE TABLE IF EXISTS %s", TABLE);
        db.execSQL(query);
        //Create tables again
        onCreate(db);
    }

    public void insertNewTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN, task);
        db.insertWithOnConflict(TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, COLUMN + " = ?", new String[]{task});
        db.close();
    }

    public ArrayList<String> getTaskList(){ ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db =  this.getWritableDatabase();
        Cursor cursor =  db.query(TABLE, new String[]{
                COLUMN}, null, null, null, null, null);
        while (cursor.moveToNext()){
            int index = cursor.getColumnIndex(COLUMN);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }
}
