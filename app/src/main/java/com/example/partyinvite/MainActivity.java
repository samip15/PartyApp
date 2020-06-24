package com.example.partyinvite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.partyinvite.Data.TextUtils;
import com.example.partyinvite.Data.WaitListContract;
import com.example.partyinvite.Data.WaitListDBHelper;

public class MainActivity extends AppCompatActivity {
    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //rv
        RecyclerView waitlistRecyclerView;
        waitlistRecyclerView = findViewById(R.id.all_guest_list_view);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //db
        WaitListDBHelper dbHelper = new WaitListDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        TextUtils.insertFakeData(mDb);
        Cursor cursor = getAllGuests();


        mAdapter = new GuestListAdapter(this,cursor);
        waitlistRecyclerView.setAdapter(mAdapter);

    }


    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {

    }

    /**
     * Query the mDb and get all guests from the waitlist table
     *
     * @return Cursor containing the list of guests
     */
    private Cursor getAllGuests() {
        // COMPLETED (6) Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDb.query(
                WaitListContract.WaitListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitListContract.WaitListEntry.COLUMN_TIMESTAMP
        );
    }

    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitList(View view) {
    }
}