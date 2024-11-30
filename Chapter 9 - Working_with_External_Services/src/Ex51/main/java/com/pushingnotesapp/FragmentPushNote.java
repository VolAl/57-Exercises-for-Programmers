package com.pushingnotesapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.pushingnotesapp.databinding.FragmentPushNoteBinding;
import com.pushingnotesapp.model.DatabaseRefModel;

import java.util.HashMap;
import java.util.Map;

public class FragmentPushNote extends Fragment {

    private DatabaseReference myRef;
    private FragmentPushNoteBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        myRef = DatabaseRefModel.getDatabaseReference();
        binding = FragmentPushNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button saveBtn = binding.saveButton;

        saveBtn.setOnClickListener(
                v -> {
                    String noteToSave = binding.noteToSaveValue.getText().toString();
                    writeToFirebaseDB(myRef, noteToSave);
                }

        );
    }

    private void writeToFirebaseDB(DatabaseReference myRef, String value) {
        String key = myRef.child("notes").push().getKey();
        Map<String, Object> noteValues = new HashMap<>();
        noteValues.put("note", value);
        noteValues.put("timestamp", System.currentTimeMillis());

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/notes/" + key, noteValues);

        myRef.updateChildren(childUpdates);
        getParentFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}