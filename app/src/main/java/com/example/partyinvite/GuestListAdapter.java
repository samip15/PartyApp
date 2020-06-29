package com.example.partyinvite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.partyinvite.Data.WaitListContract;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public GuestListAdapter(Context mContext,Cursor cursor)
    {
        this.mContext = mContext;
        this.mCursor = cursor;

    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)){
            return;
        }
        //guest's name
        String name = mCursor.getString(mCursor.getColumnIndex(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME));
        //party size
        int partySize = mCursor.getInt(mCursor.getColumnIndex(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE));
        // id
        long id = mCursor.getLong(mCursor.getColumnIndex(WaitListContract.WaitListEntry._ID));
        // tag
        holder.itemView.setTag(id);
        //display name and size
        holder.nameTextView.setText(name);
        holder.partySizeTextView.setText(String.valueOf(partySize));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /**
     * swaps the cursor with a new one
     * @param newCursor:updated cursor
     */
    public void swapCursor(Cursor newCursor){
        if (mCursor!=null){
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor!=null){
            notifyDataSetChanged();
        }
    }

    public class GuestViewHolder extends RecyclerView.ViewHolder {
        // Will display the guest name
        TextView nameTextView;
        // Will display the party size number
        TextView partySizeTextView;
        public GuestViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_text_view);
            partySizeTextView =  itemView.findViewById(R.id.party_size_text_view);
        }
    }
}
