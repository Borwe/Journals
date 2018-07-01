package db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import model.Note;

@Dao
public interface NotesDao {

    @Insert
    void insertNote(Note note);

    @Query(value = "select * from notes")
    List<Note> getAllNotes();

    @Query(value = "select * from notes where note_id = :id")
    Note getNoteFromID(int id);
}
