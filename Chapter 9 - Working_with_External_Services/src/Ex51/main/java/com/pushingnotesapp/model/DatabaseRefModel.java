package com.pushingnotesapp.model;

import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseRefModel extends ViewModel {

    private static final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("notes");

    public static DatabaseReference getDatabaseReference() {
        return myRef;
    }
}
