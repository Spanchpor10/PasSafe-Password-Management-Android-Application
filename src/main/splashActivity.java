package com.example.passafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.concurrent.Executor;

public class splashActivity extends AppCompatActivity {
    ImageView imageView;
    private Handler mHandler = new Handler();
    private boolean validUser = false;
    private Boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
//        imageView = findViewById(R.id.imageview);
        Button unlock = findViewById(R.id.button);
        CardView hideCard = findViewById(R.id.hideCard);
        hideCard.setVisibility(View.VISIBLE);
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        String passname = prefs.getString("username", "");
        if (!isLoggedIn) {
            unlock.setVisibility(View.INVISIBLE);
            hideCard.setVisibility(View.INVISIBLE);
        }



        // Adding the gif here using glide library
//        Glide.with(this).load(R.raw.splashgif).into(imageView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent = new Intent(splashActivity.this, MainActivity.class);
        // creating a variable for our BiometricManager
        // and lets check if our user can use biometric sensor or not
        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            // this means we can use biometric sensor
            case BiometricManager.BIOMETRIC_SUCCESS:
//                msgtex.setText("You can use the fingerprint sensor to login");
//                msgtex.setTextColor(Color.parseColor("#fafafa"));
                break;

            // this means that the device doesn't have fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
//                msgtex.setText("This device doesnot have a fingerprint sensor");
//                loginbutton.setVisibility(View.GONE);
                break;

            // this means that biometric sensor is not available
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
//                msgtex.setText("The biometric sensor is currently unavailable");
//                loginbutton.setVisibility(View.GONE);
                break;

            // this means that the device doesn't contain your fingerprint
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
//                msgtex.setText("Your device doesn't have fingerprint saved,please check your security settings");
//                loginbutton.setVisibility(View.GONE);
                break;
        }
        // creating a variable for our Executor
        Executor executor = ContextCompat.getMainExecutor(this);
        // this will give us result of AUTHENTICATION
        final BiometricPrompt biometricPrompt;
        biometricPrompt = new BiometricPrompt(splashActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            // THIS METHOD IS CALLED WHEN AUTHENTICATION IS SUCCESS
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                validUser = true;
                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();


            }
        });


        // creating a variable for our promptInfo
        // BIOMETRIC DIALOG

        if (isLoggedIn) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("UNLOCK TO CONTINUE")
                            .setDescription("Use your fingerprint to login ").setDeviceCredentialAllowed(true).build();
                    biometricPrompt.authenticate(promptInfo);


                }
            }, 2000);
        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                }
            }, 2000);
        }



        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("UNLOCK TO CONTINUE")
                        .setDescription("Use your fingerprint to login ").setDeviceCredentialAllowed(true).build();
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }

}