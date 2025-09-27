package com.example.passafe;

import static com.example.passafe.R.id.signup_submit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TintTypedArray;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Text;

public class signup extends AppCompatActivity {
    public String username;
    public static String nameofuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button signup_submit=(Button) findViewById(R.id.signup_submit);
        Button backtologin=(Button) findViewById(R.id.back_to_homepage);
        TextView name1=(TextView) findViewById(R.id.getname);
        TextView uname=(TextView) findViewById(R.id.getusername);
        TextView pin1=(TextView) findViewById(R.id.getpin); 

        DatabaseHelper dbhelper=new DatabaseHelper(this);
//        FirebaseFirestore db=FirebaseFirestore.getInstance();


     signup_submit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            nameofuser=name1.getText().toString();
            String username=uname.getText().toString();
            String pin=pin1.getText().toString();
            Intent intent=new Intent(signup.this,MainActivity.class);
            boolean isUserAdded= dbhelper.addUser(username,pin);
            if(isUserAdded){
                Toast.makeText(signup.this, "User Added Sucessfully!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(signup.this, "Internal Error", Toast.LENGTH_SHORT).show();
            }

         }
     });

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(signup.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}