package com.example.passafe;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static boolean isLoggedIn;

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve saved login state
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        String passname = prefs.getString("username", "");

        // Auto login if already logged in
        if (isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, dashboard.class);
            intent.putExtra("username", passname);
            startActivity(intent);
        }

        // UI components grouped for clarity
        TextView uname = findViewById(R.id.username_area);
        TextView pin1 = findViewById(R.id.pin_area);
        MaterialCardView ucard = findViewById(R.id.uname_card);
        MaterialCardView pcard = findViewById(R.id.pin_card);
        Button submit_button = findViewById(R.id.submit_button);
        TextView gotosignup = findViewById(R.id.go_to_signup);
        gotosignup.setEnabled(true); // ensure enabled

        DatabaseHelper dbhelper = new DatabaseHelper(this);

        // Submit button logic for login validation
        submit_button.setOnClickListener(view -> {
            boolean isvaliduser = false;
            String username = uname.getText().toString().trim();

            // Check for valid username in DB
            ArrayList<UserManager> usernames = dbhelper.getUsernames();
            for (UserManager user : usernames) {
                if (username.equals(user.UserName)) {
                    isvaliduser = true;
                    break;
                }
            }

            if (isvaliduser) {
                Toast.makeText(MainActivity.this, "Successful!", Toast.LENGTH_SHORT).show();

                boolean isvalidpass = false;
                String userpassword = pin1.getText().toString();

                ArrayList<UserManager> passwords = dbhelper.getPasswords();
                for (UserManager pass : passwords) {
                    if (userpassword.equals(pass.PassWord)) {
                        isvalidpass = true;
                        break;
                    }
                }

                if (isvalidpass) {
                    Toast.makeText(MainActivity.this, "Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    intent.putExtra("username", username);
                    intent.putExtra("pin", userpassword);
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                    SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("username", username);
                    editor.putString("pin", userpassword);
                    editor.apply();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                    pcard.setStrokeColor(Color.RED);
                    pin1.setText("");
                }
            } else {
                ucard.setStrokeColor(Color.RED);
                Toast.makeText(getApplicationContext(), "User Not Found!", Toast.LENGTH_SHORT).show();
                uname.setText("");
            }
        });

        // Signup text click event
        gotosignup.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, signup.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Going to sign up", Toast.LENGTH_SHORT).show();
        });
    }
}
