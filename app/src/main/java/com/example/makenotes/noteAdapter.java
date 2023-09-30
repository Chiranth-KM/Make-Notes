package com.example.makenotes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

public class noteAdapter extends RecyclerView.Adapter<noteAdapter.ViewHolder> {
    Context context;
    ArrayList<noteView> notes;

    public noteAdapter(Context context, ArrayList<noteView> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.note_layout, parent, false);
        ViewHolder view=new ViewHolder(inflate);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.img.setImageResource(notes.get(position).getImg());
        holder.content.setText(notes.get(position).getContent());
        holder.time.setText(notes.get(position).getTime());
        holder.title.setText(notes.get(position).getTitle());
        holder.id.setText(String.valueOf(notes.get(position).getId()));

        // To Update
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x=String.valueOf(notes.get(position).getId());
                Intent intent = new Intent(context, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("AdapterClass","hello");
                intent.putExtra("title", notes.get(position).getTitle());
                intent.putExtra("note", notes.get(position).getContent());
                intent.putExtra("ids",x);
                context.startActivity(intent);
            }
        });

        // To delete
        holder.button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                Button yes=dialog.findViewById(R.id.yes);
                Button no=dialog.findViewById(R.id.no);

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DataBaseHelper instance = Room.databaseBuilder(context, DataBaseHelper.class, "MakeNotesdb")
                                .allowMainThreadQueries()
                                .build();
                        notesDAO nDAO=instance.nDao();
                        nDAO.delVal(notes.get(position).getId());
                        notes.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            }
        });
    }

    public int getItemCount() {

            return notes.size();
        }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView title, content,time,id;
        CardView button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.titleBox);
            img=itemView.findViewById(R.id.pin);
            content=itemView.findViewById(R.id.content);
            time=itemView.findViewById(R.id.time);
            button=itemView.findViewById(R.id.cardView);
            id=itemView.findViewById(R.id.id);
        }
    }
}
