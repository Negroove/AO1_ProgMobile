package com.montoya181.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME    = "contacts.db";
    private static final int    DB_VERSION = 1;

    public static final String TABLE      = "contacts";
    public static final String COL_ID      = "_id";
    public static final String COL_FIRST   = "first_name";
    public static final String COL_LAST    = "last_name";
    public static final String COL_PHONE   = "phone";
    public static final String COL_ADDRESS = "address";
    public static final String COL_GENDER  = "gender";

    public ContactDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE + " ("
                + COL_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_FIRST   + " TEXT, "
                + COL_LAST    + " TEXT, "
                + COL_PHONE   + " TEXT, "
                + COL_ADDRESS + " TEXT, "
                + COL_GENDER  + " TEXT"
                + ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
