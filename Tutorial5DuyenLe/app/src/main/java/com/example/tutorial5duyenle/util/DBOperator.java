package com.example.tutorial5duyenle.util;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tutorial5duyenle.constant.DBConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBOperator {

    private static SQLiteDatabase db;

    // Get or open the database instance
    private static SQLiteDatabase getDatabaseInstance(Context context) {
        if (db == null || !db.isOpen()) {
            String path = DBConstant.getDatabasePath(context);
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        }
        return db;
    }

    // Execute SQL without arguments
    public static void execSQL(Context context, String sql) {
        SQLiteDatabase db = getDatabaseInstance(context);
        try {
            db.execSQL(sql);
            Log.d("DBOperator", "Executed SQL: " + sql);
        } catch (SQLException e) {
            Log.e("DBOperator", "SQL execution failed: " + sql, e);
        }
    }

    // Execute SQL with arguments
    public static void execSQL(Context context, String sql, Object[] args) {
        SQLiteDatabase db = getDatabaseInstance(context);
        try {
            db.execSQL(sql, args);
            Log.d("DBOperator", "Executed SQL with arguments: " + sql);
        } catch (SQLException e) {
            Log.e("DBOperator", "SQL execution failed: " + sql, e);
        }
    }

    // Execute SQL query and return a Cursor
    public static Cursor execQuery(Context context, String sql) {
        SQLiteDatabase db = getDatabaseInstance(context);
        try {
            Cursor cursor = db.rawQuery(sql, null);
            Log.d("DBOperator", "Executed query: " + sql);
            return cursor;
        } catch (SQLException e) {
            Log.e("DBOperator", "Query execution failed: " + sql, e);
            return null;
        }
    }

    // Copy database from assets to the application's data directory
    public static void copyDB(Context context) throws IOException {
        String path = DBConstant.getDatabasePath(context);
        File file = new File(path);

        if (!file.exists()) {
            try {
                // Create the database directory if it doesn't exist
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    parentDir.mkdirs();
                }

                // Copy the database file from assets
                InputStream is = context.getAssets().open(DBConstant.DATABASE_FILE);
                OutputStream os = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                is.close();
                os.flush();
                os.close();

                Log.d("DBOperator", "Database copied successfully to: " + path);
            } catch (IOException e) {
                Log.e("DBOperator", "Failed to copy database", e);
                throw e;
            }
        } else {
            Log.d("DBOperator", "Database already exists at: " + path);
        }
    }

    // Close the database connection
    public static void closeDB() {
        if (db != null && db.isOpen()) {
            db.close();
            db = null;
            Log.d("DBOperator", "Database connection closed.");
        }
    }
}
