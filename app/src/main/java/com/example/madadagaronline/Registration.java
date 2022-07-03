package com.example.madadagaronline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.madadagaronline.Models.MyUser;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends AppCompatActivity {
    FloatingActionButton editpic;
    CircleImageView circleImageView;
    EditText firstname;
    EditText lastname;
    EditText location;
    EditText cnicno;
    TextView phonenumber;
    Button save;
    DatabaseReference dr;
    FirebaseAuth mAuth;
    FirebaseUser user;

    FirebaseStorage fs;
    StorageReference sr;
    Uri filepath;
    String downloodurl,number;
    ProgressDialog progressDialog ;
    String rating="",rater="",usernumber="0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setTitle("Profile");
        //   getSupportActionBar().setIcon(R.drawable.notificationpic);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#fe980f"));


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        number = preferences.getString("note", "");

        progressDialog= new ProgressDialog(Registration.this);
        editpic=findViewById(R.id.pfpic);
        circleImageView=findViewById(R.id.profileid);
        firstname=findViewById(R.id.ename);
        lastname=findViewById(R.id.lstname);
        phonenumber=findViewById(R.id.phonenumbers);
        location= findViewById(R.id.location_crrunt);
        cnicno=findViewById(R.id.cnic);


        phonenumber.setText(number);
        save=findViewById(R.id.savebtn);
        dr= FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        fs=FirebaseStorage.getInstance();
        sr=fs.getReference();

        if(user!=null){
            retrivedata();
        }
        else{
            Toast.makeText(Registration.this, "user not login...retrive", Toast.LENGTH_SHORT).show();
        }

        editpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseimgs();
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseimgs();
            }
        });




    }

    public void Goto_Home(View view) {

        if(firstname.getText().toString().length()==0)
        {
            firstname.setError("must required");
            return;
        }
        else if(lastname.getText().toString().length()==0)
        {
            lastname.setError("must required");
            return;

        }
        else
        {

            uploadimages();


        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==1&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null) {

            filepath = data.getData();
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                circleImageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Registration.this, "Something went wrong\n"+e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }

    public void choseimgs()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select image"),1);
    }
    public void uploadimages()
    {
        progressDialog.setTitle("uploading....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (filepath != null) {



            //final StorageReference stdf = sr.child("images/" + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID) + "/" + p);
            final StorageReference stdf = sr.child("images/" + user.getUid());
            UploadTask uploadTask = stdf.putFile(filepath);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,
                                Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return stdf.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        downloodurl = downloadUri.toString();
                        if (task.isSuccessful()) {

                            uploaddata();
                             // Picasso.get().load(downloodurl).into(prdimg);
                            Toast.makeText(Registration.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            uploaddata();
                            Toast.makeText(Registration.this, "error uploading image. " + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }

                }

            });


        }
        else
        {

            uploaddata();
        }

    }


    public void uploaddata()
    {

//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//
//
//                dr.child("tokens").child(user.getUid()).child("device_token").setValue(task.getResult());
//
//
//            }
//        });


        if(user!=null){
            MyUser myUser=new MyUser(firstname.getText().toString(),lastname.getText().toString(),
                    phonenumber.getText().toString(),cnicno.getText().toString(),location.getText().toString(),user.getUid(),"",downloodurl,"Active",usernumber);
                    dr.child("Users").child(user.getUid()).setValue(myUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("tag","tr1");
                if(rating.length()!=0)
                {
                    dr.child("Users").child(user.getUid()).child("rating").setValue(rating);
                    dr.child("Users").child(user.getUid()).child("rater").setValue(rater);
                }
                Log.d("tag","tr2");
                progressDialog.dismiss();

                Intent intent=new Intent(Registration.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Log.d("tag","tr3");
            }
        });
        }
        else{
            progressDialog.dismiss();
            Toast.makeText(Registration.this, "user not login...", Toast.LENGTH_SHORT).show();
        }


    }


    public void retrivedata()
    {
        dr.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child(user.getUid()).exists())
                {
                    MyUser myUser=new MyUser();
                    myUser=snapshot.child(user.getUid()).getValue(MyUser.class);
                    Picasso.get().load(myUser.getImage()).placeholder(R.drawable.man).into(circleImageView);
                    firstname.setText(myUser.getFirst_name());
                    lastname.setText(myUser.getLast_name());
                    phonenumber.setText(myUser.getPhonenumber());
                    location.setText(myUser.getLocation());
                    downloodurl=myUser.getImage();
                    usernumber=myUser.getUsernum();


                    if(snapshot.child("rating").exists())
                    {
                        rating=snapshot.child("rating").getValue().toString();
                        rater=snapshot.child("rater").getValue().toString();
                    }
                }
                else
                {
                    usernumber=String.valueOf(snapshot.getChildrenCount());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }

}
