//package com.example.tutorial5duyenle;
//import com.example.tutorial5duyenle.constant.SQLCommand;
//import com.example.tutorial5duyenle.util.DBOperator;
//import com.example.tutorial5duyenle.view.TableView;
//import android.app.Activity;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ScrollView;
//import android.widget.Spinner;
//import android.widget.Toast;
//public class QueryActivity extends Activity implements OnClickListener {
//    Button backBtn,resultBtn;
//    Spinner querySpinner;
//    ScrollView scrollView;
////    public void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.query_duyenle);
//////copy database file
////        try{
////            DBOperator.copyDB(getBaseContext());
////        }catch(Exception e){
////            e.printStackTrace();
////        }
////        backBtn=(Button)this.findViewById(R.id.goback_btn);
////        backBtn.setOnClickListener(this);
////        resultBtn=(Button)this.findViewById(R.id.showresult_btn);
////        resultBtn.setOnClickListener(this);
////        querySpinner=(Spinner)this.findViewById(R.id.querylist_spinner);
////        scrollView=(ScrollView)this.findViewById(R.id.scrollview_queryresults);
////    }
//
//    public void onClick(View v)
//    {
//        String sql="";
//        int id=v.getId();
//        if (id==R.id.showresult_btn){
////show query result
//            int pos=querySpinner.getSelectedItemPosition();
//            if (pos==Spinner.INVALID_POSITION){
////User doesn't choose any query, show warning
//                Toast.makeText(this.getBaseContext(), "Please choose a query!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            scrollView.removeAllViews();
//            if (pos==0){
////show all books
//                sql=SQLCommand.QUERY_1;
//            }else if (pos==1){
////list the call numbers of books with the title ‘Database Management’
//                sql=SQLCommand.QUERY_2;
//            }else if (pos==2){
//                sql=SQLCommand.QUERY_3;
//            }else if (pos==3){
//                sql=SQLCommand.QUERY_4;
//            }else if (pos==4){
//                sql=SQLCommand.QUERY_5;
//            }else if (pos==5){
//                sql=SQLCommand.QUERY_6;
//            }else if (pos==6){
//                sql=SQLCommand.QUERY_7;
//            }
//            Cursor cursor=DBOperator.getInstance().execQuery(sql);
//            scrollView.addView(new TableView(this.getBaseContext(),cursor));
//        }else if (id==R.id.goback_btn){
////go back to main screen
//            Intent intent = new Intent(this, DuyenLeActivity.class);
//            this.startActivity(intent);
//        }
//    }
//}
//

package com.example.tutorial5duyenle;

import com.example.tutorial5duyenle.constant.SQLCommand;
import com.example.tutorial5duyenle.util.DBOperator;
import com.example.tutorial5duyenle.view.TableView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class QueryActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "QueryActivity";
    Button backBtn, resultBtn;
    Spinner querySpinner;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_duyenle);

        // Copy database file
        try {
            Log.d(TAG, "Copying database file...");
            DBOperator.copyDB(getBaseContext());
            DBOperator.getInstance().logDatabaseSchema(); // Log schema for debugging
        } catch (Exception e) {
            Log.e(TAG, "Error copying database file: " + e.getMessage(), e);
        }

        // Initialize UI components
        backBtn = findViewById(R.id.goback_btn);
        backBtn.setOnClickListener(this);

        resultBtn = findViewById(R.id.showresult_btn);
        resultBtn.setOnClickListener(this);

        querySpinner = findViewById(R.id.querylist_spinner);
        scrollView = findViewById(R.id.scrollview_queryresults);

        Log.d(TAG, "QueryActivity initialized successfully.");
    }

    @Override
    public void onClick(View v) {
        String sql = "";
        int id = v.getId();

        if (id == R.id.showresult_btn) {
            // Show query result
            int pos = querySpinner.getSelectedItemPosition();
            if (pos == Spinner.INVALID_POSITION) {
                Toast.makeText(this.getBaseContext(), "Please choose a query!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Clear the scroll view before showing results
            scrollView.removeAllViews();

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
                    Toast.makeText(this.getBaseContext(), "Invalid query selection.", Toast.LENGTH_SHORT).show();
                    return;
            }

            try {
                Cursor cursor = DBOperator.getInstance().execQuery(sql);
                scrollView.addView(new TableView(this.getBaseContext(), cursor));
                Log.d(TAG, "Query executed successfully.");
            } catch (Exception e) {
                Log.e(TAG, "Error executing query: " + e.getMessage(), e);
                Toast.makeText(this.getBaseContext(), "Error executing query.", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.goback_btn) {
            Log.d(TAG, "Navigating back to the main activity.");
            Intent intent = new Intent(this, DuyenLeActivity.class);
            this.startActivity(intent);
        }
    }
}
