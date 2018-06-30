package db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import model.Note;

@Dao
public interface NotesDao {

    @Insert
    void insertNote(Note note);
}
