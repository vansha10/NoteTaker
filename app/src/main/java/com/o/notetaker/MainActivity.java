package com.o.notetaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewModelProvider.AndroidViewModelFactory viewModelFactory = new ViewModelProvider.
                AndroidViewModelFactory(getApplication());
        noteViewModel = new ViewModelProvider(this, viewModelFactory).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update recycler view
                Toast.makeText(MainActivity.this, notes.get(0).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
