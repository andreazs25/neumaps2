package com.example.neumaps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginR extends AppCompatActivity {

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
                Intent registro=new Intent(LoginR.this, RegistroN.class);
                LoginR.this.startActivity(registro);
                finish();
            }

        });
        //Al pulsar el botón Entrar
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creamos dos variables para guardar textos de entrada
                final String EMAIL = email.getText().toString();
                final String CLAVE = pass.getText().toString();
                //Enviamos al metodo Login las variables de entrada
                login(EMAIL,CLAVE);
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
    //Este metodo lo usamos para conectarnos a la BD y saber si el login es correcto
    private void login(final String email, final String password){
        //IP bridge entre Android y el PC para conectarnos a localhost
        String LOGIN_REQUEST_URL = "http://10.0.2.2/Neumaps/login.php";

        // JSON data
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("email", email);
            jsonObject.put("clave", password);
            System.out.println(jsonObject.toString());
        } catch (JSONException e){
            e.printStackTrace();
        }

        // Json request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                LOGIN_REQUEST_URL,
                jsonObject,
                new Response.Listener<JSONObject>(){
                    private static final String TAG = "Respuesta";
                    @Override
                    public void onResponse(JSONObject response){
                        try{
                            //Usa la respuesta JSONObject en este log
                            Log.d(TAG, response.getString("success"));
                            boolean success = response.getBoolean("success");
                            if (success) {
                                System.out.println("Logeado!!");
                                Intent i=new Intent (LoginR.this,Formulario.class);
                                LoginR.this.startActivity(i);
                            }
                        } catch (JSONException e) {
                            System.out.println("Error logging in");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                    Toast.makeText(LoginR.this, "Can't connect to Internet. Please check your connection.", Toast.LENGTH_LONG).show();
                }
                else if (error instanceof ServerError) {
                    Toast.makeText(LoginR.this, "Unable to login. Either the username or password is incorrect.", Toast.LENGTH_LONG).show();
                }
                else if (error instanceof ParseError) {
                    Toast.makeText(LoginR.this, "Parsing error. Please try again.", Toast.LENGTH_LONG).show();
                }
                else if (error instanceof NoConnectionError) {
                    Toast.makeText(LoginR.this, "Can't connect to internet. Please check your connection.", Toast.LENGTH_LONG).show();
                }
                else if (error instanceof TimeoutError) {
                    Toast.makeText(LoginR.this, "Connection timed out. Please check your internet connection.", Toast.LENGTH_LONG).show();
                }

                //Do other stuff if you want
                error.printStackTrace();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String,String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                3600,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}

