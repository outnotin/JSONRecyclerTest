package com.example.outnotin.testdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by outnotin on 9/28/2016 AD.
 */

public class StretchLab {

    private static StretchLab instance;

    private Context context;
    private SQLiteDatabase database;

    public StretchLab(Context context) {
        this.context = context;
        TestDB stretchDatabase = new TestDB(context);
        database = stretchDatabase.getWritableDatabase();
    }

    public static StretchLab getInstance(Context context){
        if(instance == null){
            instance = new StretchLab(context);
        }
        return instance;
    }

    public Stretch getStretchByPosition(int position){
        return null;
    }
}
