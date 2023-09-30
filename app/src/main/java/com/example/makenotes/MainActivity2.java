package com.example.makenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity2 extends AppCompatActivity {
    private Button save;
    private ImageButton back;
    private EditText title,note;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        back=(ImageButton) findViewById(R.id.back);
        save= findViewById(R.id.save);
        title= findViewById(R.id.titleBar);
        note= findViewById(R.id.note);
        textView=findViewById(R.id.textView3);


        // Get the current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
        int minute = calendar.get(Calendar.MINUTE);
        String currentTime = String.format("%02d:%02d", hour, minute);


        DataBaseHelper dataBaseHelper=DataBaseHelper.getDB(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent=getIntent();
        if(intent!=null)
        {
            if(intent.hasExtra("MainActivityClass"))
            {
                textView.setText("Add Note");
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String titleText=title.getText().toString();
                        String noteText=note.getText().toString();
                        if(titleText.equals("") && noteText.equals(""))
                        {
                            Toast.makeText(MainActivity2.this, "Feilds can't be empty", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            dataBaseHelper.nDao().addVals(
                                    new noteTable(titleText,noteText)
                            );
                            Intent intent=new Intent(MainActivity2.this, MainActivity.class);
                            intent.putExtra("addValues","hello");
                            intent.putExtra("passTime",currentTime);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
            } else if (intent.hasExtra("AdapterClass")) {

                textView.setText("Update Note");
                String retrivedTitle = intent.getStringExtra("title");
                String retrivedContent = intent.getStringExtra("note");
                String recId=intent.getStringExtra("ids");
                int x=Integer.parseInt(recId);

                title.setText(retrivedTitle);
                note.setText(retrivedContent);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String titleText=title.getText().toString();
                        String noteText=note.getText().toString();
                        dataBaseHelper.nDao().updateNote(
                                x,titleText,noteText
                        );
                        Intent intent=new Intent(MainActivity2.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            else {
                // Do nothing
            }
        }
        else
        {
            // Do nothing
        }

    }
}