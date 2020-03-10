package com.o.notetaker.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.o.notetaker.Model.Note;
import com.o.notetaker.R;
import com.o.notetaker.ViewModel.AddEditNoteViewModel;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "EXTRA_ID";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "EXTRA_PRIORITY";
    public static final String EXTRA_REQUEST = "EXTRA_REQUEST";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    private AddEditNoteViewModel viewModel;

    private int REQUEST_CODE;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        REQUEST_CODE = intent.getIntExtra(EXTRA_REQUEST, -1);
        if (REQUEST_CODE == MainActivity.ADD_NOTE_REQUEST)
            setTitle("Add Note");
        else if (REQUEST_CODE == MainActivity.EDIT_NOTE_REQUEST) {
            setTitle("Edit Note");
            noteId = intent.getIntExtra(EXTRA_ID, -1);
            editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }

        ViewModelProvider.AndroidViewModelFactory viewModelFactory = new ViewModelProvider.
                AndroidViewModelFactory(getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(AddEditNoteViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                if (REQUEST_CODE == MainActivity.ADD_NOTE_REQUEST)
                    saveNote();
                else if (REQUEST_CODE == MainActivity.EDIT_NOTE_REQUEST)
                    updateNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note(title, description, priority);

        viewModel.insert(note);

        Toast.makeText(this, "Note Saved!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void updateNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note(title, description, priority);
        note.setId(noteId);

        if (noteId != -1) {
            viewModel.update(note);
            Toast.makeText(this, "Note Updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

}
