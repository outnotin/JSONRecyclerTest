package com.example.outnotin.testdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by outnotin on 9/26/2016 AD.
 */
public class TestDB extends SQLiteOpenHelper {

    private static TestDB instance = null;

    private static final String TAG = "TestDB";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "stretchdatabase";
    private static final String DATABASE_TABLE = "stretch";

    private List<Stretch> stretchList;

    public TestDB(Context context) {
        super(context, DATABASE_NAME,  null, DATABASE_VERSION);
    }

    public static TestDB getInstance(Context context){
        if(instance == null){
            instance = new TestDB(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + DATABASE_TABLE +
                "(sid INTEGER PRIMARY KEY AUTOINCREMENT," +
                "sname VARCHAR(100)," +
                "sinfo VARCHAR(250)," +
                "spath VARCHAR(250));");

        Log.d(TAG, "onCreate: create table success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int getDatabaseSize(){
        try {
            SQLiteDatabase database;
            database = this.getReadableDatabase();

            String strSQL = "SELECT COUNT(*) FROM stretch";
            Cursor cursor = database.rawQuery(strSQL, null);
            int totalRow = cursor.getInt(0);
            return totalRow;
        }catch (Exception e){
            return -1;
        }
    }

    //Insert name to database (id auto increment)
    public long insertData(String sname, String sinfo, String spath){
        try{
            SQLiteDatabase db;
            db = this.getWritableDatabase();

            ContentValues val = new ContentValues();
            val.put("sname", sname);
            val.put("sinfo", sinfo);
            val.put("spath", spath);

            long rows = db.insert(DATABASE_TABLE, null, val);
            db.close();

            return rows;

        }catch (Exception e){
            return -1;
        }
    }

    //Delete last row
    public long deleteData(){
        try {
            SQLiteDatabase db;
            db = this.getWritableDatabase();

            long rows = db.delete(DATABASE_TABLE, "sid = (SELECT MAX(sid) FROM " + DATABASE_TABLE + ")", null);

            return rows;
        }catch (Exception e){
            return -1;
        }
    }

    public List<Stretch> selectAll(){
        SQLiteDatabase database;
        database = this.getReadableDatabase();
        String strSQL = "SELECT sid as _id , * FROM " + DATABASE_TABLE;
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, null);
        Stretch mStretch;
        stretchList = new ArrayList<>();

        try{
            Log.d(TAG, "selectAll: Try before if");
            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()){
                mStretch = new Stretch();
                mStretch.setsName(cursor.getString(cursor.getColumnIndex("sname")));
                mStretch.setsInfo(cursor.getString(cursor.getColumnIndex("sinfo")));
                mStretch.setsInfo(cursor.getString(cursor.getColumnIndex("spath")));

//                Log.d(TAG, "selectAll: " + cursor.getString(cursor.getColumnIndex("sname")));

                stretchList.add(mStretch);
                Log.d(TAG, "selectAll: " + stretchList.get(i).getsName());


                cursor.moveToNext();
            }
//            return cursor;
        }catch (Exception e){
            Log.e(TAG, "selectAll: ", e);
//            return null;
        }finally {
            cursor.close();
            Log.d(TAG, "selectAll: Finally");
            return stretchList;
        }
    }

}
