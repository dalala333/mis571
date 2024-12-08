package com.example.tutorial5duyenle.constant;
/**
 * SQL commands
 * Including select/delete/update/insert
 */
public abstract class SQLCommand
{
    //query all students
    public static String QUERY_STUDENT = "select stid, stname from Student";
    //list all data in books table
    public static String QUERY_1 = "select lbcallnum, lbtitle from LibBook";
    //List the call numbers of books with the title ‘Database Management’
    public static String QUERY_2 = "select lbcallnum from LibBook where lbtitle like '%Database Management%'";
    //List all the books currently checked out and not returned
    public static String QUERY_3 = "select lbcallnum from CheckOut where coreturned = 'N'";
    //Find students who currently have books checked out
    public static String QUERY_4 = "select stid from CheckOut where coreturned = 'N'";
    //List all books checked out by student with ID '777'
    public static String QUERY_5 = "select lbcallnum from CheckOut where stid = '777'";
    //Count how many books each student has checked out
    public static String QUERY_6 = "select stid, count(lbcallnum) as booksCheckedOut from CheckOut group by stid";
    // List all books with fines greater than 0
    public static String QUERY_7 = "select lbcallnum from CheckOut where cofine > 0";
}