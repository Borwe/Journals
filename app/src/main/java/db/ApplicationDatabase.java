package db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import model.Note;

@Database(version = 1 , entities = {Note.class}, exportSchema = false)
public abstract class ApplicationDatabase extends RoomDatabase{

    private static ApplicationDatabase INSTANCE;

    public abstract NotesDao notesDao();


    public static synchronized ApplicationDatabase getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context,ApplicationDatabase.class,"application.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
