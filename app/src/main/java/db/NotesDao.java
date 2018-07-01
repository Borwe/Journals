package db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
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

    @Query(value = "delete from notes where note_id = :id")
    void deleteNoteByID(int id);

    @Query(value = "update notes set topic = :topic where note_id = :id")
    void updateNodeByTopic(String topic,int id);

    @Query(value = "update notes set text = :text where note_id=:id")
    void updateNodeByText(String text,int id);
}
