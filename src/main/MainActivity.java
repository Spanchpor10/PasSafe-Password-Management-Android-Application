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
//import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firestore.v1.Target;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
public static boolean isLoggedIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String nameofuser=getIntent().getStringExtra("name");
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
         isLoggedIn = prefs.getBoolean("isLoggedIn",false);
         String passname=prefs.getString("username","");

        if(isLoggedIn==true){
            Intent intent=new Intent(MainActivity.this,dashboard.class);
            intent.putExtra("username",passname);
            startActivity(intent);
        }


        TextView uname = (TextView) findViewById(R.id.username_area);
        TextView pin1 = (TextView) findViewById(R.id.pin_area);
        MaterialCardView ucard = (MaterialCardView) findViewById(R.id.uname_card);
        MaterialCardView pcard = (MaterialCardView) findViewById(R.id.pin_card);
        Button submit_button = (Button) findViewById(R.id.submit_button);
//        Button gotosignup = (Button) findViewById(R.id.go_to_signup_button);
        TextView gotosignup=(TextView) findViewById(R.id.go_to_signup);

        DatabaseHelper dbhelper=new DatabaseHelper(this);


//        dbhelper.addUser("sarthak","2115");
//        dbhelper.addUser("disha","disha1");
//        dbhelper.addUser("madhur","madhur1");


        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isvaliduser=false;
                String username=uname.getText().toString();
                String gotfromdb;
                ArrayList<UserManager> usernames=dbhelper.getUsernames();
                for (int i=0;i<usernames.size();i++){
                    gotfromdb=usernames.get(i).UserName;
                    if (gotfromdb.equals(username)){
//                        Toast.makeText(MainActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();
                        isvaliduser=true;
                    }
                    else{
//                        Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
                        continue;
                    }
                }
                if (isvaliduser==true){
                    Toast.makeText(MainActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();

                            Boolean isvalidpass=false;
                            String userpassword = pin1.getText().toString();
                            String gotpassfromdb;
                            ArrayList<UserManager> passwords = dbhelper.getPasswords();
                            for (int i = 0; i < passwords.size(); i++) {
                                gotpassfromdb = passwords.get(i).PassWord;
                                if (gotpassfromdb.equals(userpassword)) {
//                                    Toast.makeText(MainActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();
                                    isvalidpass=true;
                                } else {
//                                    Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                                    continue;
                                }
                            }
                            if(isvalidpass){

                                Toast.makeText(MainActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, dashboard.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                intent.putExtra("username", username);
                                intent.putExtra("pin", userpassword);
                                intent.putExtra("nameofuser",nameofuser);
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                startActivity(intent);
//                                isLoggedIn=true;
                                SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                                editor.putBoolean("isLoggedIn", true);
                                editor.putString("username",username);
                                editor.putString("pin",userpassword);
                                editor.apply();



                            }
                            else {

                                Toast.makeText(MainActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                                pcard.setStrokeColor(Color.RED);
//                        Toast.makeText(getApplicationContext(),"Wrong pin,please retry!",Toast.LENGTH_LONG).show();
                                pin1.setText("");
                            }
                        }
//                    });
//                }
                else{
//                    Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
                    isvaliduser=false;
                    ucard.setStrokeColor(Color.RED);
                    Toast.makeText(getApplicationContext(), "User Not Found!", Toast.LENGTH_LONG).show();
                    uname.setText("");
                }

            }
        });




        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "goin to sign up", Toast.LENGTH_LONG).show();
            }
        });



    }
}
