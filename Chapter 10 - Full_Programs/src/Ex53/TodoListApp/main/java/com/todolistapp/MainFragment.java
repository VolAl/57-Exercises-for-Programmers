package com.todolistapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.todo_list.R;
import com.todo_list.databinding.FragmentMainBinding;
import com.todolistapp.model.DatabaseRefModel;
import com.todolistapp.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private DatabaseReference myRef;

    private final Set<String> snapshotValues = new HashSet<>();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        myRef = DatabaseRefModel.getDatabaseReference();

        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addTaskBtn.setOnClickListener(v ->
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_FragmentMain_to_FragmentAddTask)
        );

        binding.completeTaskBtn.setOnClickListener(v ->
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_FragmentMain_to_FragmentCompleteTask)
        );

        readFromFirebaseDB(myRef);
    }

    private void readFromFirebaseDB(DatabaseReference myRef) {

        myRef.child("tasks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    if(taskSnapshot.getChildrenCount() > 2) {
                        Task task = taskSnapshot.getValue(Task.class);
                        if (task != null) {
                            snapshotValues.add(task.toString());
                        }
                    } else {
                        if(Objects.equals(taskSnapshot.getKey(), "taskId")) {
                            String taskId = Objects.requireNonNull(taskSnapshot.getValue()).toString();
                            List<String> task = snapshotValues.stream().filter(s -> s.contains(taskId)).collect(Collectors.toList());
                            if(!task.isEmpty()) {
                                String taskToUpdate = task.get(0);
                                snapshotValues.remove(taskToUpdate);
                                snapshotValues.add(taskToUpdate.replace("[ ] ", "[X] "));
                            }
                        }
                    }
                }

                StringBuilder snapshotValue = new StringBuilder();
                List<String> sortedList = new ArrayList<>(snapshotValues);
                Collections.sort(sortedList);
                for (String s : sortedList) {
                    snapshotValue.append(s).append(System.lineSeparator());
                }
                binding.outputTextView.setText(snapshotValue.toString());

                Log.d(TAG, "Value is: " + snapshotValue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}