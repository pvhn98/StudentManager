package com.sii.studentmanager.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sii.studentmanager.model.ClassModel;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public StudentSqlite sqlite;

    public Controller(Context context){
        sqlite = new StudentSqlite(context);
    }
    public long insertData(ClassModel aClass){
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentSqlite.dbClass.C_ID,aClass.getId());
        contentValues.put(StudentSqlite.dbClass.C_NAME, aClass.getName());

        long result = sqLiteDatabase.insert(StudentSqlite.dbClass.T_NAME, null, contentValues);
        sqLiteDatabase.close();
        return result;
    }

    public List<ClassModel> getData(){
        List<ClassModel> classes=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = sqlite.getReadableDatabase();
        String select = "select * from "+ StudentSqlite.dbClass.T_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        if (cursor.moveToFirst()){
            do {
                classes.add(new ClassModel(cursor.getString(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return classes;
    }

    public long deleteData(String id){
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        long result = sqLiteDatabase.delete(StudentSqlite.dbClass.T_NAME, StudentSqlite.dbClass.C_ID+"=?", new String[]{id});
        sqLiteDatabase.close();
        return result;
    }

    public long updateData(String id, String newId, String name){
        SQLiteDatabase sqLiteDatabase = sqlite.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudentSqlite.dbClass.C_ID, newId);
        contentValues.put(StudentSqlite.dbClass.C_NAME, name);
        long result = sqLiteDatabase.update(StudentSqlite.dbClass.T_NAME, contentValues, StudentSqlite.dbClass.C_ID+"=?", new String[]{id});
        sqLiteDatabase.close();
        return result;
    }
}


