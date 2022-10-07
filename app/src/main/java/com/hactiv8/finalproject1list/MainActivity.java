package com.hactiv8.finalproject1list;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.hactiv8.finalproject1list.Adapter.Adapter;
//import com.hactiv8.finalproject1list.Model.Model;
//import com.hactiv8.finalproject1list.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity  {

    DbHelper dbHelper;
    ArrayAdapter<String> mAdapter;
    ListView lstTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        lstTask = (ListView) findViewById(R.id.lstTask);
        loadTaskList();
    }

    private void loadTaskList() {
        ArrayList<String> taskList = dbHelper.getTaskList();
        if (mAdapter==null){
            mAdapter = new ArrayAdapter<String>(this, R.layout.task_layout, R.id.TextIsi, taskList);
            lstTask.setAdapter(mAdapter);
//            lstTask.setAdapter(mAdapter);
        }else{ mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();

        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add: final EditText taskEditText = new EditText(this);
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Tambah Catatan")
                    .setMessage("Apa yang ingin kamu catat ?")
                    .setView(taskEditText)
                    .setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int witch) {
                                String task = String.valueOf(taskEditText.getText());
                                dbHelper.insertNewTask(task);
                                loadTaskList();
                            }
                        })
                    .setNegativeButton("Batal", null)
                    .create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void deleteTask(View view)
    {
        View parent = (View)view.getParent();
        TextView textView = (TextView) parent.findViewById(R.id.TextIsi);
        Log.e("String", (String) textView.getText());
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Tugas Selesai")
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = String.valueOf(textView.getText());
                        dbHelper.deleteTask(task);
                        loadTaskList();
                    }
                })
                .setNegativeButton("Batal", null)
                .create();
        dialog.show();
    }
}

