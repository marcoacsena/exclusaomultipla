package com.example.exclusaomultipla.activity;

import android.os.Bundle;

import com.example.exclusaomultipla.R;
import com.example.exclusaomultipla.Tools;
import com.example.exclusaomultipla.adapter.TarefaAdapter;
import com.example.exclusaomultipla.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvListaDeTarefas;
    private TarefaAdapter tarefaAdapter;
    private List<Tarefa> listaDeTarefas = new ArrayList<>();
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initComponent();

    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tarefas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    public void adicionarTarefas(){
        //Listar tarefas

        listaDeTarefas.add(new Tarefa(1, R.drawable.java,"Estudar JAVA", R.color.red_500));
        listaDeTarefas.add(new Tarefa(2, R.drawable.flutter, "Estudar Flutter", R.color.red_500));
        listaDeTarefas.add(new Tarefa(3, R.drawable.react, "Estudar React", R.color.red_500));
        listaDeTarefas.add(new Tarefa(4, R.drawable.react_native, "Estudar Reat Native", R.color.red_500));
        listaDeTarefas.add(new Tarefa(5, R.drawable.mvc, "Estudar MVC", R.color.red_500));
        listaDeTarefas.add(new Tarefa(6, R.drawable.agil, "Estudar Ágil", R.color.red_500));

        tarefaAdapter.notifyDataSetChanged();
    }

    private void initComponent() {

        FloatingActionButton fab = findViewById(R.id.fab);

        //Configurar recyclerview
        rvListaDeTarefas = findViewById(R.id.rvListaDeTarefas);
        rvListaDeTarefas.setLayoutManager(new LinearLayoutManager(this));
        rvListaDeTarefas.setHasFixedSize(true);

        //set data and list adapter
        tarefaAdapter = new TarefaAdapter(getApplicationContext(), listaDeTarefas);
        rvListaDeTarefas.setAdapter(tarefaAdapter);

        tarefaAdapter.setOnClickListener(new TarefaAdapter.OnClickListener() {
            @Override
            public void onItemClick(View view, Tarefa obj, int pos) {
                if (tarefaAdapter.getSelectedItemCount() > 0) {
                    enableActionMode(pos);
                } else {
                    // read the inbox which removes bold from the row
                    Tarefa tarefa = tarefaAdapter.getItem(pos);
                    Toast.makeText(getApplicationContext(), "Read: " + tarefa.getNomeTarefa(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(View view, Tarefa obj, int pos) {
                enableActionMode(pos);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarTarefas();
            }
        });

        tarefaAdapter.clearSelections();
        adicionarTarefas();
        actionModeCallback = new ActionModeCallback();

    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode((ActionMode.Callback) actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {

        tarefaAdapter.toggleSelection(position);
        int count = tarefaAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }

    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            Tools.setSystemBarColor(MainActivity.this, R.color.colorDarkBlue2);
            mode.getMenuInflater().inflate(R.menu.delete, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_delete) {
                deleteInboxes();
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            tarefaAdapter.clearSelections();
            actionMode = null;
            Tools.setSystemBarColor(MainActivity.this, R.color.colorPrimary);
        }
    }

    private void deleteInboxes() {
        List<Integer> selectedItemPositions = tarefaAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            tarefaAdapter.removeData(selectedItemPositions.get(i));
        }
        tarefaAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
