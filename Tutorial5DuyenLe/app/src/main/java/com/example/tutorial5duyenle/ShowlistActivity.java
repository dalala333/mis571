package com.example.tutorial5duyenle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.tutorial5duyenle.util.DBOperator;

public class ShowlistActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showlist_duyenle); // Ensure this layout exists

        listView = findViewById(R.id.checkout_listview);

        // Get the SQL query from the intent
        Intent intent = getIntent();
        String sql = intent.getStringExtra("sql");

        // Execute the SQL query
        try {
            Cursor cursor = DBOperator.execQuery(this, sql);
            if (cursor != null && cursor.getCount() > 0) {
                // Bind the query results to the ListView using SimpleCursorAdapter
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        this,
                        R.layout.listitem_duyenle, // Layout for each list item
                        cursor,
                        new String[]{"stname", "coduedate", "lbtitle"}, // Updated column names
                        new int[]{R.id.stname, R.id.coduedate, R.id.lbtitle}, // View IDs in the layout
                        SimpleCursorAdapter.NO_SELECTION
                );


                // Set the adapter to the ListView
                listView.setAdapter(adapter);

                // Set an item click listener to show a Toast with more details
                listView.setOnItemClickListener((parent, view, position, id) -> {
                    try {
                        Cursor itemCursor = (Cursor) parent.getItemAtPosition(position);
                        String studentName = itemCursor.getString(itemCursor.getColumnIndexOrThrow("stname"));
                        String bookTitle = itemCursor.getString(itemCursor.getColumnIndexOrThrow("lbTitle"));
                        String dueDate = itemCursor.getString(itemCursor.getColumnIndexOrThrow("coduedate"));

                        Toast.makeText(this, "Student: " + studentName + "\nTitle: " + bookTitle + "\nDue Date: " + dueDate, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error reading data from database.", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                });
            } else {
                Toast.makeText(this, "No checkout records found.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to fetch data.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
