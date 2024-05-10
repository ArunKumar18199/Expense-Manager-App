package com.example.expensemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.Color;
import android.widget.ImageButton;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity   {
    PieChart pieChart;
    String[] salaryList;
    String[] expList;
    Integer totSal=0;
    Integer totExp=0;
    Integer displayRem;
    private ImageButton inctransbtn;
    private ImageButton exptransbtn;
    public  static final String DATABASE_NAME="Goals.db";
    public  static final String TABLE_NAME="Income_table";
    public  static final String COL1="SALARY";
    public  static final String COL2="OCCUPATION";
    public  static final String COL3="DATE";

    public  static final String TABLE_name="Expense_table";
    public  static final String cols1="EXPENSE";
    public  static final String cols2="CATEGORY";
    public  static final String cols3="DATEEXP";

    DatabaseHelper dbHelper=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();

        //Income records
        inctransbtn=(ImageButton) findViewById(R.id.inctransbtn);
        exptransbtn=(ImageButton) findViewById(R.id.exptransbtn);
        //Income button
        ImageButton incomeadd = (ImageButton) findViewById(R.id.incomebtn);

        incomeadd.setOnClickListener(new View.OnClickListener(){
            // When the button is pressed/clicked, it will run the code below
            @Override
            public void onClick(View view){
                // Intent is what you use to start another activity
                Intent intent = new Intent(MainActivity.this, Income.class);
                startActivity(intent);
            }
        });

        //Expense button
        ImageButton expadd = (ImageButton) findViewById(R.id.expensebtn);

        expadd.setOnClickListener(new View.OnClickListener(){
            // When the button is pressed/clicked, it will run the code below
            @Override
            public void onClick(View view){
                // Intent is what you use to start another activity
                Intent intent = new Intent(MainActivity.this, Expense.class);
                startActivity(intent);

            }
        });

        pieChart=(PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(false);
        pieChart.setExtraOffsets(5,5,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        String salList[]=getAllSalary();
        String expenseList[]=getAllExpenses();
        ArrayList<PieEntry> sal=new ArrayList<>();

        //total salary
        for(int i=0;i<salList.length;i++)
        {
            totSal=totSal+(Integer.parseInt(salList[i]));

        }

        //total expenses
        for(int i=0;i<expenseList.length;i++)
        {
            totExp=totExp+(Integer.parseInt(expenseList[i]));
        }

        displayRem=totSal-totExp;
        sal.add(new PieEntry(displayRem, "Balance"));
        sal.add(new PieEntry(totExp, "Total-Exp"));
        PieDataSet dataSet=new PieDataSet(sal,"Income");

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data=new PieData(dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.MAGENTA);

        pieChart.setData(data);
        ViewIncome();
        ViewExpense();
    }

    //get Salaries
    public String[] getAllSalary()
    {

        int i=0;

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db=openOrCreateDatabase("Goals.db", MODE_PRIVATE, null);
        Cursor cursor=db.rawQuery(selectQuery,null);

        int count=cursor.getCount();
        salaryList=new String[count];
        if(cursor.moveToFirst())
        {
            do {
                salaryList[i]=cursor.getString(0);
                i++;

            }while (cursor.moveToNext());
        }
        return  salaryList;
    }

    //get expenses
    public String[] getAllExpenses()
    {
        int i=0;
        String selectQuery="SELECT * FROM "+ TABLE_name;
        SQLiteDatabase db=openOrCreateDatabase("Goals.db", MODE_PRIVATE, null);
        Cursor cursor=db.rawQuery(selectQuery,null);
        int count=cursor.getCount();
        expList=new String[count];
        if(cursor.moveToFirst())
        {
            do {
                expList[i]=cursor.getString(0);
                i++;

            }while (cursor.moveToNext());
        }
        return  expList;
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
    //view income
    public void  ViewIncome()
    {
        inctransbtn.setOnClickListener(new View.OnClickListener(){
            // When the button is pressed/clicked, it will run the code below

            public void onClick(View view) {
                Cursor data=dbHelper.showData();

                if(data.getCount()==0)
                {
                    //message
                    display("Error","No Data Found.");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while (data.moveToNext())
                {
                    buffer.append("\n"+"Income: " + data.getString(0) + "\n");
                    buffer.append("Source: " + data.getString(1) + "\n");
                    buffer.append("Date: " + data.getString(2) + "\n");

                    display("All Income: ", buffer.toString());
                }
            }
        });
    }

    //view expense
    public void  ViewExpense()
    {
        exptransbtn.setOnClickListener(new View.OnClickListener(){
            // When the button is pressed/clicked, it will run the code below
            public void onClick(View view) {
                Cursor data=dbHelper.showExpData();
                if(data.getCount()==0)
                {
                    //message
                    display("Error","No Data Found.");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while (data.moveToNext())
                {
                    buffer.append("\n"+"Expense: " + data.getString(0) + "\n");
                    buffer.append("Category: " + data.getString(1) + "\n");
                    buffer.append("Date: " + data.getString(2) + "\n");

                    display("All Expenses: ", buffer.toString());
                }
            }
        });
    }

    public  void display(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}