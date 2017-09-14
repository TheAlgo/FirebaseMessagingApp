package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

import static com.google.firebase.udacity.friendlychat.NotesActivity.notes;

public class Edit_note extends AppCompatActivity implements TextWatcher {
    int noteid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        EditText editText=(EditText)findViewById(R.id.editText);
        Intent i=getIntent();
        noteid=i.getIntExtra("noteid",-1);

        if(noteid!=-1)
        {
            editText.setText(notes.get(noteid));
        }
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        notes.set(noteid,String.valueOf(s));
        NotesActivity.arrayAdapter.notifyDataSetChanged();
        if(NotesActivity.set==null)
        {
            NotesActivity.set=new HashSet<String>();
        }

        else {
            NotesActivity.set.clear();
        }
        NotesActivity.set.addAll(notes);//adding the example notes to set
        NotesActivity.sharedPreferences.edit().remove("notes").apply();//so that after deleting the notes vanishes and does not persisits in the memeory
        NotesActivity.sharedPreferences.edit().putStringSet("notes",NotesActivity.set).apply();//adding everything to the memory
        NotesActivity.arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    }

