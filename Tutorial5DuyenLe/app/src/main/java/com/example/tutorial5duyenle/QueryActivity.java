package com.example.tutorial5duyenle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tutorial5duyenle.constant.SQLCommand;
import com.example.tutorial5duyenle.util.DBOperator;
import com.example.tutorial5duyenle.view.TableView;

public class QueryActivity extends Activity implements View.OnClickListener {

    private Button backBtn, resultBtn;
    private Spinner querySpinner;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_duyenle);

        // Copy the database file
        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to copy database", Toast.LENGTH_SHORT).show();
        }

        // Initialize UI components
        backBtn = findViewById(R.id.goback_btn);
        resultBtn = findViewById(R.id.showresult_btn);
        querySpinner = findViewById(R.id.querylist_spinner);
        scrollView = findViewById(R.id.scrollview_queryresults);

        // Set click listeners
        backBtn.setOnClickListener(this);
        resultBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.showresult_btn) {
            // Show query result
            handleQueryExecution();
        } else if (id == R.id.goback_btn) {
            // Go back to the main screen
            Intent intent = new Intent(this, DuyenLeActivity.class);
            startActivity(intent);
        }
    }

    private void handleQueryExecution() {
        int pos = querySpinner.getSelectedItemPosition();
        if (pos == Spinner.INVALID_POSITION) {
            // User didn't choose any query, show a warning
            Toast.makeText(this, "Please choose a query!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Clear previous query results
        scrollView.removeAllViews();

        // Map selected position to SQL command
        String sql;
        switch (pos) {
            case 0:
                sql = SQLCommand.QUERY_1;
                break;
            case 1:
                sql = SQLCommand.QUERY_2;
                break;
            case 2:
                sql = SQLCommand.QUERY_3;
                break;
            case 3:
                sql = SQLCommand.QUERY_4;
                break;
            case 4:
                sql = SQLCommand.QUERY_5;
                break;
            case 5:
                sql = SQLCommand.QUERY_6;
                break;
            case 6:
                sql = SQLCommand.QUERY_7;
                break;
            default:
                Toast.makeText(this, "Invalid query selection", Toast.LENGTH_SHORT).show();
                return;
        }

        // Execute the SQL query
        try {
            Cursor cursor = DBOperator.execQuery(this, sql);
            if (cursor != null && cursor.getCount() > 0) {
                // Display query results in a TableView
                scrollView.addView(new TableView(this, cursor));
                Toast.makeText(this, "Query executed successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to execute query", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
