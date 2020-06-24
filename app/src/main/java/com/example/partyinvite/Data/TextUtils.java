package com.example.partyinvite.Data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    public static void insertFakeData(SQLiteDatabase db){
        if (db == null){
            return;
        }
        // create list of fake guest
        List<ContentValues> list = new ArrayList<>();
        ContentValues cv = new ContentValues();
        cv.put(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME,"samip");
        cv.put(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE,50);
        list.add(cv);
        cv = new ContentValues();
        cv.put(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME,"manish");
        cv.put(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE,10);
        list.add(cv);
        cv = new ContentValues();
        cv.put(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME,"Ram");
        cv.put(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE,20);
        list.add(cv);
        cv = new ContentValues();
        cv.put(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME,"Bikash");
        cv.put(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE,15);
        list.add(cv);
        cv = new ContentValues();
        cv.put(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME,"Hari");
        cv.put(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE,30);
        list.add(cv);
        cv = new ContentValues();
        cv.put(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME,"sita");
        cv.put(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE,40);
        list.add(cv);

        // Insert All Guests
        try{
            db.beginTransaction();
            //clear the table first
            db.delete(WaitListContract.WaitListEntry.TABLE_NAME,null,null);
            //go through list one by one
            for (ContentValues c :list){
               db.insert(WaitListContract.WaitListEntry.TABLE_NAME,null,c);
            }
            db.setTransactionSuccessful();
        }catch(SQLException e){
            //error
        }
        finally{
            db.endTransaction();
        }
    }
}
