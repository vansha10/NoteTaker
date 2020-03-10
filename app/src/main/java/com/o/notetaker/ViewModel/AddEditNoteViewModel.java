package com.o.notetaker.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.o.notetaker.Model.Note;
import com.o.notetaker.Repository.NoteRepository;

public class AddEditNoteViewModel extends AndroidViewModel {
    NoteRepository noteRepository;
    public AddEditNoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
    }

    public void insert(Note note) {
        noteRepository.insert(note);
    }

    public void update(Note note) {
        noteRepository.update(note);
    }
}
