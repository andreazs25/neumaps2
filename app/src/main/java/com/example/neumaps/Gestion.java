package com.example.neumaps;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gestion {

    private static Gestion miGestor;

    private Gestion() {

    }

    public synchronized static Gestion getSingletonInstance() {
        if (miGestor== null) {
            miGestor = new Gestion();
        }
        return miGestor;
    }
    public boolean esEmail(String correo) {
        // Patron de validar el email
        String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@"
                + "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
        Pattern pattern = Pattern.compile(emailPattern);
        if (correo != null) {
            Matcher matcher = pattern.matcher(correo);
            if (matcher.matches()) {
                return true;
            }

        }
        return false;
    }


    public boolean comprobacionBlancosRegistro(String EMAIL,String CLAVE,String NOMBRE,String DIRECCION,String PROVINCIA,Boolean MEDICO) {
        boolean incorrecto = false;

        if (EMAIL.equals("") || CLAVE.equals("") || NOMBRE.equals("") ||  DIRECCION.equals("") || PROVINCIA.equals("")|| MEDICO.equals("")) {
            incorrecto=true;
        }
        return  incorrecto;
    }

    public boolean comprobacionBlancosLogin(String EMAIL,String CLAVE) {
        boolean incorrecto = false;

        if (EMAIL.equals("") || CLAVE.equals("") ) {
            incorrecto=true;
        }
        return  incorrecto;
    }


     public boolean comprobacionFormato(String EMAIL){
        boolean incorrecto=false;
         if (!Gestion.getSingletonInstance().esEmail(EMAIL)) {
            // Toast.makeText(getApplicationContext(),
                   //  "formato incorrecto", Toast.LENGTH_SHORT).show();
            incorrecto=true;
         }
         return incorrecto;
    }
    public boolean comprobacionLongitud(String CLAVE) {
        boolean incorrecto=false;
        if (CLAVE.length() < 5) {
            //Toast.makeText(getApplicationContext(),
            //"la contraseña es muy corta minimo 6 caracteres", Toast.LENGTH_SHORT);
            incorrecto=true;
        }
        return  incorrecto;
    }
        public boolean comprobacionedad( int edad){
            boolean incorrecto=false;
                if(edad<0){
                incorrecto=true;
                }
                return incorrecto;
        }


    public boolean comprobaciontelefono(int telefono){
        boolean incorrecto=false;
        if(telefono<9){
            incorrecto=true;
        }
        return incorrecto;
        }

    }


