package com.example.musicdb.artists;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.musicdb.ArtistsEditorActivity;
import com.example.musicdb.MusicDbHelper;
import com.example.musicdb.R;
import com.example.musicdb.artists.data.ArtistsContracts;
import com.example.musicdb.artists.AtristsCursorAdapter;
import com.example.musicdb.gender.GenderCursorAdapter;

public class ArtistsActivity extends AppCompatActivity {

    private MusicDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);

        // Setup FAB to open EditorActivity
        mDbHelper = new MusicDbHelper(this);
        ListView petListView = (ListView) findViewById(R.id.list);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        mDbHelper.onCreate(db);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor
//        Log.v("GenderActivity", "Set cursor adapter");
        // Setup the item click listener
        petListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(ArtistsActivity.this, ArtistsEditorActivity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link GenderEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on.


                // Set the URI on the data field of the intent
                intent.setData(Uri.parse(Long.toString(id)));

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                ArtistsContracts.GenderEntry._ID,
                ArtistsContracts.GenderEntry.COLUMN_ARTIST_NAME,
                ArtistsContracts.GenderEntry.COLUMN_ARTIST_GENDER};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                ArtistsContracts.GenderEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        ListView petListView = (ListView) findViewById(R.id.list);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        AtristsCursorAdapter adapter = new AtristsCursorAdapter(this, cursor);

        // Attach the adapter to the ListView.
        petListView.setAdapter(adapter);
    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertGender() {
        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ArtistsContracts.GenderEntry.COLUMN_ARTIST_NAME, "Toto");
        values.put(ArtistsContracts.GenderEntry.COLUMN_ARTIST_GENDER, 1);


        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(ArtistsContracts.GenderEntry.TABLE_NAME, null, values);

        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link GenderEntry#CONTENT_URI} to indicate that we want to insert
        // into the pets database table.
        // Receive the new content URI that will allow us to access Toto's data in the future.
    }

    /**
     * Helper method to delete all pets in the database.
     */
    private void deleteAllPets() {
//        int rowsDeleted = getContentResolver().delete(ArtistsContracts.GenderEntry.CONTENT_URI, null, null);
//        Log.v("CatalogActivity", rowsDeleted + " rows deleted from pet database");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertGender();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllPets();
                return true;
            case R.id.action_add_entries:
                Intent intent = new Intent(ArtistsActivity.this, ArtistsEditorActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
