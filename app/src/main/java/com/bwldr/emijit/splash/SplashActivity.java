package com.bwldr.emijit.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bwldr.emijit.signup.SignupActivity;

/**
 * Display "splash" screen, and redirect to the start of the app flow
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }
}
