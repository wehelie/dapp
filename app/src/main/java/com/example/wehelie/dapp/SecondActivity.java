package com.example.wehelie.dapp;

import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = Constants.KEY_MAINACTIVITY;



    private EditText editName;
    private EditText editAge;
    private EditText editEmail;
    private EditText editUsername;
    private EditText editOccupation;
    private EditText editDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        editName = (EditText) findViewById(R.id.editName);
        editAge = (EditText) findViewById(R.id.editDob);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editEmail= (EditText) findViewById(R.id.editEmail);
        editOccupation = (EditText) findViewById(R.id.editJob);
        editDescription = (EditText) findViewById(R.id.editBio);

        Button submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast warn =  Toast.makeText(SecondActivity.this, "CORRECT ALL ERRORS BEFORE SUBMITTING FORM AGAIN", Toast.LENGTH_SHORT);

                String name = editName.getText().toString();
                String username = editUsername.getText().toString();
                String dob = editAge.getText().toString();
                String email = editEmail.getText().toString();
                String job = editOccupation.getText().toString();
                String bio = editDescription.getText().toString();


                int age = getAge(dob);

                // start the SecondActivity
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.putExtra(Constants.KEY_NAME, name);
                intent.putExtra(Constants.KEY_EMAIL, email);
                intent.putExtra(Constants.KEY_USERNAME, username);

                intent.putExtra(Constants.KEY_OCCUPATION, job);
                intent.putExtra(Constants.KEY_DESCRIPTION, bio);

                if (name == null || name.equals("")) {
                    editName.setError("Name field cannot be empty!!");
                    warn.show();
                } else if (age < 18 || dob == "") {
                    editAge.setError("must be 18 or up!");
                } else {

                    editAge.setText(Integer.toString(age));
                    intent.putExtra("age", Integer.toString(age));
                    startActivity(intent);
                }
            }
        });
    }



    private int getAge(String dobString) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month + 1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }


        return age;
    }

}