package com.lagdroidstudios.android.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = (Button) findViewById(R.id.button);
        Button retrieve = (Button) findViewById(R.id.button2);
        mydatabase = openOrCreateDatabase("DBName",MODE_PRIVATE,null);
        //mydatabase.execSQL("DROP TABLE DBTable");
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS DBTable(Data VARCHAR);");
        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                EditText input = (EditText) findViewById(R.id.editText);
                String data = input.getText().toString();
                if(data.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter text in field !",Toast.LENGTH_LONG).show();
                }
                else
                {
                    mydatabase.execSQL("INSERT INTO DBTable VALUES('"+data+"');");
                }
            }
        });

        retrieve.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                StringBuilder output = new StringBuilder("");
                Cursor resultSet = mydatabase.rawQuery("Select * from DBTable",null);
                resultSet.moveToFirst();
                while(!resultSet.isAfterLast())
                {
                    //String data = resultSet.getString(1);
                    output.append(resultSet.getString(0));
                    output.append("\n");
                    resultSet.moveToNext();
                }
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(output);
                //Toast.makeText(getApplicationContext(),output,Toast.LENGTH_LONG).show();
            }
        });
    }
}
