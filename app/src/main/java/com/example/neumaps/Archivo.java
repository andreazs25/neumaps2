package com.example.neumaps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;

public class Archivo extends AppCompatActivity {
    private ListView lv1;
    private ImageView iv1;
    private String[] archivos;
    private ArrayAdapter<String> adaptador1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivo);
        File dir=getExternalFilesDir(null);
        archivos=dir.list();
        adaptador1=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,archivos);
        lv1=(ListView)findViewById(R.id.listwiew);
        lv1.setAdapter(adaptador1);
        iv1=(ImageView)findViewById(R.id.imageView);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bitmap bitmap1= BitmapFactory.decodeFile(getExternalFilesDir(null)+"/"+archivos[position]);
                iv1.setImageBitmap(bitmap1);
            }
        });
    }
}
