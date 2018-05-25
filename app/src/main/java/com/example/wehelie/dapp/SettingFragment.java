package com.example.wehelie.dapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;

import android.widget.Toast;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class SettingFragment extends Fragment {

    public static EditText userEmail;

    public static EditText max;
    public static EditText min;
    public static EditText reminder;
    public static EditText minage;
    public static EditText maxage;
    public static EditText gender;
    public static EditText privacy;


    private AppDatabase dB = null;
    private Button settingsButton;

    // validate the inputs
    private boolean notEmpty(EditText text) {
        boolean notempty = false;
        if (text.getText().toString().trim().length() > 0) {
            notempty = true;
        }
        return notempty;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dB = AppDatabase.getDB(this.getActivity().getApplicationContext());

        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        settingsButton = (Button) view.findViewById(R.id.settingsButton);
        userEmail = view.findViewById(R.id.userEmail);
        min = view.findViewById(R.id.min);
        max = view.findViewById(R.id.max);
        gender = view.findViewById(R.id.gender);
        privacy = view.findViewById(R.id.privacy);
        minage = view.findViewById(R.id.minage);
        maxage = view.findViewById(R.id.maxage);
        reminder = view.findViewById(R.id.reminder);


        settingsButton.setOnClickListener(v -> {

            UserSetting userSetting = new UserSetting();
            userSetting.setEmail(userEmail.getText().toString());
            userSetting.setPrivacy(privacy.getText().toString());
            userSetting.setGender(gender.getText().toString());
            userSetting.setMaxDistance(max.getText().toString());
            userSetting.setMinDistance(min.getText().toString());
            userSetting.setReminder(reminder.getText().toString());
            userSetting.setMaxAge(minage.getText().toString());
            userSetting.setMinAge(maxage.getText().toString());



            if (notEmpty(userEmail) && notEmpty(gender) && notEmpty(privacy) && notEmpty(max) && notEmpty(min)) {
                insertDatabase(getView(), userSetting);
                Toasty.warning(getContext(), "Your profile has been saved!", Toast.LENGTH_LONG).show();
            } else {
                Toasty.warning(getContext(), "a value is missing!", Toast.LENGTH_LONG).show();
            }

        });


        String[] userEmailAddress = new String[3];


        userEmailAddress[0] = userEmail.getText().toString();
        List<UserSetting> addEmails = new ArrayList<UserSetting>();
        addEmails = loadAllByIds(dB, userEmailAddress);


        new GetSettingTask(getActivity()).execute();

        return view;
    }


    public void insertDatabase(View view, UserSetting userSetting) {
        new InsertUserSettingTask(this.getActivity(), userSetting).execute();
    }

    private static class InsertUserSettingTask extends AsyncTask<Void, Void, UserSetting> {

        private WeakReference<Activity> activityWeakReference;
        private UserSetting userSetting;

        public InsertUserSettingTask(Activity activity, UserSetting userSetting) {
            activityWeakReference = new WeakReference<>(activity);
            this.userSetting = userSetting;
        }

        @Override
        protected UserSetting doInBackground(Void... voids) {
            Activity activity = activityWeakReference.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getDB(activity.getApplicationContext());
            db.settingsDao().insertAll(userSetting);
            return userSetting;
        }
    }

    private static class GetSettingTask extends AsyncTask<Void, Void, UserSetting> {

        private WeakReference<Activity> activityWeakReference;

        public GetSettingTask(Activity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected UserSetting doInBackground(Void... voids) {
            Activity activity = activityWeakReference.get();
            if (activity == null) {
                return null;
            }

            AppDatabase db = AppDatabase.getDB(activity.getApplicationContext());
            List<UserSetting> users = db.settingsDao().getAll();
            if (users.size() <= 0 || users.get(0) == null) {
                return null;
            }
            return users.get(0);
        }
        @Override
        protected void onPostExecute(UserSetting setting) {
            MainActivity activity = (MainActivity) activityWeakReference.get();
            if(setting == null || activity == null) {
                return;
            }

            userEmail.setText(setting.getEmail());
            reminder.setText(setting.getReminder());
            min.setText(setting.getMinDistance());
            minage.setText(setting.getMinAge());
            maxage.setText(setting.getMaxAge());
            max.setText(setting.getMaxDistance());
            gender.setText(setting.getGender());
            privacy.setText(setting.getPrivacy());

        }
    }

    private static List<UserSetting> loadAllByIds(final AppDatabase db, String[] emails) {
        List<UserSetting> settings = db.settingsDao().loadAllByIds(emails);
        return settings;
    }

}