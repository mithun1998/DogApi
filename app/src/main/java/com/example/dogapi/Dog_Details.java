package com.example.dogapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Dog_Details extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dog_activity);

        getScdIntent();
    }

    private void getScdIntent(){
        if(getIntent().hasExtra("name")){
            String nameString = getIntent().getStringExtra("name");
            String lifeString = getIntent().getStringExtra("life");
            String temperamentString = getIntent().getStringExtra("temperament");
            String originString = getIntent().getStringExtra("origin");

            initTxt(nameString,lifeString,temperamentString,originString);



        }
    }

    private void initTxt(String name, String life, String temperament, String origin){
        TextView nameTxtV=findViewById(R.id.dog_name);
        nameTxtV.setText(name);

        TextView priceTxtV=findViewById(R.id.dog_life);
        priceTxtV.setText(life);

        TextView categorieTxtV=findViewById(R.id.dog_temperament);
        categorieTxtV.setText(temperament);

        TextView styleTxtV=findViewById(R.id.dog_origin);
        styleTxtV.setText(origin);

    }
}
