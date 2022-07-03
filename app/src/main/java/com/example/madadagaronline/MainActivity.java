package com.example.madadagaronline;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
  FirebaseAuth mAuth;
  FirebaseUser user;
  DatabaseReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        dr=FirebaseDatabase.getInstance().getReference();
//        Intent intent = new Intent(MainActivity.this,Registration.class);
//        startActivity(intent);
    }

    @Override
    protected void onStart() {

        super.onStart();
        if(user==null){
            Intent intent = new Intent(MainActivity.this, com.example.madadagaronline.Authintication_PhoneNumber.class);
            startActivity(intent);
        }else {
            dr.child("Users").orderByChild("phonenumber").equalTo(user.getPhoneNumber()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {

                        Intent intent = new Intent(MainActivity.this, Registration.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}