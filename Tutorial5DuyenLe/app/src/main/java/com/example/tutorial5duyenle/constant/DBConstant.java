package com.example.tutorial5duyenle.constant;
/**
 * Constants related to database file
 * Such as file path and file name
 */
import android.content.Context;

public abstract class DBConstant {
    public static final String DATABASE_FILE = "library.db";
    public static final int DATABASE_VERSION = 1;

    // Add this method to dynamically fetch the database path
    public static String getDatabasePath(Context context) {
        return context.getDatabasePath(DATABASE_FILE).getAbsolutePath();
    }
}