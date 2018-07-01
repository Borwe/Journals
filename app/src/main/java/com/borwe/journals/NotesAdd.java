package com.borwe.journals;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import db.ApplicationDatabase;
import model.Note;

public class NotesAdd extends AppCompatActivity {

    TextView dateInput;
    EditText titleInput;
    EditText textInput;
    Button addNotesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateInput=findViewById(R.id.input_date);
        titleInput=findViewById(R.id.input_title);
        textInput=findViewById(R.id.input_text);
        addNotesButton=findViewById(R.id.add_notes_button);

        addNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNote().execute();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return  true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Calendar today=Calendar.getInstance();
        String date=today.get(Calendar.DAY_OF_MONTH)+"/"+(today.get(Calendar.MONTH)+1)+"/"+
            today.get(Calendar.YEAR);
        dateInput.setText(date);
    }

    class AddNote extends AsyncTask<Void,Void,Void>{

        ProgressDialog dialog;

        String error_message=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(NotesAdd.this);
            dialog.setMessage("Please wait, adding");
            dialog.setTitle("Adding To DB");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //check that all textfields contain text
            String date=dateInput.getText().toString();
            String title=titleInput.getText().toString();
            String text=textInput.getText().toString();

            if(date==null || date.length()<=0){
                error_message="Sorry, please make sure you input a value in date atleast";
            }else if(title==null || title.length()<=0){
                error_message="Sorry, please give a title of some sort before continuing";
            }else if(text==null || text.length()<=0){
                error_message="Sorry, please input something as diary before continuing";
            }

            Note note=Note.newNote(date,title,text);
            ApplicationDatabase.getInstance(NotesAdd.this).notesDao().insertNote(note);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.hide();
            dialog.cancel();

            if(error_message!=null){
                AlertDialog.Builder alert=new AlertDialog.Builder(NotesAdd.this);
                alert.setTitle("Error..");
                alert.setMessage(error_message);
                alert.setPositiveButton("Okay, let me retry",null);
                alert.setCancelable(false);
                alert.create().show();
            }else{
                AlertDialog.Builder alert=new AlertDialog.Builder(NotesAdd.this);
                alert.setTitle("Added");
                alert.setCancelable(false);
                alert.setMessage("Your input was added to diary");
                alert.setPositiveButton("Thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NotesAdd.this.finish();
                    }
                });
                alert.create().show();
            }
        }
    }
}
