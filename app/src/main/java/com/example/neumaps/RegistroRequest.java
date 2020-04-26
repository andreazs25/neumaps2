package com.example.neumaps;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroRequest  extends StringRequest {
    //"http://localhost/androidphp/phpproyecto/registro.php";
    private static  final String ruta="http://localhost/Neumaps/registro.php";
    private Map<String,String>parametros;

    public RegistroRequest(String email, String clave, String nombre,int edad,int telefono ,String dire,String provincia,boolean medico,Response.Listener<String> listener) {
        super(Method.POST, ruta, listener,null);
        parametros=new HashMap<>();
        parametros.put("email",email+"");
        parametros.put("clave",clave+"");
        parametros.put("nombre",nombre+"");
        parametros.put("edad",edad+"");
        parametros.put("telefono",telefono+"");
        parametros.put("direccion",dire+"");
        parametros.put("provincia",provincia+"");
        parametros.put("medico",medico+"");
    }
    protected Map<String,String> getParametros(){
        return parametros;
    }
}
