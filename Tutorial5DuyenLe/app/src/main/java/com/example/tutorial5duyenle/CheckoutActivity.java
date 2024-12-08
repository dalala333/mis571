package com.example.tutorial5duyenle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tutorial5duyenle.constant.SQLCommand;
import com.example.tutorial5duyenle.util.DBOperator;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        String[] args = new String[4];

        // Student ID
        args[0] = stuIdEdit.getText().toString().trim();

        // Book call number
        args[1] = bookIdEdit.getText().toString().trim();

        // Date
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        // Format the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        args[2] = dateFormat.format(calendar.getTime());

        // Returned state
        args[3] = isCheckout ? "N" : "Y";

        return args;
    }
}
