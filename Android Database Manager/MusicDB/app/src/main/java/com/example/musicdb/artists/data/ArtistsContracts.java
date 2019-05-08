package com.example.musicdb.artists.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ArtistsContracts {
    private ArtistsContracts() {}


    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static final class GenderEntry implements BaseColumns {



        /** Name of database table for pets */
        public final static String TABLE_NAME = "artists";

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_ARTIST_NAME = "name";
        public final static String COLUMN_ARTIST_GENDER = "gender";
    }

}