package com.sassy.contentproviderexample;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class CompanyProvider extends ContentProvider {

    public CompanyProvider() {
    }

    SQLiteDatabase myDB;
    public static final String AUTHORITY = "com.sassy.contentproviderexample";
    public static final Uri CONTENT_URI_1 = Uri.parse("content://"+AUTHORITY + "/emp");
    public static final String DB_name = "company";
    public static final String DB_table = "emp";
    public static final int DB_ver = 1;
    static int EMP = 1;
    static int EMP_ID = 2;

    static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY ,"emp",EMP);
        uriMatcher.addURI(AUTHORITY,"emp/#",EMP_ID);
    }

    private class MyOwnDatabase extends SQLiteOpenHelper{

        public MyOwnDatabase(Context ct){
            super(ct,DB_name,null,DB_ver);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table "+ DB_table +
                    "(_id integer primary key autoincrement,emp_name text,profile text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + DB_table);
        }
    }


    @Override
    public boolean onCreate() {
        MyOwnDatabase database = new MyOwnDatabase(getContext());
        myDB = database.getWritableDatabase();
        if(myDB != null){
            return true;
        }
        else{
            return false;
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = myDB.insert(DB_table,null,values);
        if(row > 0){
            uri = ContentUris.withAppendedId(CONTENT_URI_1,row);
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return uri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(DB_table);

        Cursor cursor = sqLiteQueryBuilder.query(myDB,null,null,
                null,null,null,"_id");
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
