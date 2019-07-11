package com.said.alc4phase1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


/**
 * @author Said Mohamed
 */
public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Display the Up button to return back to main activity
    }
}
