package com.example.tutorial5duyenle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.util.Log; // Add this import for logging
import com.example.tutorial5duyenle.util.DBOperator;
public class DuyenLeActivity extends Activity implements OnClickListener{
    Button checkoutBtn,queryBtn;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons
        Button checkoutBtn = findViewById(R.id.goCheckOut_btn);
        Button queryBtn = findViewById(R.id.goDoQuery_btn);

        // Set up listener for the Check Out button
        checkoutBtn.setOnClickListener(v -> {
            Log.d("Navigation", "Navigating to CheckoutActivity");
            Intent intent = new Intent(this, CheckoutActivity.class);
            this.startActivity(intent);
        });

        // Set up listener for the Do Query button
        queryBtn.setOnClickListener(v -> {
            Log.d("Navigation", "Navigating to QueryActivity");
            Intent intent = new Intent(this, QueryActivity.class);
            this.startActivity(intent);
        });

        // Copy the database
        try {
            DBOperator.copyDB(getBaseContext());
        } catch (Exception e) {
            Log.e("Database", "Failed to copy database", e);
        }
    }

    public void onClick(View v)
    {
        int id=v.getId();
        if (id==R.id.goCheckOut_btn){
            Intent intent = new Intent(this, CheckoutActivity.class);
            this.startActivity(intent);
        }
        else if (id==R.id.goDoQuery_btn){
            Intent intent = new Intent(this, QueryActivity.class);
            this.startActivity(intent);
        }
    }
}
