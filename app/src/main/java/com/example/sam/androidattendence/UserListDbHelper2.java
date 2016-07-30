package com.example.sam.androidattendence;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sam on 18/7/16.
 */
public class UserListDbHelper2 extends SQLiteOpenHelper {


    public static final String TABLE = "My_Subjects_Data";
    public static final int VERSION = 3;
    // public static final String ID = "ID";
    public static final String ADMISSION_NUMBER = "ADMISSION_NUMBER";
    public static final String NAME = "NAME";
    public static final String BRANCH = "BRANCH";
    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";
    public static final String HOSTEL = "HOSTEL";
    public static final String ROOM_NUMBER = "ROOM_NUMBER";

    public UserListDbHelper2(Context context, String name,CursorFactory factory, int version) {
        super(context, TABLE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        if(sqLiteDatabase==null||!sqLiteDatabase.isOpen())
            createTable();
        else
            Log.i("Database Already","Present");

    }

    void createTable() {

        SQLiteDatabase sdb = this.getWritableDatabase();
        sdb.execSQL("DROP TABLE IF EXISTS " +   TABLE);

        String sql = "CREATE TABLE IF NOT EXISTS My_Subjects_Data(SUBJECT1 VARCHAR,SUBJECT2 VARCHAR,SUBJECT3 VARCHAR,SUBJECT4 VARCHAR,SUBJECT5 VARCHAR,SUBJECT6 VARCHAR,SUBJECT1_ATT INTEGER);";

        System.out.println(sql);
        sdb.execSQL(sql);
        sdb.close();
    }


    public void insertData(String Subject1,String Subject2,String Subject3, String Subject4,String Subject5,String Subject6,int Subject1_Att)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        /*ContentValues values = new ContentValues();
        values.put(ADMISSION_NUMBER, admission_number);
        values.put(NAME,name);
        values.put(BRANCH,branch);
        values.put(MOBILE_NUMBER,mobile_no);
        values.put(HOSTEL,hostel);
        values.put(ROOM_NUMBER,room_no);

        db.insert(TABLE, null, values);
        System.out.println("Submitted...");
        Log.i("Submitted ::", " insert data executed");
        */db.execSQL("INSERT INTO "+TABLE+" VALUES('"+Subject1+"','"+Subject2+"','"+Subject3+"','"+Subject4+"','"+Subject5+"','"+Subject6+"','"+Subject1_Att+"');");
    }

    public void insertData_Subject1_Att(int Subject1_Att){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+TABLE+" VALUES('"+Subject1_Att+"');");
    }

    public Cursor Checkdata()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return query(db, "SELECT * From "+TABLE);
    }

    private Cursor query(SQLiteDatabase db, String query) {
        Cursor cursor = db.rawQuery(query, null);
        System.out.println("Executing Query: " + query);
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}