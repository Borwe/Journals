package com.borwe.journals;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import model.Note;

public class NotesUpdate extends AppCompatActivity {

    EditText n_topic,n_text;
    Button update_button;
    TextView n_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        n_date=findViewById(R.id.input_date);
        n_topic=findViewById(R.id.input_title);
        n_text=findViewById(R.id.input_text);

        update_button=findViewById(R.id.update_notes_button);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateNote().execute();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NotesUpdate.this.finish();
        return  true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent data=getIntent();
        n_date.setText(data.getStringExtra("note_time"));
        n_topic.setText(data.getStringExtra("note_title"));
        n_text.setText(data.getStringExtra("note_text"));
    }

    class UpdateNote extends AsyncTask<Void,Void,Void>{
        ProgressDialog progressDialog;
        String error_message=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(NotesUpdate.this);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String title=n_topic.getText().toString();
            String text=n_text.getText().toString();

            if(title==null || title.length()<=0){
                error_message="Please input something under title";
            }else if(text==null || text.length()<=0){
                error_message="Please make sure you enter something in journal";
            }

            Intent data=NotesUpdate.this.getIntent();
            Note.updateNote(NotesUpdate.this,data.getIntExtra("note_id",-1),text,title);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.hide();
            progressDialog.cancel();

            if(error_message==null){
                AlertDialog.Builder alert=new AlertDialog.Builder(NotesUpdate.this);
                alert.setTitle("Updated");
                alert.setMessage("Your note was updated");
                alert.setPositiveButton("Okay, Go back to view Journal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NotesUpdate.this.finish();
                    }
                });
                alert.setCancelable(false);
                alert.create().show();
            }else{
                AlertDialog.Builder alert=new AlertDialog.Builder(NotesUpdate.this);
                alert.setTitle("Erorr");
                alert.setMessage(error_message);
                alert.setCancelable(false);
                alert.setPositiveButton("Retry",null);
                alert.create().show();
            }
        }
    }
}
