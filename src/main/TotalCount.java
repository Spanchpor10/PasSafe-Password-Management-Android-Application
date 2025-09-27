//package com.example.passafe;
//
//import static com.example.passafe.DatabaseHelper.PCOLUMN_SITEID;
//import static com.example.passafe.DatabaseHelper.PCOLUMN_SITENAME;
//import static com.example.passafe.DatabaseHelper.PCOLUMN_SITEPASSWORD;
//import static com.example.passafe.DatabaseHelper.PCOLUMN_SITEUSERNAME;
//import static com.example.passafe.dashboard.USERTABLE;
//import static java.lang.String.valueOf;
//
//import android.app.AlertDialog;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.constraintlayout.widget.ConstraintSet;
//import androidx.core.widget.TextViewOnReceiveContentListener;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.material.card.MaterialCardView;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.UploadTask;
//
//import org.w3c.dom.Text;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class dashboard extends AppCompatActivity {
//    private Context context;
//    private String namefromsignup;
//    boolean isUpdated=false;
//    public static String USERTABLE;
//    private TableLayout table;
//    private ConstraintLayout constraintLayout;
//    private CardView editDeleteCard;
//    private TextView editText;
//    private TextView deleteText;
//    private int oldId=0;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        String username=getIntent().getStringExtra("username");
//        USERTABLE=username+"passworddetails";
//        DatabaseHelper databaseHelper=new DatabaseHelper(this);
//        SQLiteDatabase db=databaseHelper.getWritableDatabase();
//        String createPTable="CREATE TABLE IF NOT EXISTS "+ USERTABLE + " ( " + PCOLUMN_SITEID + " INTEGER PRIMARY KEY  , "
//                + PCOLUMN_SITENAME + " TEXT , " + PCOLUMN_SITEUSERNAME + " TEXT , " + PCOLUMN_SITEPASSWORD + " TEXT ) ";
//        db.execSQL(createPTable);
//
//
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//        Button getpassword=(Button) findViewById(R.id.getpassword);
//        TextView welcomeUser=(TextView) findViewById(R.id.welcome_user);
//        welcomeUser.setText("Welcome "+username+" !");
//        Button adddata=(Button) findViewById(R.id.upload);
//        table=(TableLayout) findViewById(R.id.tableLayout);
//        ImageView myprofile=(ImageView) findViewById(R.id.myprofile);
//        TextView nameofuser=(TextView) findViewById(R.id.nameofuser);
//        MaterialCardView myprofilecard=(MaterialCardView) findViewById(R.id.myprofileinfo);
//        myprofilecard.setEnabled(false);
//        myprofilecard.setVisibility(View.INVISIBLE);
//        CardView backgroundforprofile=(CardView) findViewById(R.id.backgroundcardformyprofile);
//        editDeleteCard=(CardView) findViewById(R.id.editDeleteCard);
//        editDeleteCard.setEnabled(false);
//        editDeleteCard.setVisibility(View.INVISIBLE);
//        constraintLayout = findViewById(R.id.constraintLayout);
//        editText=(TextView) findViewById(R.id.editTextView);
//        deleteText=(TextView) findViewById(R.id.deleteTextView);
//
//
//
//
//        context=dashboard.this;
////        DatabaseHelper databaseHelper=new DatabaseHelper(this);
//
//        Button logout=(Button) findViewById(R.id.logoutbutton);
//
//        String userpin;
//
//
//        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        userpin=prefs.getString("pin","");
//
//
//
//
//        adddata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatabaseHelper dbhelper=new DatabaseHelper(getApplicationContext());
//                // Create an instance of AlertDialog.Builder
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Enter site information");
//
//                LinearLayout layout = new LinearLayout(context);
//                layout.setOrientation(LinearLayout.VERTICAL);
//
//
//                final EditText siteIdInput = new EditText(context);
//                siteIdInput.setInputType(InputType.TYPE_CLASS_PHONE);
//                siteIdInput.setHint("Site ID");
//                layout.addView(siteIdInput);
//
//                final EditText siteNameInput = new EditText(context);
//                siteNameInput.setHint("Site Name");
//                layout.addView(siteNameInput);
//
//                final EditText siteUsernameInput = new EditText(context);
//                siteUsernameInput.setHint("Site Username");
//                layout.addView(siteUsernameInput);
//
//                final EditText sitePasswordInput = new EditText(context);
//                sitePasswordInput.setHint("Site Password");
//                layout.addView(sitePasswordInput);
//
//
//                builder.setView(layout);
//
//                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        isUpdated=false;
//                        // Get the values entered by the user
//                        int siteId = Integer.parseInt(siteIdInput.getText().toString());
//                        String siteName = siteNameInput.getText().toString();
//                        String siteUsername = siteUsernameInput.getText().toString();
//                        String sitePassword = sitePasswordInput.getText().toString();
//                        dbhelper.addPasswords(siteId,siteName,siteUsername,sitePassword);
//                        isUpdated=true;
//
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builder.show();
//
//            }
//        });
//
//
//        getpassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                builder.setTitle("Enter PIN");
//                builder.setMessage("Please enter your PIN:");
//
//                final EditText input = new EditText(context);
//                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
//                builder.setView(input);
//
//                builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String pinString = input.getText().toString();
//                        if (!TextUtils.isEmpty(pinString)) {
//                            if(pinString.equals(userpin)){
//                                table.removeViewsInLayout(1,table.getChildCount()-1);
//                                getPasswordsFromDB(table);
//
//                            }
//
//                            else{
//                                Toast.makeText(context, "Wrong Pin please retry!", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    }
//                });
//
//// Set the negative button to cancel the dialog
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//// Create and show the AlertDialog
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//
//
//            }
//        });
//        myprofile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(myprofilecard.getVisibility()==View.INVISIBLE) {
//                    myprofilecard.setEnabled(true);
//                    myprofilecard.setVisibility(View.VISIBLE);
//                    nameofuser.setText(username);
//                    nameofuser.setVisibility(View.VISIBLE);
//                    logout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
//                            editor.putBoolean("isLoggedIn", false);
//                            editor.apply();
//                            Intent intent=new Intent(dashboard.this,MainActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivity(intent);
//                        }
//                    });
//
//
//                }
//                else{
//                    myprofilecard.setEnabled(false);
//                    myprofilecard.setVisibility(View.INVISIBLE);
//
//                }
//            }
//        });
//
//
//
//    }//oncreate
//
//    @Override
//    public void onBackPressed() {
//        finishAffinity();
//    }
//
//
//    public void getPasswordsFromDB(TableLayout table){
//        DatabaseHelper databaseHelper=new DatabaseHelper(context);
//        table.removeViewsInLayout(1,table.getChildCount()-1);
//        String siteid;
//        siteid = null;
//        String sitename = null;
//        String username=null;
//        String password=null;
//        ArrayList<passwordmanager> passwords=databaseHelper.getPasswordDetails();
//        for(int i=0;i<passwords.size();i++){
//            TextView siteidview=new TextView(context);
//            TextView sitenameview=new TextView(context);
//            TextView siteusernameview=new TextView(context);
//            TextView sitepasswordview=new TextView(context);
//
//
//            TableRow row=new TableRow(context);
//            row.setPadding(0,5,0,20);
//
//
//            siteid= valueOf(passwords.get(i).siteid);
//            sitename= valueOf(passwords.get(i).sitename);
//            username= valueOf(passwords.get(i).siteusername);
//            password= valueOf(passwords.get(i).sitepassword);
//
//            siteidview.setText(siteid);
//            sitenameview.setText(sitename);
//            siteusernameview.setText(username);
//            sitepasswordview.setText(password);
//
//            siteidview.setGravity(Gravity.CENTER);
//            sitenameview.setGravity(Gravity.CENTER);
//            siteusernameview.setGravity(Gravity.CENTER);
//            sitepasswordview.setGravity(Gravity.CENTER);
//
//            siteidview.setTextColor(Color.BLACK);
//            sitenameview.setTextColor(Color.BLACK);
//            siteusernameview.setTextColor(Color.BLACK);
//            sitepasswordview.setTextColor(Color.BLACK);
//
//
//            row.addView(siteidview);
//            row.addView(sitenameview);
//            row.addView(siteusernameview);
//            row.addView(sitepasswordview);
//
//            row.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Toast.makeText(context, siteidview.getText(), Toast.LENGTH_SHORT).show();
//                    oldId=Integer.parseInt(siteidview.getText().toString());
//
//                    float x= v.getX();
//                    float y=v.getY();
//
////                ConstraintSet constraintSet = new ConstraintSet();
////                constraintSet.clone(constraintLayout);
////                editDeleteCard.setEnabled(true);
////                constraintSet.connect(editDeleteCard.getId(), ConstraintSet.START, constraintLayout.getId(), ConstraintSet.START, (int) x);
////                constraintSet.connect(editDeleteCard.getId(), ConstraintSet.TOP, constraintLayout.getId(), ConstraintSet.TOP, (int) y);
//                    editDeleteCard.setX(x);
//                    editDeleteCard.setY(y);
////                constraintSet.applyTo(constraintLayout);
//                    editDeleteCard.setVisibility(View.VISIBLE);
//                    return true;
//                }
//            });
//
//            editText.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DatabaseHelper dbhelper=new DatabaseHelper(getApplicationContext());
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("Enter updated information");
//
//                    LinearLayout layout = new LinearLayout(context);
//                    layout.setOrientation(LinearLayout.VERTICAL);
//
//
//                    final EditText siteIdInput = new EditText(context);
//                    siteIdInput.setInputType(InputType.TYPE_CLASS_PHONE);
//                    siteIdInput.setPadding(50,20,50,20);
//                    siteIdInput.setHint("New Site ID");
//                    layout.addView(siteIdInput);
//
//                    final EditText siteNameInput = new EditText(context);
//                    siteNameInput.setHint("New Site Name");
//                    siteNameInput.setPadding(50,20,50,20);
//                    layout.addView(siteNameInput);
//
//                    final EditText siteUsernameInput = new EditText(context);
//                    siteUsernameInput.setHint("New Site Username");
//                    siteUsernameInput.setPadding(50,20,50,20);
//                    layout.addView(siteUsernameInput);
//
//                    final EditText sitePasswordInput = new EditText(context);
//                    sitePasswordInput.setHint("New Site Password");
//                    sitePasswordInput.setPadding(50,20,50,20);
//                    layout.addView(sitePasswordInput);
//
//                    builder.setView(layout);
//
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            int siteId = Integer.parseInt(siteIdInput.getText().toString());
//                            String siteName = siteNameInput.getText().toString();
//                            String siteUsername = siteUsernameInput.getText().toString();
//                            String sitePassword = sitePasswordInput.getText().toString();
//
//                            dbhelper.updatePasswords(oldId,siteId,siteName,siteUsername,sitePassword,USERTABLE);
//                            getPasswordsFromDB(table);
//                            if(editDeleteCard.getVisibility()==View.VISIBLE){
//                                editDeleteCard.setVisibility(View.INVISIBLE);
//                            }
//
//                        }
//                    });
//
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    builder.show();
//
//                }
//            });
//
//            deleteText.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    DatabaseHelper dbhelper=new DatabaseHelper(getApplicationContext());
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("Enter updated information");
//
//                    LinearLayout layout = new LinearLayout(context);
//                    layout.setOrientation(LinearLayout.VERTICAL);
//
//
//                    final TextView confirmation = new TextView(context);
//                    confirmation.setText("Do you really want to delete the selected record?");
//                    layout.addView(confirmation);
//
//                    builder.setView(layout);
//
//                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                            dbhelper.deletePassword(Integer.parseInt(siteidview.getText().toString()),USERTABLE);
//                            getPasswordsFromDB(table);
//                            if(editDeleteCard.getVisibility()==View.VISIBLE){
//                                editDeleteCard.setVisibility(View.INVISIBLE);
//                            }
//                        }
//                    });
//
//                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//
//                    builder.show();
//
//                }
//            });
//
//            row.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(editDeleteCard.getVisibility()==View.VISIBLE){
//                        editDeleteCard.setVisibility(View.INVISIBLE);
//                    }
//                }
//            });
//            table.addView(row);
//            table.setGravity(Gravity.TOP);
//
//
//
//        }
//    }
//}
//
//package com.example.passafe;
//
//        import android.content.Context;
//
//        import android.database.sqlite.SQLiteDatabase;
//        import android.database.sqlite.SQLiteOpenHelper;
//
//        import androidx.annotation.Nullable;
//
//public class Database_handler extends SQLiteOpenHelper {
//    private static final int DATABASE_VERSION=1;
//    private static final String DATABASE_NAME="mydatabase.db";
//    private static final String TABLE_NAME="userdata";
//    private static final String COLUMN_ID="id";
//    private static final String COLUMN_NAME="name";
//    private static final String COLUMN_COUNTRY="country";
//
//    SQLiteDatabase mydatabase;
//
//    public Database_handler(@Nullable Context context) {
//        super(context, DATABASE_NAME,null,DATABASE_VERSION,null);
//        mydatabase=getWritableDatabase();
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE "+TABLE_NAME+" ( "+COLUMN_ID+" INTEGER PRIMARY KEY,"+COLUMN_NAME+" TEXT, "+COLUMN_COUNTRY+" TEXT )");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//
//}
//package com.example.passafe;
//
//        import static com.example.passafe.dashboard.USERTABLE;
//
//        import android.content.ContentValues;
//        import android.content.Context;
//        import android.database.Cursor;
//        import android.database.sqlite.SQLiteDatabase;
//        import android.database.sqlite.SQLiteOpenHelper;
//
//        import java.util.ArrayList;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//    public static final String DATABASE_NAME="pasSafeDb";
//    public static final int DATABASE_VERSION=1;
//    public static final String TABLE_NAME="userdetails";
//    public static final String PTABLE_NAME="passworddetails";
//
//    public static final String COLUMN_ID="id";
//
//    public static final String PCOLUMN_SITEID="siteid";
//    public static final String PCOLUMN_SITENAME="sitename";
//    public static final String PCOLUMN_SITEUSERNAME="siteusername";
//    public static final String PCOLUMN_SITEPASSWORD="sitepassword";
//    public static final String COLUMN_USERNAME="name";
//    public static final String COLUMN_PASSWORD="email";
//    public DatabaseHelper(Context context){
//        super(context,DATABASE_NAME,null,DATABASE_VERSION);
//
//    }
//
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        String createTable=" CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
//                + COLUMN_USERNAME + " TEXT , " + COLUMN_PASSWORD + " TEXT ) ";
//        db.execSQL(createTable);
//
////        String createPTable="CREATE TABLE "+ PTABLE_NAME + " ( " + PCOLUMN_SITEID + " INTEGER PRIMARY KEY  , "
////                            + PCOLUMN_SITENAME + " TEXT , " + PCOLUMN_SITEUSERNAME + " TEXT , " + PCOLUMN_SITEPASSWORD + " TEXT ) ";
////        db.execSQL(createPTable);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
//        onCreate(db);
//        db.execSQL(" DROP TABLE IF EXISTS " + USERTABLE);
//        onCreate(db);
//    }
//
//
//    public boolean addUser(String username,String password){
//
//        SQLiteDatabase db=this.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put(COLUMN_USERNAME,username);
//        values.put(COLUMN_PASSWORD,password);
//
//        long result=db.insert(TABLE_NAME,null,values);
//        return result!=1;
//    }
//
//
//    public ArrayList<UserManager> getUser(){
//
//        SQLiteDatabase db=this.getReadableDatabase();
//        Cursor cursor=db.rawQuery(" SELECT * FROM " + TABLE_NAME ,null);
//        ArrayList<UserManager> arruser= new ArrayList<>();
//        while (cursor.moveToNext()){
//            UserManager userManager=new UserManager();
//            userManager.id=cursor.getInt(0);
//            userManager.UserName=cursor.getString(1);
//            userManager.PassWord=cursor.getString(2);
//
//            arruser.add(userManager);
//        }
//        return arruser;
//    }
//
//    public ArrayList<UserManager> getUsernames(){
//
//        SQLiteDatabase db=this.getReadableDatabase();
//        Cursor cursor=db.rawQuery("SELECT name FROM " + TABLE_NAME,null);
//        ArrayList<UserManager> arruseernames=new ArrayList<>();
//        while(cursor.moveToNext()){
//            UserManager userManager=new UserManager();
//            userManager.UserName=cursor.getString(0);
//            arruseernames.add(userManager);
//        }
//        return arruseernames;
//    }
//
//    public ArrayList<UserManager> getPasswords(){
//
//        SQLiteDatabase db=this.getReadableDatabase();
//        Cursor cursor=db.rawQuery("SELECT email FROM " + TABLE_NAME,null);
//        ArrayList<UserManager> arrpasswords=new ArrayList<>();
//        while(cursor.moveToNext()){
//            UserManager userManager=new UserManager();
//            userManager.PassWord=cursor.getString(0);
//            arrpasswords.add(userManager);
//        }
//        return arrpasswords;
//    }
//
//
//    public ArrayList<passwordmanager> getPasswordDetails(){
//        SQLiteDatabase db= this.getReadableDatabase();
//        Cursor cursor=db.rawQuery("SELECT * FROM "+ USERTABLE ,null);
//        ArrayList<passwordmanager> arrpassworddetails=new ArrayList<>();
//        while(cursor.moveToNext()){
//            passwordmanager passwordmanager=new passwordmanager();
//            passwordmanager.siteid=cursor.getInt(0);
//            passwordmanager.sitename=cursor.getString(1);
//            passwordmanager.siteusername=cursor.getString(2);
//            passwordmanager.sitepassword=cursor.getString(3);
//            arrpassworddetails.add(passwordmanager);
//        }
//        return arrpassworddetails;
//    }
//
//
//    public void addPasswords(int siteid,String sitename,String siteusername,String sitepassword){
//        SQLiteDatabase db=this.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put(PCOLUMN_SITEID,siteid);
//        values.put(PCOLUMN_SITENAME,sitename);
//        values.put(PCOLUMN_SITEUSERNAME,siteusername);
//        values.put(PCOLUMN_SITEPASSWORD,sitepassword);
//
//        db.insert(USERTABLE,null,values);
//    }
//
//    public void updatePasswords(int oldId,int siteid,String sitename,String siteusername,String sitepassword,String USERTABLE){
//        SQLiteDatabase db=this.getWritableDatabase();
//        String updateValues="UPDATE "+ USERTABLE +" SET "+ PCOLUMN_SITEID +" = "+ siteid +", "
//                + PCOLUMN_SITENAME +" = '"+ sitename +"', "+ PCOLUMN_SITEUSERNAME +" = '"+ siteusername +"', "
//                + PCOLUMN_SITEPASSWORD +" = '"+ sitepassword +"' WHERE "+ PCOLUMN_SITEID +" = "+ oldId ;
//        db.execSQL(updateValues);
//
//    }
//
//
//    public void deletePassword(int siteId,String USERTABLE){
//        SQLiteDatabase db=this.getWritableDatabase();
//        String deleteValue=" DELETE FROM "+ USERTABLE +" WHERE "+ PCOLUMN_SITEID +" = "+ siteId;
//        db.execSQL(deleteValue);
//    }
//
//
//
////    public List<String> getNames(){
////        List<String> names=new ArrayList<>();
////        SQLiteDatabase db=this.getReadableDatabase();
////        Cursor cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME ,null);
//////        Cursor cursor=db.query(TABLE_NAME,new String[]{COLUMN_NAME},null,null,null,null,null);
//////        if(cursor.moveToFirst()){
//////            do{
//////                names.add(cursor.getString(0));
//////            }while(cursor.moveToLast());
//////        }
////        while(cursor.moveToNext()){
////            names.add(cursor.getString(0));
////        }
////        cursor.close();
////        return names;
////    }
//}
//package com.example.passafe;
//
//        import static android.content.ContentValues.TAG;
//        import static com.example.passafe.R.id.go_to_signup_button;
//
//        import androidx.annotation.NonNull;
//        import androidx.annotation.Nullable;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.app.Activity;
//        import android.content.ContentValues;
//        import android.content.Intent;
//        import android.content.SharedPreferences;
//        import android.graphics.Color;
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.TextView;
//        import android.widget.Toast;
////import android.widget.*;
//
//        import com.google.android.gms.tasks.OnCompleteListener;
//        import com.google.android.gms.tasks.OnFailureListener;
//        import com.google.android.gms.tasks.OnSuccessListener;
//        import com.google.android.gms.tasks.Task;
//        import com.google.android.material.card.MaterialCardView;
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//        import com.google.firebase.firestore.CollectionReference;
//        import com.google.firebase.firestore.DocumentReference;
//        import com.google.firebase.firestore.DocumentSnapshot;
//        import com.google.firebase.firestore.EventListener;
//        import com.google.firebase.firestore.FirebaseFirestore;
//        import com.google.firebase.firestore.FirebaseFirestoreException;
//        import com.google.firebase.firestore.QueryDocumentSnapshot;
//        import com.google.firebase.firestore.QuerySnapshot;
//        import com.google.firebase.firestore.Source;
//        import com.google.firestore.v1.Target;
//
//        import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//    public static boolean isLoggedIn;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        String nameofuser=getIntent().getStringExtra("name");
//        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        isLoggedIn = prefs.getBoolean("isLoggedIn", false);
//        String passname=prefs.getString("username","");
//
//        if(isLoggedIn==true){
//            Intent intent=new Intent(MainActivity.this,dashboard.class);
//            intent.putExtra("username",passname);
//            startActivity(intent);
//        }
//
//
//
////        FirebaseFirestore db = FirebaseFirestore.getInstance();
////        CollectionReference cr=db.collection("userdetails");
////        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().getRoot();
//
//        TextView uname = (TextView) findViewById(R.id.username_area);
//        TextView pin1 = (TextView) findViewById(R.id.pin_area);
//        MaterialCardView ucard = (MaterialCardView) findViewById(R.id.uname_card);
//        MaterialCardView pcard = (MaterialCardView) findViewById(R.id.pin_card);
//        MaterialCardView maincard = (MaterialCardView) findViewById(R.id.login_card);
//        Button submit_button = (Button) findViewById(R.id.submit_button);
//        Button gotosignup = (Button) findViewById(R.id.go_to_signup_button);
//
//
////        Database_handler dbhandler=new Database_handler(this);
//        DatabaseHelper dbhelper=new DatabaseHelper(this);
//
//
////        dbhelper.addUser("sarthak","2115");
////        dbhelper.addUser("disha","disha1");
////        dbhelper.addUser("madhur","madhur1");
//
//
//        submit_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Boolean isvaliduser=false;
//                String username=uname.getText().toString();
//                String gotfromdb;
//                ArrayList<UserManager> usernames=dbhelper.getUsernames();
//                for (int i=0;i<usernames.size();i++){
//                    gotfromdb=usernames.get(i).UserName;
//                    if (gotfromdb.equals(username)){
////                        Toast.makeText(MainActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();
//                        isvaliduser=true;
//                    }
//                    else{
////                        Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
//                        continue;
//                    }
//                }
//                if (isvaliduser==true){
//                    Toast.makeText(MainActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();
////                    upass.setVisibility(View.VISIBLE);
////                    submit_button.setOnClickListener(new View.OnClickListener() {
////                        @Override
////                        public void onClick(View view) {
//                    Boolean isvalidpass=false;
//                    String userpassword = pin1.getText().toString();
//                    String gotpassfromdb;
//                    ArrayList<UserManager> passwords = dbhelper.getPasswords();
//                    for (int i = 0; i < passwords.size(); i++) {
//                        gotpassfromdb = passwords.get(i).PassWord;
//                        if (gotpassfromdb.equals(userpassword)) {
////                                    Toast.makeText(MainActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();
//                            isvalidpass=true;
//                        } else {
////                                    Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
//                            continue;
//                        }
//                    }
//                    if(isvalidpass==true){
//
//                        Toast.makeText(MainActivity.this, "Successfull!", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, dashboard.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                        intent.putExtra("username", username);
//                        intent.putExtra("pin", userpassword);
//                        intent.putExtra("nameofuser",nameofuser);
//                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
//                        startActivity(intent);
////                                isLoggedIn=true;
//                        SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
//                        editor.putBoolean("isLoggedIn", true);
//                        editor.putString("username",username);
//                        editor.putString("pin",userpassword);
//                        editor.apply();
//
//
//
//                    }
//                    else {
//
//                        Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
//                        pcard.setStrokeColor(Color.RED);
////                        Toast.makeText(getApplicationContext(),"Wrong pin,please retry!",Toast.LENGTH_LONG).show();
//                        pin1.setText("");
//                    }
//                }
////                    });
////                }
//                else{
//                    Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
//                    isvaliduser=false;
//                    ucard.setStrokeColor(Color.RED);
//                    Toast.makeText(getApplicationContext(), "User Not Found!", Toast.LENGTH_LONG).show();
//                    uname.setText("");
//                }
//
//            }
//        });
//
//
//
//
//        gotosignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, signup.class);
//                startActivity(intent);
//                Toast.makeText(MainActivity.this, "goin to sign up", Toast.LENGTH_LONG).show();
//            }
//        });
//
///*
//        submit_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = uname.getText().toString();
//                String pin = pin1.getText().toString();
//
//                DocumentReference df=cr.document(username);
//                df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                if(documentSnapshot.exists()){
//                                    String mypin=documentSnapshot.getString("pin");
//                                    String myusername=documentSnapshot.getString("username");
////                            uname.setText(myusername);
////                            pin1.setText(mypin);
//
//                                    if(username.equals(myusername)) {
//                                        if (pin.equals(mypin)) {
//                                            Intent intent = new Intent(MainActivity.this, dashboard.class);
//                                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                                            intent.putExtra("username", username);
//                                            intent.putExtra("pin", pin);
//                                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
//                                            startActivity(intent);
//                                        }
//                                        else{
//                                            pcard.setStrokeColor(Color.RED);
//                                            Toast.makeText(getApplicationContext(),"Wrong pin,please retry!",Toast.LENGTH_LONG).show();
//                                            pin1.setText("");
//                                        }
//                                    }
//                                    else{
//                                        ucard.setStrokeColor(Color.RED);
//                                        Toast.makeText(getApplicationContext(), "User Not Found!", Toast.LENGTH_LONG).show();
//                                        uname.setText("");
//
//
//                                    }
//                                }
//                                else {
////                            uname.setText("failure boss");
//                                    Toast.makeText(MainActivity.this, "Cannot find user!\nPlease Signup", Toast.LENGTH_LONG).show();
//                                    maincard.setStrokeColor(Color.RED);
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                ucard.setStrokeColor(Color.RED);
//                                Toast.makeText(getApplicationContext(), "no document found!", Toast.LENGTH_LONG).show();
//                                maincard.setStrokeColor(Color.RED);
//                                uname.setText("");
//
//
//                            }
//                        });
//
//                        }
//            });
//
//            */
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////                dbref.addValueEventListener(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(@NonNull DataSnapshot snapshot) {
////                        snapshot.getValue();
////                    }
////
////                    @Override
////                    public void onCancelled(@NonNull DatabaseError error) {
////
////
////                    }
////                });
//
//
//
//
//
//
//                /*
//                db.collection("userdetails")
//                        .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Log.d(TAG, document.getId() + " => " + document.getData());
//                                        String myusername=document.getId().toString();
//                                        if(username.equals(myusername)){
//                                            Toast.makeText(MainActivity.this, "User is valid!", Toast.LENGTH_LONG).show();
//                                        }
//                                        else{
//                                            Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//                                } else {
//                                    Log.w(TAG, "Error getting documents.", task.getException());
//                                }
//
//
////                Toast.makeText(MainActivity.this, "welcome", Toast.LENGTH_LONG).show();
////                Intent intent=new Intent(MainActivity.this,dashboard.class);
////                intent.putExtra("username", goformserveruname);
////                startActivity(intent);
//
//
////                Map<String, Object> user = new HashMap<>();
////                user.put("username", username);
////                user.put("pin", pin);
//////                user.put("born", 1815);
////                  d
////
////                // Add a new document with a generated ID
////                db.collection("users").document(username)
////                        .set(user, SetOptions.merge())
////                        .addOnSuccessListener(new OnSuccessListener<Void>() {
////                            @Override
////                            public void onSuccess(Void avoid) {
////                                Toast.makeText(getApplicationContext(),"DocumentSnapshot added successfully",Toast.LENGTH_LONG).show();
////                                Log.d(TAG, "DocumentSnapshot added successfully");
////                            }
////                        })
////
////                        .addOnFailureListener(new OnFailureListener() {
////                            @Override
////                            public void onFailure(@NonNull Exception e) {
////                                Log.w(TAG, "Error adding document", e);
////                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
////                            }
////                        });
//
//
////                if(username.equals(sarthak)) {
////                    if (pin.equals(mypin)) {
////                        Intent intent = new Intent(MainActivity.this, dashboard.class);
////                        intent.putExtra("username", username);
////                        intent.putExtra("pin", pin);
////                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
////                        startActivity(intent);
////                    }
////                    else{
////                        pcard.setStrokeColor(Color.RED);
////                        Toast.makeText(getApplicationContext(),"Wrong pin,please retry!",Toast.LENGTH_LONG).show();
////                        pin1.setText("");
////                    }
////                }
////                else{
////                    ucard.setStrokeColor(Color.RED);
////                    Toast.makeText(getApplicationContext(), "User Not Found!", Toast.LENGTH_LONG).show();
////                    uname.setText("");
////
////
////                }
//
//                            }
//                        });
//            }*/
//
//
//
//
//
//    }
//}
//package com.example.passafe;
//
//public class passwordmanager {
//    int siteid;
//    String sitename;
//    String siteusername;
//    String sitepassword;
//}
//
//package com.example.passafe;
//
//        import static com.example.passafe.R.id.signup_submit;
//
//        import androidx.annotation.NonNull;
//        import androidx.annotation.Nullable;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.appcompat.widget.TintTypedArray;
//
//        import android.content.SharedPreferences;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.TextView;
//
//
//        import android.content.Intent;
//        import android.graphics.Color;
//        import android.os.Bundle;
//        import android.util.Log;
//        import android.view.View;
//        import android.view.animation.Animation;
//        import android.widget.Button;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import com.google.android.gms.tasks.OnFailureListener;
//        import com.google.android.gms.tasks.OnSuccessListener;
//        import com.google.android.material.card.MaterialCardView;
//        import com.google.firebase.firestore.DocumentReference;
//        import com.google.firebase.firestore.DocumentSnapshot;
//        import com.google.firebase.firestore.EventListener;
//        import com.google.firebase.firestore.FirebaseFirestore;
//        import com.google.firebase.firestore.FirebaseFirestoreException;
//        import com.google.firebase.firestore.SetOptions;
//
//        import java.util.HashMap;
//        import java.util.Map;
//
//        import org.w3c.dom.Text;
//
//public class signup extends AppCompatActivity {
//    public String username;
//    public static String nameofuser;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        Button signup_submit=(Button) findViewById(R.id.signup_submit);
//        Button backtologin=(Button) findViewById(R.id.back_to_homepage);
//        TextView name1=(TextView) findViewById(R.id.getname);
//        TextView uname=(TextView) findViewById(R.id.getusername);
//        TextView pin1=(TextView) findViewById(R.id.getpin);
//
//        DatabaseHelper dbhelper=new DatabaseHelper(this);
////        FirebaseFirestore db=FirebaseFirestore.getInstance();
//
//
//        signup_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                nameofuser=name1.getText().toString();
//                String username=uname.getText().toString();
//                String pin=pin1.getText().toString();
//                Intent intent=new Intent(signup.this,MainActivity.class);
//                boolean isUserAdded= dbhelper.addUser(username,pin);
//                if(isUserAdded){
//                    Toast.makeText(signup.this, "User Added Sucessfully!", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(signup.this, "Internal Error", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        backtologin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(signup.this,MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });
//    }
//}
//
//package com.example.passafe;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//
//        import android.net.Uri;
//        import android.os.Bundle;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.TextView;
//        import android.widget.Toast;
//
//        import com.google.android.gms.tasks.OnCompleteListener;
//        import com.google.android.gms.tasks.OnFailureListener;
//        import com.google.android.gms.tasks.OnSuccessListener;
//        import com.google.android.gms.tasks.Task;
//        import com.google.android.material.card.MaterialCardView;
//        import com.google.firebase.database.DataSnapshot;
//        import com.google.firebase.database.DatabaseError;
//        import com.google.firebase.database.DatabaseReference;
//        import com.google.firebase.database.FirebaseDatabase;
//        import com.google.firebase.database.ValueEventListener;
//        import com.google.firebase.firestore.CollectionReference;
//        import com.google.firebase.firestore.DocumentReference;
//        import com.google.firebase.firestore.DocumentSnapshot;
//        import com.google.firebase.firestore.EventListener;
//        import com.google.firebase.firestore.FirebaseFirestore;
//        import com.google.firebase.firestore.FirebaseFirestoreException;
//        import com.google.firebase.firestore.QueryDocumentSnapshot;
//        import com.google.firebase.firestore.QuerySnapshot;
//        import com.google.firebase.firestore.Source;
//        import com.google.firebase.storage.FirebaseStorage;
//        import com.google.firebase.storage.StorageReference;
//        import com.google.firebase.storage.UploadTask;
//        import com.google.firestore.v1.Target;
//
//        import java.io.File;
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
//        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference("this is the path");
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
//}
//
//package com.example.passafe;
//
//public class UserManager {
//    int id;
//    String UserName;
//    String PassWord;
//}
