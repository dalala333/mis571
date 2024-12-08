package com.example.tutorial5duyenle.view;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.tutorial5duyenle.util.DBOperator;

/**
 * This class is used to show data in the database.
 * It is an extension of TableLayout.
 */
public class TableView extends TableLayout {

    public TableView(Context context, String tableName) {
        super(context);
        String sql = "SELECT * FROM " + tableName + ";";
        Cursor cursor = DBOperator.execQuery(context, sql);
        if (cursor != null) {
            this.extractData(context, cursor);
        } else {
            this.displayNoDataMessage(context);
        }
    }

    public TableView(Context context, Cursor cursor) {
        super(context);
        if (cursor != null) {
            this.extractData(context, cursor);
        } else {
            this.displayNoDataMessage(context);
        }
    }

    /**
     * Fill data in the TableView using a Cursor.
     */
    private void extractData(Context context, Cursor cursor) {
        TextView textView;
        TableRow row;
        boolean first = true;

        while (cursor.moveToNext()) {
            // Display column names as a header for the first row
            if (first) {
                row = new TableRow(context);
                String[] columnNames = cursor.getColumnNames();
                for (String columnName : columnNames) {
                    textView = new TextView(context);
                    textView.setText(columnName);
                    row.addView(textView);
                }
                this.addView(row);

                // Add a separator line
                View line = new View(context);
                line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 2));
                line.setBackgroundColor(0xFF909090);
                this.addView(line);

                first = false;
            }

            // Display values in a row
            row = new TableRow(context);
            int length = cursor.getColumnCount();
            for (int i = 0; i < length; i++) {
                textView = new TextView(context);
                textView.setText(cursor.getString(i));
                row.addView(textView);
            }
            this.addView(row);
        }

        // Close the cursor to avoid database exceptions
        cursor.close();
    }

    /**
     * Display a message when no data is available.
     */
    private void displayNoDataMessage(Context context) {
        TableRow row = new TableRow(context);
        TextView textView = new TextView(context);
        textView.setText("No data available.");
        row.addView(textView);
        this.addView(row);
    }
}
