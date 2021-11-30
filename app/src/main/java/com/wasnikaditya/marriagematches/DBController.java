package com.wasnikaditya.marriagematches;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

public class DBController extends SQLiteOpenHelper {
    // All Static variables
    public Context context;

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "marriageDatabase.db";

    private static final String TABLE_MARRIAGE = "table_marriage";
    private static final String MARRIAGE_ID = "marriage_id";
    private static final String MARRIAGE_NAME = "marriage_name";
    private static final String MARRIAGE_EMAIL = "marriage_email";
    private static final String MARRIAGE_GENDER = "marriage_gender";
    private static final String MARRIAGE_IMAGE = "marriage_image";
    private static final String MARRIAGE_BIRTHDATE = "marriage_birthdate";
    private static final String MARRIAGE_AGE = "marriage_age";


    Bitmap bitmap, bitmap1, bitmapDeviceList, bitmapDeviceConnectedStatusImage;


    public DBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) //PRIMARY KEY
    {

        String CREATE_TABLE_MARRIAGE = "CREATE TABLE " + TABLE_MARRIAGE + "(" + MARRIAGE_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + MARRIAGE_NAME + " TEXT ," + MARRIAGE_EMAIL + " TEXT, " + MARRIAGE_GENDER + " TEXT,"   + MARRIAGE_BIRTHDATE + " TEXT, " + MARRIAGE_AGE + " TEXT " + ")";
        db.execSQL(CREATE_TABLE_MARRIAGE);

      //  + MARRIAGE_IMAGE + " BLOB,
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARRIAGE);
        // Create tables again
        onCreate(db);

    }




    //*******************************
   // public void insertMarriageDetail(String name, String email, String gender, int image, String birthdate, String age) throws SQLiteException {
    public void insertMarriageDetail(String name, String email, String gender, String birthdate, String age) throws SQLiteException {

     /*   bitmap1 = BitmapFactory.decodeResource(context.getResources(), Integer.parseInt(image));
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byte[] byteArray1 = stream1.toByteArray();*/


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MARRIAGE_NAME, name);
        values.put(MARRIAGE_EMAIL, email);
        values.put(MARRIAGE_GENDER, gender);
       // values.put(MARRIAGE_IMAGE, byteArray1);
       // values.put(MARRIAGE_IMAGE, image);
        values.put(MARRIAGE_BIRTHDATE, birthdate);
        values.put(MARRIAGE_AGE, age);

        db.insert(TABLE_MARRIAGE, null, values);
        db.close(); // Closing database connection
    }


    //*********Delete Device List************************
    public void deleteMarriageList()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from table_marriage");
        db.close();
    }
    //*********Get row count*********
    //public boolean getRowCountCloud()
    public int getRowCountCloud()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MARRIAGE;
        System.out.println("selectQuery 1278:::: "+selectQuery);
        Cursor cursor      = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
            if(cursor.getCount() > 0)
            {
                cursor.close();
                return 1;
            }
        }
        return 0;
    }



}