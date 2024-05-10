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

public class Expense extends AppCompatActivity {
    TextView textdate;
    EditText editdate;
    DatabaseHelper GoalsDB;
    Button btnsave;
    EditText exp,cat,dt;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        textdate=findViewById(R.id.textdate);
        editdate=findViewById(R.id.editdate);
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        editdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        Expense.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month=month+1;
                        String date=day+"/"+month+"/"+year;
                        editdate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        GoalsDB=new DatabaseHelper(this);
        exp=(EditText) findViewById(R.id.editAmt);
        cat=(EditText) findViewById(R.id.editcatg);
        dt=(EditText) findViewById(R.id.editdate);
        btnsave=(Button) findViewById(R.id.buttonsv);
        ExpAdd();
    }

    public void ExpAdd()
    {
        btnsave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String expense=exp.getText().toString();
                String category=cat.getText().toString();
                String dated=dt.getText().toString();

                boolean insertData=GoalsDB.expAdd(expense,category,dated);

                if(insertData==true)
                {
                    Toast.makeText(Expense.this,"Data Successfully Inserted",Toast.LENGTH_LONG).show();
                }
                else
                {

                    Toast.makeText(Expense.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                }
                exp.setText("");
                cat.setText("");
                dt.setText("");
            }
        });
    }
}
