package com.example.partyinvite.Data;

import android.provider.BaseColumns;

public class WaitListContract {
    //inner class to define base columns
    public static final class WaitListEntry implements BaseColumns{
        // static members that are in the table and are columns
        public static final String  TABLE_NAME = "waitlist";
        public static final String  COLUMN_GUEST_NAME = "guest";
        public static final String  COLUMN_PARTY_SIZE = "partySize";
        public static final String  COLUMN_TIMESTAMP = "timestamp";

    }
}
