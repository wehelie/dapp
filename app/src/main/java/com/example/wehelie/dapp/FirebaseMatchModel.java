package com.example.wehelie.dapp;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.function.Consumer;

public class FirebaseMatchModel {
    private DatabaseReference myDatabase;
    private HashMap<DatabaseReference, ValueEventListener> listeners;

    public FirebaseMatchModel() {
        myDatabase = FirebaseDatabase.getInstance().getReference();
        listeners = new HashMap<>();
    }


    public void findMatches(Consumer<DataSnapshot> dataChangedCallback, Consumer<DatabaseError> dataErrorCallback) {
        DatabaseReference matchesdRef = myDatabase.child("matches");
        ValueEventListener listenHandler = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataChangedCallback.accept(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dataErrorCallback.accept(databaseError);
            }
        };

        matchesdRef.addValueEventListener(listenHandler);
        listeners.put(matchesdRef, listenHandler);
    }

    public void updateMatchId(MatchesObject matches) {
        DatabaseReference matchesRef = myDatabase.child("matches");
        matchesRef.child(matches.uid).setValue(matches);
    }

    public void clear() {
        listeners.forEach(Query::removeEventListener);
    }
}
