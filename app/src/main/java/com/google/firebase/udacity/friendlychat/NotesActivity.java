package com.google.firebase.udacity.friendlychat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NotesActivity extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();
    ListView listView;
    static ArrayAdapter arrayAdapter;
    static Set<String> set;
    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        findViewById(R.id.pencil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.pencil);
                // fab.setOnClickListener(new View.OnClickListener() {
                //  @Override
                //  public void onClick(View view) {
                notes.add("");
                if (set == null) {
                    set = new HashSet<>();
                } else {
                    set.clear();
                }
                set.addAll(notes);//adding the example notes to set
                NotesActivity.sharedPreferences.edit().remove("notes").apply();
                sharedPreferences.edit().putStringSet("notes", NotesActivity.set).apply();//adding everything to the memory

                Intent i = new Intent(getApplicationContext(), Edit_note.class);
                i.putExtra("noteid", notes.size() - 1);
                startActivity(i);
            }
        });
        listView = (ListView) findViewById(R.id.listview);
        sharedPreferences = this.getSharedPreferences("com.google.firebase.udacity.friendlychat", Context.MODE_PRIVATE);

        set = sharedPreferences.getStringSet("notes", null);

        notes.clear();
        if (set != null) {

            notes.addAll(set);//adds all the elements in the notes tghat is fetched by sharedpreferences
        } else {

            set = new HashSet<String>();
            set.addAll(notes);//adding the example notes to set
            NotesActivity.sharedPreferences.edit().remove("notes").apply();
            sharedPreferences.edit().putStringSet("notes", set).apply();//adding everything to the memory
            arrayAdapter.notifyDataSetChanged();
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), Edit_note.class);
                i.putExtra("noteid", position);
                startActivity(i);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                new AlertDialog.Builder(new ContextThemeWrapper(NotesActivity.this,R.style.AlertDialogCustom))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.android.notes", Context.MODE_PRIVATE);
                                if (set == null) {
                                    set = new HashSet<>();
                                } else {
                                    set.clear();
                                }
                                set.addAll(notes);//adding the example notes to set
                                NotesActivity.sharedPreferences.edit().remove("notes").apply();
                                sharedPreferences.edit().putStringSet("notes", NotesActivity.set).apply();//adding everything to the memory
                                arrayAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("no", null)
                        .show();
                return true;
            }
        });
    }
}





