package com.example.neumaps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class LoginN extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("LOGIN");
        /*HttpsURLConnection urlConnection= GeneradorConexionesSeguras.getInstance()
                .crearConexionSegura(this,"https://134.209.235.115/login.php");*/
       final EditText email=(EditText) findViewById(R.id.userName);
       final  EditText pass=(EditText)findViewById(R.id.passWord);
        Button entrar=(Button) findViewById(R.id.bLogin);
        Button registro=(Button)findViewById(R.id.bRegistro);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro=new Intent(LoginN.this, RegistroN.class);
                LoginN.this.startActivity(registro);
                finish();
            }

        });

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String EMAIL=email.getText().toString();
                final String CLAVE=pass.getText().toString();
                boolean icblancos= Gestion.getSingletonInstance().comprobacionBlancosLogin(EMAIL,CLAVE);
                boolean iclave= Gestion.getSingletonInstance().comprobacionLongitud(CLAVE);
                boolean icEmail= Gestion.getSingletonInstance().comprobacionFormato(EMAIL);
                System.out.println("prueb1");
                String ruta = "http://localhost/Neumaps/login.php";

                Response.Listener<String> r1 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response+"hola5555");
                        try{
                            JSONObject jsonRespuesta= new JSONObject(response);
                            System.out.println(jsonRespuesta.length()+"prueba2");
                            boolean ok=jsonRespuesta.getBoolean("success");
                            if(ok==true){
                                System.out.println("prueba3");
                                Intent i=new Intent (LoginN.this,Formulario.class);
                                LoginN.this.startActivity(i);

                            }else{
                                AlertDialog.Builder alerta =new AlertDialog.Builder(LoginN.this);
                                alerta.setMessage("Fallo en el login").setNegativeButton("reintentar",null).create().show();
                            }

                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                LoginRequest r=new LoginRequest(EMAIL,CLAVE,r1);
                RequestQueue cola= Volley.newRequestQueue(LoginN.this);
                cola.add(r);

                Intent i=new Intent (LoginN.this,Formulario.class);
                LoginN.this.startActivity(i);
            }
        });
    }
    public AlertDialog createSimpleDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("AVISO")
                .setMessage("El Mensaje para el usuario Hay campos vacios,debe llenar todos los campos")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Acciones
                                dialog.cancel();
                            }
                        });


        return builder.create();
    }
}
