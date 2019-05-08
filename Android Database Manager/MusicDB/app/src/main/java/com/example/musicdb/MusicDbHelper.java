package com.example.musicdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.musicdb.artists.data.ArtistsContracts;
import com.example.musicdb.gender.data.GenderContract;

public class MusicDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = MusicDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "music.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;


    public MusicDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE IF NOT EXISTS " + GenderContract.GenderEntry.TABLE_NAME + " ("
                + GenderContract.GenderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GenderContract.GenderEntry.COLUMN_GENDER_NAME + " TEXT NOT NULL);";

        String SQL_CREATE_ARTISTS_TABLE =  "CREATE TABLE IF NOT EXISTS " + ArtistsContracts.GenderEntry.TABLE_NAME + " ("
                + ArtistsContracts.GenderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ArtistsContracts.GenderEntry.COLUMN_ARTIST_NAME + " TEXT NOT NULL, "
                + ArtistsContracts.GenderEntry.COLUMN_ARTIST_GENDER + " INTEGER NOT NULL );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
        db.execSQL(SQL_CREATE_ARTISTS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
