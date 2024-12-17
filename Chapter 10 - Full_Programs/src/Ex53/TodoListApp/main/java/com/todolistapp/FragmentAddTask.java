package com.todolistapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.todo_list.databinding.FragmentAddTaskBinding;
import com.todolistapp.model.DatabaseRefModel;
import com.todolistapp.model.Task;

import java.util.HashMap;
import java.util.Map;

public class FragmentAddTask extends Fragment {

    private DatabaseReference myRef;
    private FragmentAddTaskBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        myRef = DatabaseRefModel.getDatabaseReference();
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
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

        Map<String, Task> tasks = new HashMap<>();
        Task task = new Task();
        task.setTaskName(value);
        task.setCompleted(false);
        tasks.put("task" + task.getTaskId(), task);

        tasksRef.setValue(tasks);

        getParentFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

