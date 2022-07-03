package com.example.madadagaronline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Select_your_Option extends AppCompatActivity {
LinearLayout l1,l2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_your_option);
        l1=findViewById(R.id.becom_mazdoor);
        l2=findViewById(R.id.hire_mazdoor);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Select_your_Option.this, com.example.madadagaronline.Authintication_PhoneNumber.class);
                startActivity(intent);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Select_your_Option.this, com.example.madadagaronline.Authintication_PhoneNumber.class);
                startActivity(intent);
            }
        });
    }
}