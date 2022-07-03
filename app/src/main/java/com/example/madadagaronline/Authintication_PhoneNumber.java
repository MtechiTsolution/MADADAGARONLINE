package com.example.madadagaronline;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;


public class Authintication_PhoneNumber extends AppCompatActivity {


    EditText e1;
  //  PinView e2;
    PinView e2;



    String mVerificationId, phoneNumber;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseAuth mAuth;
    FirebaseUser user;
    PhoneAuthProvider.ForceResendingToken tok;
    ProgressDialog progressDialog;

    DatabaseReference databaseReference;
    SharedPreferences preferences;
    ProgressBar progressBar;
    SharedPreferences.Editor editor;
    Button positiveDialog;
    Button negativeDialog;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    TextView Phoneauthnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authintication_phone_number);
//            TextView linkTextView = findViewById(R.id.activity_main_link);
//            linkTextView.setMovementMethod(LinkMovementMethod.getInstance());


        getSupportActionBar().setTitle("Authentication");
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        getSupportActionBar().setIcon(R.drawable.phone);
//
//         Define ColorDrawable object and parse color
//         using parseColor method
//         with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#012535"));

//         Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        e1 = findViewById(R.id.phonenumber);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(Authintication_PhoneNumber.this);

        getSupportActionBar().hide();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                e2.setText(phoneAuthCredential.getSmsCode());

                Toast.makeText(Authintication_PhoneNumber.this, "Success", Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                progressDialog.dismiss();
                alertDialog.dismiss();
                Toast.makeText(Authintication_PhoneNumber.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();

                //View1.setVisibility(View.VISIBLE);
                // View2.setVisibility(View.GONE);
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                tok = token;

                progressDialog.dismiss();
                alertDialog.dismiss();

                authinticating();


                //  View2.setVisibility(View.VISIBLE);
                //  View1.setVisibility(View.GONE);


            }
        };


    }


    public void onclick(View view) {

        phoneNumber = e1.getText().toString().trim();
        preferences = PreferenceManager.getDefaultSharedPreferences(Authintication_PhoneNumber.this);
        editor = preferences.edit();


        Log.d("tag", "check1");

        if (phoneNumber.length() == 0) {

            e1.setError("must required");
        } else {
            if (phoneNumber.length() == 11) {
                phoneNumber = "+92" + phoneNumber.substring(1, 11);
            }
            Log.d("tag", "check2");
            editor.putString("note", phoneNumber);
            editor.apply();

            try {
//
//                    progressDialog.setTitle("Authenticating.....");
//                    progressDialog.setCanceledOnTouchOutside(false);
//                    progressDialog.show();

                dialogBuilder = new AlertDialog.Builder(Authintication_PhoneNumber.this);
                View layoutView = getLayoutInflater().inflate(R.layout.progress_authentication, null);
//                Button dialogButton = layoutView.findViewById(R.id.btnDialog);
                Phoneauthnumber = layoutView.findViewById(R.id.phnauthdig);
                Phoneauthnumber.setText("we've sent you verification code to\n " + phoneNumber);
                dialogBuilder.setView(layoutView);
                dialogBuilder.setCancelable(false);
                alertDialog = dialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                databaseReference.child("Users_Adda").orderByChild("phonenumber").equalTo(phoneNumber).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if (snapshot.exists()) {
                            alertDialog.dismiss();
                            Toast.makeText(Authintication_PhoneNumber.this, "Phone Number is already registered as a Terminal", Toast.LENGTH_LONG).show();

                        } else {

                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    phoneNumber,        // Phone number to verify
                                    60,                 // Timeout duration
                                    TimeUnit.SECONDS,   // Unit of timeout
                                    Authintication_PhoneNumber.this,               // Activity (for callback binding)
                                    mCallbacks);


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                dialogButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        alertDialog.dismiss();
//                    }
//                });


            } catch (Exception e) {
                Log.d("tag", e.getMessage());
            }


            Log.d("tag", "check3");
        }


    }


    public void authinticating() {


        final AlertDialog.Builder dialogBuilder = new
                AlertDialog.Builder(Authintication_PhoneNumber.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.otp_layout, null);
        Button button = customView.findViewById(R.id.verify);
        e2 = customView.findViewById(R.id.pin_view);

        dialogBuilder.setCancelable(false);

        dialogBuilder.setView(customView);
        final AlertDialog dialog = dialogBuilder.show();
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onStart();

//                        Intent intent=new Intent(Authintication_PhoneNumber.this,
//                                Registration.class);
//                        startActivity(intent);

                String varificatiocode = e2.getText().toString();
                if (e2.getText().toString().length() == 0) {
                    e2.setError("must required");
                } else {

                    Log.d("tag", varificatiocode);


                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, varificatiocode);
                    signInWithPhoneAuthCredential(credential);
                    dialog.dismiss();

                }


            }
        });


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            databaseReference.child("Users").orderByChild("phonenumber").equalTo(phoneNumber).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.exists()) {
                                        Intent intent = new Intent(Authintication_PhoneNumber.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {

                                        Intent intent = new Intent(Authintication_PhoneNumber.this, Registration.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        } else {

                        }
                    }
                });
    }



}
