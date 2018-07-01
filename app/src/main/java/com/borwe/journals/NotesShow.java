package com.borwe.journals;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import model.Note;

public class NotesShow extends AppCompatActivity {

    TextView note_title,note_date,note_text;
    int note_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_show);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        note_title=findViewById(R.id.note_view_title);
        note_date=findViewById(R.id.note_view_date);
        note_text=findViewById(R.id.note_view_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent data=getIntent();
        note_title.setText(data.getStringExtra("note_title"));
        note_date.setText(data.getStringExtra("note_time"));
        note_text.setText(data.getStringExtra("note_text"));
        note_id=data.getIntExtra("note_id",-1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.notes_view_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.note_view_delete:
                Note.deleteNoteFromDB(note_id,NotesShow.this);
                AlertDialog.Builder alert=new AlertDialog.Builder(NotesShow.this);
                alert.setTitle("Note removed");
                alert.setMessage("Your note has been removed from Journal");
                alert.setPositiveButton("Cool", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NotesShow.this.finish();
                    }
                });
                alert.setCancelable(false);
                alert.create().show();
                break;

            case R.id.note_view_edit:
                Intent data=getIntent();
                Intent update=new Intent(NotesShow.this,NotesUpdate.class);
                update.putExtra("note_time",data.getStringExtra("note_time"));
                update.putExtra("note_title",data.getStringExtra("note_title"));
                update.putExtra("note_text",data.getStringExtra("note_text"));
                update.putExtra("note_id",data.getIntExtra("note_id",-1));
                startActivity(update);
                NotesShow.this.finish();
                break;

            case android.R.id.home:
                NotesShow.this.finish();
                break;
        }
        return true;
    }
}
