package com.example.magasin_cms.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.magasin_cms.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public static final int DATABASE_VERSION = 1; // DB Version
    private static final String DATABASE_NAME = "TODO_DATABASE";// DB name
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //Insertion des données
    public void insertTask(ToDoModel model) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, model.getTask());
        values.put(COL_3, 0);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateTask(int id, String task) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, task);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3, status);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks() {
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        modelList.add(task);

                    } while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }


}
