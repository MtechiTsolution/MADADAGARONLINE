package com.example.madadagaronline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class ListJobs_Act extends AppCompatActivity {
    public static String catogory="Plumber";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_jobs);
        setFragment();
    }
    protected void setFragment() {
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.your_placeholder, new Jobslist());
        // or ft.add(R.id.your_placeholder, new ABCFragment());
        // Complete the changes added above
        ft.commit();
    }
}