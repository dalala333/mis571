package com.example.tutorial5duyenle.constant;
public abstract class SQLCommand {
    //query all students
    public static String QUERY_STUDENT;
    //list all data in books table
    public static String QUERY_1;
    //List the call numbers of books with the title
    public static String QUERY_2;
    public static String QUERY_3;
    public static String QUERY_4;
    public static String QUERY_5;
    public static String QUERY_6;
    public static String QUERY_7;

    static {
        QUERY_STUDENT = "select stid, stname from Student";
        QUERY_1 = "select lbcallnum, lbtitle from Libbook";
        QUERY_2 = "select lbcallnum from libbook where lbtitle like '%Database Management%'";
        QUERY_3 = "select lbcallnum from CheckOut where coreturned = 'N'";
        QUERY_4 = "select stid from CheckOut where coreturned = 'N'";
        QUERY_5 = "select lbcallnum from CheckOut where stid = '777'";
        QUERY_6 = "select stid, count(lbcallnum) as booksCheckedOut from CheckOut group by stid";
        QUERY_7 = "select lbcallnum from CheckOut where cofine > 0";
    }

    public static String RETURN_BOOK = "update checkout set coreturned=? where stid=? and lbcallnum=?";
    public static String CHECK_BOOK = "insert into checkout(stid,lbcallnum,coduedate,coreturned) values(?,?,?,?)";
    public static final String CREATE_CHECKOUT_TABLE = "CREATE TABLE IF NOT EXISTS CheckOut (" +
            "stid TEXT PRIMARY KEY," +
            "lbcallnum TEXT NOT NULL," +
            "coduedate TEXT NOT NULL," +
            "coreturned TEXT NOT NULL" +
            ");";
}