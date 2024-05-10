package com.example.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public  static final String DATABASE_NAME="Goals.db";
    public  static final String TABLE_NAME="Income_table";
    public  static final String COL1="SALARY";
    public  static final String COL2="OCCUPATION";
    public  static final String COL3="DATE";
    public  static final String TABLE_name="Expense_table";
    public  static final String cols1="EXPENSE";
    public  static final String cols2="CATEGORY";
    public  static final String cols3="DATEEXP";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable="CREATE TABLE " + TABLE_NAME + " (SALARY INTEGER, " + " OCCUPATION TEXT, DATE TEXT )";
        String cTable="CREATE TABLE " + TABLE_name + " (EXPENSE INTEGER, " + " CATEGORY TEXT, DATEEXP TEXT )";
        db.execSQL(createTable);
        db.execSQL(cTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query1,query2;
        query1 = " DROP TABLE IF EXISTS Income_table ";
        query2=" DROP TABLE IF EXISTS Expense_table ";
        sqLiteDatabase.execSQL(query1);
        sqLiteDatabase.execSQL(query2);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String salary, String occp, String dated )
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL1,salary);
        contentValues.put(COL2,occp);
        contentValues.put(COL3,dated);

        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public boolean expAdd(String exp,String catg,String doe)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(cols1,exp);
        contentValues.put(cols2,catg);
        contentValues.put(cols3,doe);
        long result=db.insert(TABLE_name,null,contentValues);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    //Income
    public Cursor showData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return  data;
    }
    //Expense
    public Cursor showExpData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor data=db.rawQuery("SELECT * FROM " + TABLE_name,null);
        return  data;
    }
}

