package com.example.partyinvite.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WaitListDBHelper extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "waitlist.db";
    // Database Version
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructor For Helper Class
     *
     * @param context:Context For Database Helper Class
     */
    public WaitListDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creating A New Database WaitList
     *
     * @param sqLiteDatabase:database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + WaitListContract.WaitListEntry.TABLE_NAME + " (" +
                WaitListContract.WaitListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                WaitListContract.WaitListEntry.COLUMN_GUEST_NAME + "TEXT NOT NULL ," +
                WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE + "INTEGER NOT NULL, " +
                WaitListContract.WaitListEntry.COLUMN_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);

    }

    /**
     * When We Update The App Or Create A New Database Drop Old One
     *
     * @param sqLiteDatabase:Database
     * @param oldVersion:Previous Version
     * @param newVersion:Current  Version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+ WaitListContract.WaitListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
