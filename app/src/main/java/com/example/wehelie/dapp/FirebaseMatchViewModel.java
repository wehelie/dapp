package com.example.wehelie.dapp;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.function.Consumer;

public class FirebaseMatchViewModel {
    private FirebaseMatchModel model;

    public FirebaseMatchViewModel() {
        model = new FirebaseMatchModel();
    }

    public void findMatches(Consumer<ArrayList<MatchesObject>> resultCallback) {
        model.findMatches(
                (DataSnapshot dataSnapshot) -> {
                    ArrayList<MatchesObject> matches = new ArrayList<>();
                    for (DataSnapshot matchesSnapshot: dataSnapshot.getChildren()) {
                        MatchesObject itemMatches = matchesSnapshot.getValue(MatchesObject.class);
                        assert itemMatches != null;
                        itemMatches.uid = matchesSnapshot.getKey();
                        matches.add(itemMatches);
                    }
                    resultCallback.accept(matches);
                },
                (databaseError -> System.out.println("Error reading matches items: " + databaseError))
        );
    }

//    public void fetchMathesInformation(String key) {
//
//    }

    public void updateMatchId(MatchesObject matches) {
        model.updateMatchId(matches);
    }

    public void clear() {
        model.clear();
    }
}
