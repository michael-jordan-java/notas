package br.senai.sp.android.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotesDao {
    private SQLiteDatabase database;
    private String[] colums = {CustomSQLiteOpenHelper.COLUMN_ID, CustomSQLiteOpenHelper.COLUMN_NOTES};
    private CustomSQLiteOpenHelper sqLiteOpenHelper;

    public NotesDao(Context context) {
        sqLiteOpenHelper = new CustomSQLiteOpenHelper(context);
    }

    public void open() throws SQLException {
        database = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteOpenHelper.close();
    }

    public Note create(String note) {
        ContentValues values = new ContentValues();
        values.put(CustomSQLiteOpenHelper.COLUMN_NOTES, note);
        long insertId = database.insert(CustomSQLiteOpenHelper.TABLE_NOTES, null, values);
        Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_NOTES, colums, CustomSQLiteOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Note newNote = new Note();
        newNote.setId(cursor.getLong(0));
        newNote.setNote(cursor.getString(1));
        cursor.close();
        return newNote;
    }

    public void delete(Note note) {
        long id = note.getId();
        database.delete(CustomSQLiteOpenHelper.TABLE_NOTES, CustomSQLiteOpenHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Note> getAll() {
        List<Note> notes = new ArrayList<Note>();

        Cursor cursor = database.query(CustomSQLiteOpenHelper.TABLE_NOTES, colums, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Note note = new Note();
            note.setId(cursor.getLong(0));
            note.setNote(cursor.getString(1));
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return notes;

    }

}