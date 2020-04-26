package com.example.neumaps;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

       // "http://localhost/androidphp/phpproyecto/login.php" Login
    private static  final String ruta="http://localhost/Neumaps/login.php";
    private Map<String,String> parametros;

    public LoginRequest( String email,String clave, Response.Listener<String> listener) {
        super(Request.Method.POST, ruta, listener, null);
        parametros=new HashMap<>();
        parametros.put("email",email+"");
        parametros.put("clave",clave+"");
    }
    protected Map<String,String> getParametros(){
        return parametros;
    }

}
