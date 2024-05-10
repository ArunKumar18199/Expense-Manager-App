package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class Income extends AppCompatActivity  {
    TextView dateid;
    EditText dtselect;
    DatabaseHelper GoalsDB;
    Button btnsave;
    EditText sal,occ,dt;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        dateid=findViewById(R.id.dateid);
        dtselect=findViewById(R.id.dtselect);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        dtselect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Income.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        dtselect.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        GoalsDB=new DatabaseHelper(this);
        sal=(EditText) findViewById(R.id.Salaryamt);
        occ=(EditText) findViewById(R.id.occp);
        dt=(EditText) findViewById(R.id.dtselect);
        btnsave=(Button) findViewById(R.id.buttonsave);
        AddData();
    }

    public void AddData()
    {
        btnsave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String salary=sal.getText().toString();
                String occp=occ.getText().toString();
                String dated=dt.getText().toString();

                boolean insertData=GoalsDB.addData(salary,occp,dated);

                if(insertData==true)
                {
                    Toast.makeText(Income.this,"Data Successfully Inserted",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(Income.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
                sal.setText("");
                occ.setText("");
                dt.setText("");
            }
        });
    }
}