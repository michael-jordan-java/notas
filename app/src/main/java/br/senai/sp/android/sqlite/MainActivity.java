package br.senai.sp.android.sqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView txtMensagem;
    private NotesDao dao;
    private ListView listagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dao = new NotesDao(this);

        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtMensagem = (TextView) findViewById(R.id.empty);
        listagem = (ListView) findViewById(R.id.list);


        listagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Note anotacao = (Note) listagem.getItemAtPosition(posicao);
                querExcluir(anotacao);
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


        List<Note> notes = dao.getAll();

        if (notes.size() > 0) {
            txtMensagem.setVisibility(View.INVISIBLE);
        } else {
            txtMensagem.setVisibility(View.VISIBLE);
        }

        ArrayAdapter<Note> adapter = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1, notes);
        listagem.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        dao.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_note) {
            Intent intent = new Intent(this, AddNoteActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void querExcluir(final Note anotacao) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação");
        builder.setMessage("Deseja excluir?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(anotacao);
                dialog.dismiss();
                onResume();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
