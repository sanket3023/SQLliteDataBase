package com.example.sqllitedatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements android.view.View.OnClickListener {


    EditText rollno,name,mark;
    Button Add,Delete,Modify,View,ViewAll,Show;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollno=findViewById(R.id.rollno);
        name=findViewById(R.id.name);
        mark=findViewById(R.id.mark);


        Add=findViewById(R.id.button1);
        Delete=findViewById(R.id.button2);
        Modify=findViewById(R.id.button3);
        View=findViewById(R.id.button4);
        ViewAll=findViewById(R.id.button5);
        Show=findViewById(R.id.button6);

        Add.setOnClickListener(this);
        Delete.setOnClickListener(this);
        Modify.setOnClickListener(this);
        View.setOnClickListener(this);
        ViewAll.setOnClickListener(this);
        Show.setOnClickListener(this);


       db= openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);
       db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno varchar(20),name varcher(20),mark varcher);");


    }

    @Override
    public void onClick(android.view.View v) {

        switch(v.getId()){
            case R.id.button1:

  //1//
                if(rollno.getText().toString().trim().length()==0 ||
                        name.getText().toString().trim().length()==0 ||
                        mark.getText().toString().trim().length()==0)
                {
                    Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
                    shwmsg("Error", "Invalid Input");
                    return;
                }
                    db.execSQL("INSERT INTO student VALUES('"+rollno.getText()+"','"
                                                                +name.getText()+ "','"
                                                                + mark.getText()+"');");
                    shwmsg("Success", "Record added");

                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                clearText();
                break;

//2//

            case R.id.button2:
            {
                if(rollno.getText().toString().trim().length()==0)
                {
                    shwmsg("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+rollno.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM student WHERE rollno='"+rollno.getText()+"'");
                    shwmsg("Success", "Record Deleted");
                }
                else
                {
                    shwmsg("Error", "Invalid Rollno");
                }
                clearText();
            } break;

//3//

            case R.id.button3:
            {
                if(rollno.getText().toString().trim().length()==0)
                {
                    shwmsg("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+rollno.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("UPDATE student SET name='"+name.getText()+"',marks='"+mark.getText()+
                            "' WHERE rollno='"+rollno.getText()+"'");
                    shwmsg("Success", "Record Modified");
                }
                else
                {
                    shwmsg("Error", "Invalid Rollno");
                }
                clearText();
            } break;

//4//
            case R.id.button4:
                if(View==View)
            {
                if(rollno.getText().toString().trim().length()==0)
                {
                    shwmsg("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+rollno.getText()+"'", null);
                if(c.moveToFirst())
                {
                    name.setText(c.getString(1));
                    mark.setText(c.getString(2));
                }
                else
                {
                    shwmsg("Error", "Invalid Rollno");
                    clearText();
                }
            } break;

//5//
            case R.id.button5:
            {
                Cursor c=db.rawQuery("SELECT * FROM student", null);
                if(c.getCount()==0)
                {
                    shwmsg("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("rollno: "+c.getString(0)+"\n");
                    buffer.append("name: "+c.getString(1)+"\n");
                    buffer.append("mark: "+c.getString(2)+"\n\n");
                }
                shwmsg("Student Details", buffer.toString());
            } break;

//6//
            case R.id.button6:
            {
                shwmsg("Student Record Application", "Developed By Sanket Walke");
            }
        }

    }

    private void clearText() {

        mark.setText("");
        name.setText("");
        rollno.setText("");

    }

    private void shwmsg(String title, String msg) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(true);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.show();
    }
}
