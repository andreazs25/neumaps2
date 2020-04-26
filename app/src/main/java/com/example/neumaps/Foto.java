package com.example.neumaps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

public class Foto extends AppCompatActivity {
    private ImageView imagen1;
    private EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        imagen1=(ImageView)findViewById(R.id.imageView);
        et1=(EditText)findViewById(R.id.editText);
        //Button tmf=(Button) findViewById(R.id.button);

        Button rcf=(Button)findViewById(R.id.button2);
        rcf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null)+"/"+et1.getText().toString());
                imagen1.setImageBitmap(bitmap1);
                //recuperarFoto(v);
            }
        });
        Button ver=(Button)findViewById(R.id.button3);
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento1=new Intent(getApplicationContext(),Archivo.class);
                startActivity(intento1);
                //ver(v);,
            }
        });
    }
    public void tomarFoto(View v){
        Intent intento1=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File foto=new File(getExternalFilesDir(null),et1.getText().toString());
        Uri photoURI = FileProvider.getUriForFile(this.getApplicationContext(), this.getApplicationContext().getPackageName() + ".provider", foto);
        intento1.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        intento1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }
    public void recuperarFoto(View v) {
        Bitmap bitmap1 = BitmapFactory.decodeFile(getExternalFilesDir(null)+"/"+et1.getText().toString());
        imagen1.setImageBitmap(bitmap1);
    }


    public void ver (View v){
    Intent intento1=new Intent(this,Archivo.class);
    startActivity(intento1);
    }


}
