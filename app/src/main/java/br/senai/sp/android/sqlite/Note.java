package br.senai.sp.android.sqlite;

public class Note {

    private long id;
    private String note;

    @Override
    public String toString() {
        return note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}