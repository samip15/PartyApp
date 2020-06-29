package com.example.partyinvite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.partyinvite.Data.TextUtils;
import com.example.partyinvite.Data.WaitListContract;
import com.example.partyinvite.Data.WaitListDBHelper;

public class MainActivity extends AppCompatActivity {
    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    private EditText mNewGuestNameEditText, mNewPartySizeEditText;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNewGuestNameEditText = findViewById(R.id.person_name_edit_text);
        mNewPartySizeEditText = findViewById(R.id.person_party_count_edit_text);
        //rv
        RecyclerView waitListRecyclerView;
        waitListRecyclerView = findViewById(R.id.all_guest_list_view);
        waitListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //db
        WaitListDBHelper dbHelper = new WaitListDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
//        TextUtils.insertFakeData(mDb);
        Cursor cursor = getAllGuests();
        mAdapter = new GuestListAdapter(this, cursor);
        waitListRecyclerView.setAdapter(mAdapter);
        //swipe helper for rv
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
               // do nothing
                return false;

            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // get id
                long id  = (long) viewHolder.itemView.getTag();
                // remove guest
                removeGuest(id);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
              View itemview = viewHolder.itemView;
                Paint p = new Paint();
                // red color
                p.setARGB(255,255,0,0);
                if (dX>0){
                    // right swipe
                    c.drawRect((float) itemview.getLeft(),(float) itemview.getTop(),dX,(float)itemview.getBottom(),p);
                }else{
                    // left swipe
                    c.drawRect((float) itemview.getRight()+dX,(float) itemview.getTop(),(float)itemview.getRight(),(float)itemview.getBottom(),p);

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(waitListRecyclerView);
    }
    // ==============================DATABASE OPERATIONS =================================

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
                WaitListContract.WaitListEntry.COLUMN_TIMESTAMP );
    }

    /**
     * Adds new guests to the database
     *
     * @param name:
     * @param partySize:
     */
    private void addNewGuest(String name, int partySize) {
        ContentValues cv = new ContentValues();
        cv.put(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME, name);
        cv.put(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE, partySize);
        mDb.insert(WaitListContract.WaitListEntry.TABLE_NAME, null, cv);
    }

    /**
     * Remove The Guest
     *
     * @param id:guest id
     * @return:true:if removed successfully ,false:if failed
     */
    private boolean removeGuest(long id) {
        return mDb.delete(WaitListContract.WaitListEntry.TABLE_NAME,
                WaitListContract.WaitListEntry._ID + "=" + id,
                null) > 0;
    }

    /**
     * This Method Is Called When User Click On The Add To Waitlist Button
     *
     * @param view:Button Click View
     */
    public void addToWaitList(View view) {
        if (mNewGuestNameEditText.getText().length() == 0 || mNewPartySizeEditText.getText().length() == 0) {
            return;
        }
        // default party size
        int partySize = 1;
        try {
            partySize = Integer.parseInt(mNewPartySizeEditText.getText().toString());

        } catch (NumberFormatException e) {
            Log.e(TAG, "Failed TO Parse Party Size");
        }
        // add new guest
        addNewGuest(mNewGuestNameEditText.getText().toString(), partySize);
        // update cursor with new items in the database
        mAdapter.swapCursor(getAllGuests());
        // clear the edit text
        mNewPartySizeEditText.clearFocus();
        mNewPartySizeEditText.getText().clear();
        mNewGuestNameEditText.getText().clear();
    }
}
