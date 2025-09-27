//`package com.example.passafe;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.card.MaterialCardView;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.firestore.Source;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//import com.google.firestore.v1.Target;
//
//import java.io.File;
//
//
//public class TrialActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_trial);
//
//        TextView trialview=(TextView) findViewById(R.id.TrialView);
//        DatabaseReference dbref=FirebaseDatabase.getInstance().getReference("this is the path");
//        dbref.setValue("here there").addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                trialview.setText("successful");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//
//            }
//        });
//
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference sf=storage.getReference();
//        Button trialbutton=(Button) findViewById(R.id.trialbutton);
//        trialbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Uri file =Uri.fromFile(new File("/storage/emulated/0/Android/media/com.whatsapp/WhatsApp/Media/WallPaper/7e0bcad2e3209aa415969fa5cc4f0b49.jpg"));
//                StorageReference imageref=sf.child("images/"+file.getLastPathSegment());
//                UploadTask uploadTask = imageref.putFile(file);
//                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Toast.makeText(TrialActivity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(TrialActivity.this, "Failure", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
////        Uri file =Uri.fromFile(new File("E:/Photos/InShot_20221020_214113059.jpg"));
////        StorageReference imageref=sf.child("images/"+file.getLastPathSegment());
////        UploadTask uploadTask = imageref.putFile(file);
////        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////            @Override
////            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                Toast.makeText(TrialActivity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Toast.makeText(TrialActivity.this, "Failure", Toast.LENGTH_SHORT).show();
////            }
////        });
//
//
//
//
//
//
//
//    }
//}`