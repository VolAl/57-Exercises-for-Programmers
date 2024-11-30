package com.pushingnotesapp;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.pushingnotesapp.databinding.FragmentMainBinding;
import com.pushingnotesapp.model.DatabaseRefModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

        binding.pushBtn.setOnClickListener(v ->
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_FragmentMain_to_FragmentPushNote)
        );

        readFromFirebaseDB(myRef);
    }

    private void readFromFirebaseDB(DatabaseReference myRef) {

        myRef.child("notes").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                HashMap<String, Object> noteValues = (HashMap<String,Object>) snapshot.getValue();
                assert noteValues != null;
                String note = Objects.requireNonNull(noteValues.get("note")).toString();
                String timestamp = Objects.requireNonNull(noteValues.get("timestamp")).toString();
                String value = timestamp + " - " + note;
                if(!snapshotValues.contains(value)) {
                    snapshotValues.add(value);
                    StringBuilder snapshotValue = new StringBuilder();
                    List<String> sortedList = new ArrayList<>(snapshotValues);
                    Collections.sort(sortedList);
                    for (String s : sortedList) {
                        snapshotValue.append(s).append(System.lineSeparator());
                    }
                    binding.outputTextView.setText(snapshotValue.toString());

                    Log.d(TAG, "Value is: " + snapshotValue);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}