package com.borwe.journals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import model.Note;

public class NotesShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_show);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent data=getIntent();
        Toast.makeText(getApplicationContext(),data.getStringExtra("note_title"),Toast.LENGTH_SHORT).show();
    }
}
