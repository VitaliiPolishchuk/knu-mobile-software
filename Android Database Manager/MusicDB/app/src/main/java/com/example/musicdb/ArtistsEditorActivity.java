package com.example.musicdb;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.musicdb.artists.data.ArtistsContracts;

public class ArtistsEditorActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mGenderEditText;

    /** EditText field to enter the pet's gender */

    private MusicDbHelper mDbHelper;
    /**
     * Gender of the pet. The possible values are:
     * 0 for unknown gender, 1 for male, 2 for female.
     */
    private int mGender = 0;
    private Uri mCurrentUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists_editor);
        mDbHelper = new MusicDbHelper(this);
        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mGenderEditText = (EditText) findViewById(R.id.edit_pet_gender);


        Intent intent = getIntent();
        mCurrentUri = intent.getData();

        if(mCurrentUri != null){

            String [] artistsInfo = queryName(mCurrentUri.getPath());
            mNameEditText.setText(artistsInfo[0]);
            mGenderEditText.setText(artistsInfo[1]);
        }
    }

    private String [] queryName(String id){
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        String [] projection = {
                ArtistsContracts.GenderEntry.COLUMN_ARTIST_NAME,
                ArtistsContracts.GenderEntry.COLUMN_ARTIST_GENDER
        };

        String selection = ArtistsContracts.GenderEntry._ID + " = ?";

        String [] selectionArgs = {
                id
        };

        cursor = (Cursor) database.query(ArtistsContracts.GenderEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        cursor.moveToNext();
        int nameColumnIndex = cursor.getColumnIndex(ArtistsContracts.GenderEntry.COLUMN_ARTIST_NAME);
        int genderColumnIndex = cursor.getColumnIndex(ArtistsContracts.GenderEntry.COLUMN_ARTIST_GENDER);
        String artistsName = cursor.getString(nameColumnIndex);
        String artistGender = cursor.getString(genderColumnIndex);
        String [] res = {artistsName, artistGender};
        cursor.close();
        return res;

    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    private void saveGender() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ArtistsContracts.GenderEntry.COLUMN_ARTIST_NAME, String.valueOf(mNameEditText.getText()));
        values.put(ArtistsContracts.GenderEntry.COLUMN_ARTIST_GENDER, String.valueOf(mGenderEditText.getText()));
        if(mCurrentUri == null) {


            long newRowId = db.insert(ArtistsContracts.GenderEntry.TABLE_NAME, null, values);
        } else {

            String selection = ArtistsContracts.GenderEntry._ID + " = ?";

            String [] selectionArgs = {
                    mCurrentUri.getPath()
            };
            db.update(ArtistsContracts.GenderEntry.TABLE_NAME, values, selection, selectionArgs);
        }
    }

    private void deleteGender(){
        if(mCurrentUri != null) {

            SQLiteDatabase database = mDbHelper.getWritableDatabase();

            String selection = ArtistsContracts.GenderEntry._ID + " = ?";

            String[] selectionArgs = {
                    mCurrentUri.getPath()
            };

            database.delete(ArtistsContracts.GenderEntry.TABLE_NAME, selection, selectionArgs);
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                saveGender();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                deleteGender();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
