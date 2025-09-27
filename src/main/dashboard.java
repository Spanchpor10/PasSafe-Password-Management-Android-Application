package com.example.passafe;

import static com.example.passafe.DatabaseHelper.PCOLUMN_SITEID;
import static com.example.passafe.DatabaseHelper.PCOLUMN_SITENAME;
import static com.example.passafe.DatabaseHelper.PCOLUMN_SITEPASSWORD;
import static com.example.passafe.DatabaseHelper.PCOLUMN_SITEUSERNAME;
import static java.lang.String.valueOf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class dashboard extends AppCompatActivity {
    private Context context;
    private String namefromsignup;
    boolean isUpdated = false;
    public static String USERTABLE;
    private TableLayout table;
    private ConstraintLayout constraintLayout;
    private CardView editDeleteCard;
    private TextView editText;
    private TextView deleteText;
    private int oldId = 0;
    private ImageView blur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String username = getIntent().getStringExtra("username");
        USERTABLE = username + "passworddetails";

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String createPTable = "CREATE TABLE IF NOT EXISTS " + USERTABLE + " ( " + PCOLUMN_SITEID + " INTEGER PRIMARY KEY  , "
                + PCOLUMN_SITENAME + " TEXT , " + PCOLUMN_SITEUSERNAME + " TEXT , " + PCOLUMN_SITEPASSWORD + " TEXT ) ";
        db.execSQL(createPTable);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button getpassword = findViewById(R.id.getpassword);
        TextView welcomeUser = findViewById(R.id.welcome_user);
        welcomeUser.setText("Welcome " + username + " !");
        Button adddata = findViewById(R.id.upload);
        table = findViewById(R.id.tableLayout);
        ImageView myprofile = findViewById(R.id.myprofile);
        TextView nameofuser = findViewById(R.id.nameofuser);
        MaterialCardView myprofilecard = findViewById(R.id.myprofileinfo);
        myprofilecard.setEnabled(false);
        myprofilecard.setVisibility(View.INVISIBLE);
        CardView backgroundforprofile = findViewById(R.id.backgroundcardformyprofile);
        editDeleteCard = findViewById(R.id.editDeleteCard);
        editDeleteCard.setEnabled(false);
        editDeleteCard.setVisibility(View.INVISIBLE);
        constraintLayout = findViewById(R.id.constraintLayout);
        editText = findViewById(R.id.editTextView);
        deleteText = findViewById(R.id.deleteTextView);
        blur = findViewById(R.id.blur);
        blur.setVisibility(View.INVISIBLE);

        context = dashboard.this;

        Button logout = findViewById(R.id.logoutbutton);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String userpin = prefs.getString("pin", "");

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                break;
        }

        Executor executor = ContextCompat.getMainExecutor(this);

        final BiometricPrompt biometricPrompt;
        biometricPrompt = new BiometricPrompt(dashboard.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Verified!", Toast.LENGTH_SHORT).show();
                table.removeViewsInLayout(1, table.getChildCount() - 1);
                getPasswordsFromDB(table);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        adddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Enter site information");

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText siteIdInput = new EditText(context);
                siteIdInput.setInputType(InputType.TYPE_CLASS_PHONE);
                siteIdInput.setHint("Site ID");
                layout.addView(siteIdInput);

                final EditText siteNameInput = new EditText(context);
                siteNameInput.setHint("Site Name");
                layout.addView(siteNameInput);

                final EditText siteUsernameInput = new EditText(context);
                siteUsernameInput.setHint("Site Username");
                layout.addView(siteUsernameInput);

                final EditText sitePasswordInput = new EditText(context);
                sitePasswordInput.setHint("Site Password");
                layout.addView(sitePasswordInput);

                builder.setView(layout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isUpdated = false;
                        int siteId = Integer.parseInt(siteIdInput.getText().toString());
                        String siteName = siteNameInput.getText().toString();
                        String siteUsername = siteUsernameInput.getText().toString();
                        String sitePassword = sitePasswordInput.getText().toString();
                        dbhelper.addPasswords(siteId, siteName, siteUsername, sitePassword);
                        isUpdated = true;
                        getPasswordsFromDB(table);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        getpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Verify fingerprint to continue")
                        .setDescription("Use your fingerprint to get passwords ")
                        .setDeviceCredentialAllowed(true)
                        .build();
                biometricPrompt.authenticate(promptInfo);
            }
        });

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myprofilecard.getVisibility() == View.INVISIBLE) {
                    myprofilecard.setEnabled(true);
                    blur.setVisibility(View.VISIBLE);
                    myprofilecard.setVisibility(View.VISIBLE);
                    nameofuser.setText("Name: " + username);
                    nameofuser.setVisibility(View.VISIBLE);
                    logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences.Editor editor = getSharedPreferences("MyPrefs", MODE_PRIVATE).edit();
                            editor.putBoolean("isLoggedIn", false);
                            editor.apply();
                            Intent intent = new Intent(dashboard.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                } else {
                    myprofilecard.setEnabled(false);
                    blur.setVisibility(View.INVISIBLE);
                    myprofilecard.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void getPasswordsFromDB(TableLayout table) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        table.removeViewsInLayout(1, table.getChildCount() - 1);
        String siteid;
        siteid = null;
        String sitename = null;
        String username = null;
        String password = null;
        ArrayList<passwordmanager> passwords = databaseHelper.getPasswordDetails();
        for (int i = 0; i < passwords.size(); i++) {
            TextView siteidview = new TextView(context);
            TextView sitenameview = new TextView(context);
            TextView siteusernameview = new TextView(context);
            TextView sitepasswordview = new TextView(context);

            TableRow row = new TableRow(context);
            row.setPadding(0, 5, 0, 20);

            siteid = valueOf(passwords.get(i).siteid);
            sitename = valueOf(passwords.get(i).sitename);
            username = valueOf(passwords.get(i).siteusername);
            password = valueOf(passwords.get(i).sitepassword);

            siteidview.setText(siteid);
            sitenameview.setText(sitename);
            siteusernameview.setText(username);
            sitepasswordview.setText(password);

            siteidview.setGravity(Gravity.CENTER);
            sitenameview.setGravity(Gravity.CENTER);
            siteusernameview.setGravity(Gravity.CENTER);
            sitepasswordview.setGravity(Gravity.CENTER);

            siteidview.setTextColor(Color.WHITE);
            sitenameview.setTextColor(Color.WHITE);
            siteusernameview.setTextColor(Color.WHITE);
            sitepasswordview.setTextColor(Color.WHITE);

            int fontResourceId = getResources().getIdentifier("sfprodisplaymedium", "font", getPackageName());
            Typeface typeface = getResources().getFont(fontResourceId);

            siteidview.setTypeface(typeface);
            sitenameview.setTypeface(typeface);
            siteusernameview.setTypeface(typeface);
            sitepasswordview.setTypeface(typeface);

            row.addView(siteidview);
            row.addView(sitenameview);
            row.addView(siteusernameview);
            row.addView(sitepasswordview);

            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, siteidview.getText(), Toast.LENGTH_SHORT).show();
                    oldId = Integer.parseInt(siteidview.getText().toString());

                    float x = v.getX();
                    float y = v.getY();

                    editDeleteCard.setX(x);
                    editDeleteCard.setY(y);
                    editDeleteCard.setVisibility(View.VISIBLE);
                    return true;
                }
            });

            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Enter updated information");

                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    final EditText siteIdInput = new EditText(context);
                    siteIdInput.setInputType(InputType.TYPE_CLASS_PHONE);
                    siteIdInput.setPadding(50, 20, 50, 20);
                    siteIdInput.setHint("New Site ID");
                    layout.addView(siteIdInput);

                    final EditText siteNameInput = new EditText(context);
                    siteNameInput.setHint("New Site Name");
                    siteNameInput.setPadding(50, 20, 50, 20);
                    layout.addView(siteNameInput);

                    final EditText siteUsernameInput = new EditText(context);
                    siteUsernameInput.setHint("New Site Username");
                    siteUsernameInput.setPadding(50, 20, 50, 20);
                    layout.addView(siteUsernameInput);

                    final EditText sitePasswordInput = new EditText(context);
                    sitePasswordInput.setHint("New Site Password");
                    sitePasswordInput.setPadding(50, 20, 50, 20);
                    layout.addView(sitePasswordInput);

                    builder.setView(layout);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int siteId = Integer.parseInt(siteIdInput.getText().toString());
                            String siteName = siteNameInput.getText().toString();
                            String siteUsername = siteUsernameInput.getText().toString();
                            String sitePassword = sitePasswordInput.getText().toString();

                            dbhelper.updatePasswords(oldId, siteId, siteName, siteUsername, sitePassword, USERTABLE);
                            getPasswordsFromDB(table);
                            if (editDeleteCard.getVisibility() == View.VISIBLE) {
                                editDeleteCard.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            deleteText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseHelper dbhelper = new DatabaseHelper(getApplicationContext());

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Enter updated information");

                    LinearLayout layout = new LinearLayout(context);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    final TextView confirmation = new TextView(context);
                    confirmation.setText("Do you really want to delete the selected record?");
                    layout.addView(confirmation);

                    builder.setView(layout);

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbhelper.deletePassword(Integer.parseInt(siteidview.getText().toString()), USERTABLE);
                            getPasswordsFromDB(table);
                            if (editDeleteCard.getVisibility() == View.VISIBLE) {
                                editDeleteCard.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editDeleteCard.getVisibility() == View.VISIBLE) {
                        editDeleteCard.setVisibility(View.INVISIBLE);
                    }
                }
            });

            table.addView(row);
            table.setGravity(Gravity.TOP);
        }
    }
}
