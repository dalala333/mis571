package com.example.tutorial5duyenle;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tutorial5duyenle.constant.SQLCommand;
import com.example.tutorial5duyenle.util.DBOperator;
import com.example.tutorial5duyenle.util.Pair;
import com.example.tutorial5duyenle.view.ChartGenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class CheckoutActivity extends Activity implements View.OnClickListener {
    private EditText stuIdEdit, bookIdEdit;
    private DatePicker datePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_duyenle);

        // Initialize UI components
        stuIdEdit = findViewById(R.id.edittext_studentID);
        bookIdEdit = findViewById(R.id.edittext_bookID);
        datePicker = findViewById(R.id.datePicker1);

        // Set click listeners for buttons
        findViewById(R.id.checkout_btn).setOnClickListener(this);
        findViewById(R.id.return_btn).setOnClickListener(this);
        findViewById(R.id.goBack_btn).setOnClickListener(this);
        findViewById(R.id.summary_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        try {
            if (id == R.id.checkout_btn) {
                // Check out a book
                DBOperator.execSQL(this, SQLCommand.CHECK_BOOK, this.getArgs(true));
                Toast.makeText(getBaseContext(), "Checkout successfully", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.return_btn) {
                // Return a book
                DBOperator.execSQL(this, SQLCommand.RETURN_BOOK, this.getArgs(false));
                Toast.makeText(getBaseContext(), "Return successfully", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.goBack_btn) {
                // Go back to the main screen
                Intent intent = new Intent(this, DuyenLeActivity.class);
                startActivity(intent);
            } else if (id == R.id.summary_btn) {
                // Show summary chart
                Cursor cursor = DBOperator.execQuery(this, SQLCommand.CHECKOUT_SUMMARY);
                List<Pair> pairList = new LinkedList<>();
                for (int i = 1; i <= 12; i++) {
                    Pair pair = new Pair(i, 0);
                    pairList.add(pair);
                }
                while (cursor.moveToNext()) {
                    int location = Integer.parseInt(cursor.getString(0));
                    pairList.get(location - 1).setNumber(Double.parseDouble(cursor.getString(1)));
                }
                Intent intent = ChartGenerator.getBarChart(getBaseContext(), "Checkout Summary in 2017", pairList);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error processing request", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * Get input data including studentID, book callnum, date, and returned state
     *
     * @param isCheckout true for checkout, false for return
     * @return String array of arguments
     */
    @NonNull
    private String[] getArgs(boolean isCheckout) {
        String[] args;
        if (isCheckout) {
            args = new String[4];
            args[0] = stuIdEdit.getText().toString().trim(); // Student ID
            args[1] = bookIdEdit.getText().toString().trim(); // Book call number
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            args[2] = dateFormat.format(calendar.getTime()); // Date
            args[3] = "N"; // Returned state
        } else {
            args = new String[3];
            args[0] = "Y"; // Returned state
            args[1] = stuIdEdit.getText().toString().trim(); // Student ID
            args[2] = bookIdEdit.getText().toString().trim(); // Book call number
        }
        return args;
    }
}
