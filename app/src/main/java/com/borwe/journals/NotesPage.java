package com.borwe.journals;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import model.Note;

public class NotesPage extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_page);
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView=findViewById(R.id.notes_recycler);
        notes=Note.getNotesFromDB(NotesPage.this);

        Toast.makeText(getApplicationContext(),notes.size()+" SIZE ",Toast.LENGTH_LONG).show();

        RecyclerView.LayoutManager recyclerManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerManager);
        recyclerView.setAdapter(new NotesAdapter(notes));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.notes_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_note_menu_item:
                Intent add_notes=new Intent(NotesPage.this,NotesAdd.class);
                startActivity(add_notes);
        }
        return true;
    }

    class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder>{

        private List<Note> notes;

        public NotesAdapter(List<Note> notes){
            this.notes=notes;
        }

        @NonNull
        @Override
        public NotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_summary,parent,false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final NotesAdapter.MyViewHolder holder, int position) {
            final Note note=notes.get(position);
            holder.note_time.setText(note.getDate());
            holder.note_title.setText(note.getTopic());
            String text=note.getText().substring(0,8)+"...";
            holder.note_text.setText(text);

            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent details=new Intent(holder.main.getContext(),NotesShow.class);
                    details.putExtra("note_time",note.getDate());
                    details.putExtra("note_title",note.getTopic());
                    details.putExtra("note_text",note.getText());
                    details.putExtra("note_id",note.getNote_id());
                    startActivity(details);
                }
            });
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            public TextView note_time,note_title,note_text;
            public CardView main;

            public MyViewHolder(View itemView) {
                super(itemView);
                note_time = itemView.findViewById(R.id.note_date);
                note_title=itemView.findViewById(R.id.note_title);
                note_text=itemView.findViewById(R.id.note_text);
                main=itemView.findViewById(R.id.notes_main);
            }
        }
    }
}
