package br.senai.sp.android.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

public class AddNoteActivity extends Activity {

    private NotesDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        dao = new NotesDao(this);
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Button saveButton = (Button) findViewById(R.id.save_note_button);
        final EditText noteText = (EditText) findViewById(R.id.note_text);

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String note = noteText.getEditableText().toString();

                if(note.trim().length() > 0) {
                    dao.create(note);
                    finish();
                }else{
                    Toast.makeText(AddNoteActivity.this,"Campo Vazio", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }
}