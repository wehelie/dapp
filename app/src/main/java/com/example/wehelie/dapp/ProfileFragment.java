package com.example.wehelie.dapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


import android.net.Uri;

public class ProfileFragment extends Fragment {

    private String argName;
    private String argUsername;
    private String argAge;
    private String argEmail;
    private String argOccupation;
    private String argDescription;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView displayName = view.findViewById(R.id.displayname);
        TextView displayUsername = view.findViewById(R.id.displayusername);
        TextView displayAge = view.findViewById(R.id.displayage);
        TextView displayEmail = view.findViewById(R.id.displayemail);
        TextView displayOccupation = view.findViewById(R.id.displayjob);
        TextView displayDescription = view.findViewById(R.id.displaydescription);


        if(getArguments() != null) {
            argName = getArguments().getString(Constants.KEY_NAME);
            argUsername = getArguments().getString(Constants.KEY_USERNAME);
            argAge = getArguments().getString(Constants.KEY_AGE);
            argEmail = getArguments().getString(Constants.KEY_EMAIL);
            argOccupation = getArguments().getString(Constants.KEY_OCCUPATION);
            argDescription = getArguments().getString(Constants.KEY_DESCRIPTION);
        }


        displayName.setText(argName);
        displayUsername.setText(argUsername);
        displayAge.setText(argAge);
        displayEmail.setText(argEmail);
        displayOccupation.setText(argOccupation);
        displayDescription.setText(argDescription);

        Button goback = (Button) view.findViewById(R.id.goback);
        goback.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SecondActivity.class);
            startActivity(intent);
        });
        return view;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_profile, parent, false));
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 18;

        public ContentAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }



}
