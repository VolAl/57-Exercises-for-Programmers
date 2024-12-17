package com.todolistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.todo_list.databinding.FragmentCompleteTaskBinding;
import com.todolistapp.model.DatabaseRefModel;

import java.util.HashMap;
import java.util.Map;

public class FragmentCompleteTask extends Fragment {

    private DatabaseReference myRef;
    private FragmentCompleteTaskBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        myRef = DatabaseRefModel.getDatabaseReference();
        binding = FragmentCompleteTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button saveBtn = binding.saveButton;

        saveBtn.setOnClickListener(
                v -> {
                    String taskToSave = binding.taskToSaveValue.getText().toString();
                    writeToFirebaseDB(myRef, taskToSave);
                }

        );
    }

    private void writeToFirebaseDB(DatabaseReference myRef, String value) {
        DatabaseReference tasksRef = myRef.child("tasks");

        Map<String, Object> tasksUpdates = new HashMap<>();
        tasksUpdates.put("taskId", value);
        tasksUpdates.put("completed", true);

        tasksRef.updateChildren(tasksUpdates);

        getParentFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}