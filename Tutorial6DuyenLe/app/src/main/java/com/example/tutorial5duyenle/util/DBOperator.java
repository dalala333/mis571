//package com.example.tutorial5duyenle.util;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import com.example.tutorial5duyenle.constant.DBConstant;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
///**
// * Class to manipulate tables & data
// * Uses singleton pattern to create single instance
// */
//public class DBOperator
//{
//    private static DBOperator instance = null;
//    private SQLiteDatabase db;
//    private DBOperator()
//    {
////path of database file
//        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
//        db = SQLiteDatabase.openDatabase(path, null,
//                SQLiteDatabase.OPEN_READWRITE);
//    }
//    /*
//     * Singleton Pattern
//     * Why should we avoid multiple instances here?
//     */
//    public static DBOperator getInstance()
//    {
//        if (instance==null) instance = new DBOperator();
//        return instance;
//    }
//    /**
//     * Copy database file
//     * From assets folder (in the project) to android folder (on device)
//     */
//    public static void copyDB(Context context) throws
//            IOException,FileNotFoundException{
//        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
//        File file = new File(path);
//        if (!file.exists()){
//            DBOpenHelper dbhelper = new DBOpenHelper(context, path ,1);
//            dbhelper.getWritableDatabase();
//            InputStream is = context.getAssets().open(DBConstant.DATABASE_FILE);
//            OutputStream os = new FileOutputStream(file);
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = is.read(buffer))>0){
//                os.write(buffer, 0, length);
//            }
//            is.close();
//            os.flush();
//            os.close();
//        }
//    }
//    /**
//     * execute sql without returning data, such as alter
//     * @param sql
//     */
//    public void execSQL(String sql)
//    {
//        db.execSQL(sql);
//    }
//    /**
//     * execute sql such as update/delete/insert
//     * @param sql
//     * @param args
//     * @throws SQLException
//     */
//    public void execSQL(String sql, Object[] args)
//    {
//        db.execSQL(sql, args);
//    }
//    /**
//     * execute sql query
//     * @param sql
//     * @param selectionArgs
//     * @return cursor
//     * @throws SQLException
//     */
//    public Cursor execQuery(String sql,String[] selectionArgs)
//    {
//        return db.rawQuery(sql, selectionArgs);
//    }
//    /**
//     * execute query without arguments
//     * @param sql
//     * @return
//     * @throws SQLException
//     */
//    public Cursor execQuery(String sql)
//    {
//        return this.execQuery(sql, null);
//    }
//    /**
//     * close database
//     */
//    public void closeDB()
//    {
//        if (db!=null) db.close();
//    }
//}

package com.example.tutorial5duyenle.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.tutorial5duyenle.constant.DBConstant;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class to manipulate tables & data
 * Uses singleton pattern to create single instance
 */
public class DBOperator {
    private static final String TAG = "DBOperator";
    private static DBOperator instance = null;
    private SQLiteDatabase db;

    private DBOperator() {
        // Path of database file
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        try {
            Log.d(TAG, "Attempting to open database at path: " + path);
            db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
            Log.d(TAG, "Database opened successfully.");
        } catch (Exception e) {
            Log.e(TAG, "Error opening database: " + e.getMessage(), e);
        }
    }

    /*
     * Singleton Pattern
     */
    public static DBOperator getInstance() {
        if (instance == null) {
            Log.d(TAG, "Creating new DBOperator instance.");
            instance = new DBOperator();
        }
        return instance;
    }

    /**
     * Copy database file from assets folder to device's internal storage.
     */
    public static void copyDB(Context context) throws IOException {
        String path = DBConstant.DATABASE_PATH + "/" + DBConstant.DATABASE_FILE;
        File file = new File(path);

        Log.d(TAG, "Checking if database file exists at: " + path);
        if (file.exists()) {
            Log.d(TAG, "Existing database found. Deleting...");
            file.delete(); // Force overwrite
        }

        Log.d(TAG, "Copying database file...");
        new File(DBConstant.DATABASE_PATH).mkdirs(); // Ensure directory exists
        try (InputStream is = context.getAssets().open(DBConstant.DATABASE_FILE);
             OutputStream os = new FileOutputStream(file)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            Log.d(TAG, "Database file copied successfully.");
        } catch (IOException e) {
            Log.e(TAG, "Error copying database file: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Log the database schema for debugging purposes.
     */
    public void logDatabaseSchema() {
        try {
            Log.d(TAG, "Logging database schema...");
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null);
            while (cursor.moveToNext()) {
                String tableName = cursor.getString(0);
                Log.d(TAG, "Table found: " + tableName);

                Cursor columns = db.rawQuery("PRAGMA table_info(" + tableName + ");", null);
                while (columns.moveToNext()) {
                    String columnName = columns.getString(1);
                    String columnType = columns.getString(2);
                    Log.d(TAG, "Table: " + tableName + " | Column: " + columnName + " | Type: " + columnType);
                }
                columns.close();
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Error logging database schema: " + e.getMessage(), e);
        }
    }

    /**
     * Execute SQL query.
     */
    public Cursor execQuery(String sql) {
        try {
            Log.d(TAG, "Executing query: " + sql);
            return db.rawQuery(sql, null);
        } catch (SQLException e) {
            Log.e(TAG, "Error executing query: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Close the database.
     */
    public void closeDB() {
        try {
            if (db != null && db.isOpen()) {
                db.close();
                Log.d(TAG, "Database closed successfully.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error closing database: " + e.getMessage(), e);
        }
    }
}
