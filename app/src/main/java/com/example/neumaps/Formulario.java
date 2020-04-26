package com.example.neumaps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

public class Formulario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        getSupportActionBar().setTitle("ANTECEDENTES");
    }
}
