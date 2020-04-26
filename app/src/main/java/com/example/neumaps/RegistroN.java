package com.example.neumaps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class RegistroN extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().setTitle("REGISTRO");
        HttpsURLConnection urlConnection= GeneradorConexionesSeguras.getInstance()
                .crearConexionSegura(this,"https://134.209.235.115/registro.php");
        final EditText emailT=(EditText)findViewById(R.id.email);
        final EditText clave=(EditText)findViewById(R.id.pss);
        final EditText nombre=(EditText)findViewById(R.id.nombreape);
        final EditText edad=(EditText)findViewById(R.id.edad);
        final EditText telefono=(EditText)findViewById(R.id.tele);
        final EditText direccion=(EditText)findViewById(R.id.direccion);
        final EditText provincia=(EditText)findViewById(R.id.provin);
        final CheckBox medico=(CheckBox) findViewById(R.id.cbMedico);
        Button btnRegistro=(Button)findViewById(R.id.btnGuardar);
        Button btnVolver=(Button)findViewById(R.id.volver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent (RegistroN.this, LoginN.class);
                RegistroN.this.startActivity(i);
            }
        });

        //Al hacer click en registro si todo es correcto usamos RegistroRequest para conectarnos a la BD
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EMAIL=emailT.getText().toString();
                String CLAVE=clave.getText().toString();
                String NOMBRE=nombre.getText().toString();
                int  EDAD=Integer.parseInt(edad.getText().toString());
                int TELEFONO=Integer.parseInt(telefono.getText().toString());
                String DIRECCION=direccion.getText().toString();
                String PROVINCIA=provincia.getText().toString();
                boolean MEDICO=Boolean.parseBoolean(medico.getText().toString());
                boolean icblancos= Gestion.getSingletonInstance().comprobacionBlancosRegistro(EMAIL,CLAVE,NOMBRE,DIRECCION,PROVINCIA,MEDICO);
                boolean iclave= Gestion.getSingletonInstance().comprobacionLongitud(CLAVE);
                boolean icEmail= Gestion.getSingletonInstance().comprobacionFormato(EMAIL);
                boolean icEDAD= Gestion.getSingletonInstance().comprobacionedad(EDAD);
                boolean icTefefono= Gestion.getSingletonInstance().comprobaciontelefono(TELEFONO);
                if(icblancos){
                    createSimpleDialog().show();
                }else{
                    if(iclave){
                        Toast.makeText(getApplicationContext(),
                        "la contraseña es muy corta minimo 6 caracteres", Toast.LENGTH_SHORT);
                    }else{
                        if(icEmail){
                             Toast.makeText(getApplicationContext(),
                                     "formato incorrecto", Toast.LENGTH_SHORT).show();
                        }else{
                            if(icEDAD){
                                Toast.makeText(getApplicationContext(),
                                        "edad incorecta", Toast.LENGTH_SHORT).show();
                            }else{
                                if(icTefefono){
                                    Toast.makeText(getApplicationContext(),
                                            "telefono incorrecto minimo 9 digitos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }

                Response.Listener<String>respuesta=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRespuesta= new JSONObject(response);
                            boolean ok=jsonRespuesta.getBoolean("success");
                            if(ok==true){

                                Intent i=new Intent (RegistroN.this, LoginN.class);
                                RegistroN.this.startActivity(i);
                            }else{
                                AlertDialog.Builder alerta =new AlertDialog.Builder(RegistroN.this);
                                alerta.setMessage("Fallo en el registro").setNegativeButton("reintentar",null).create().show();
                            }

                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };
                 RegistroRequest r=new RegistroRequest(EMAIL,CLAVE,NOMBRE,EDAD,TELEFONO,DIRECCION,PROVINCIA,MEDICO,respuesta);
                RequestQueue cola= Volley.newRequestQueue(RegistroN.this);
                cola.add(r);
            }
        });

    }
    //En el caso de que haya campos vacios enviamos el aviso
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
