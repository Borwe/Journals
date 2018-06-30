package model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int note_id;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "topic")
    private String topic;

    @ColumnInfo(name = "text")
    private String text;

    private Note(String date,String topic,String text){
        this.date=date;
        this.topic=topic;
        this.text=text;
    }

    public Note(int note_id,String date,String topic,String text){
        this.note_id=note_id;
        this.date=date;
        this.topic=topic;
        this.text=text;
    }

    public static Note newNote(String date,String topic,String text){
        return new Note(date,topic,text);
    }

    public int getNote_id() {
        return note_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Note{" +
                "note_id=" + note_id +
                ", date='" + date + '\'' +
                ", topic='" + topic + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
