package com.example.makenotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addfile;
    RecyclerView recyclerView;
    View emptystate;

    ArrayList<noteView> notes=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addfile=findViewById(R.id.addfile);
        recyclerView=findViewById(R.id.recyclerView2);
        emptystate=findViewById(R.id.emptyView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // To set custom display layout

        DataBaseHelper dataBaseHelper=DataBaseHelper.getDB(this);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
        int minute = calendar.get(Calendar.MINUTE);
        String currentTime = String.format("%02d:%02d", hour, minute);
        noteAdapter adapter=new noteAdapter(this,notes);
        recyclerView.setAdapter(adapter);

        addfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("MainActivityClass","Hello");
                startActivity(intent);
                finish();
            }
        });

        ArrayList<noteTable> noteViews=(ArrayList<noteTable>) dataBaseHelper.nDao().dispValues();
        for(int i=0; i<noteViews.size();i++)
        {
            notes.add(new noteView(R.drawable.pin,noteViews.get(i).getTitle(),
                    noteViews.get(i).getContent(),currentTime,noteViews.get(i).getId()));

        }
        adapter.notifyItemInserted(notes.size()-1);
        recyclerView.scrollToPosition(notes.size()-1);

        if(adapter.getItemCount()==0)
        {
            recyclerView.setVisibility(View.GONE);
            emptystate.setVisibility(View.VISIBLE);
        }
        else
        {
            recyclerView.setVisibility(View.VISIBLE);
            emptystate.setVisibility(View.GONE);
        }
    }

}