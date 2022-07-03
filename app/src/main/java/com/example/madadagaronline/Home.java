package com.example.madadagaronline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madadagaronline.Adapter.ServicesAdopter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {
   RecyclerView servicereclr;
   CardView curntjobs,pendingjobs;
   CircleImageView circleImageView;
   TextView name;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        dr= FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_home);
        name=findViewById(R.id.nmes);
        circleImageView=findViewById(R.id.profileids);
        curntjobs=findViewById(R.id.completeord);
        pendingjobs=findViewById(R.id.wihtdrword);
        servicereclr=findViewById(R.id.servirelr);
        servicereclr.setLayoutManager(new LinearLayoutManager(this));
        // Toast.makeText(Surah_1.this, ""+surah_model.ayats.size(), Toast.LENGTH_SHORT).show();
        ServicesAdopter servicesAdopter=new ServicesAdopter(this, localdata.screenItems);
        servicereclr.setAdapter(servicesAdopter);

        curntjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,CurrentJobs.class);
                startActivity(intent);
            }
        });
        pendingjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,PendingJobs.class);
                startActivity(intent);
            }
        });
        dr.child("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    name.setText(snapshot.child("first_name").getValue().toString());
                }
                if(snapshot.child("image").exists()){
                    Picasso.get().load(snapshot.child("image").getValue().toString()).placeholder(R.drawable.ic_profile).into(circleImageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}