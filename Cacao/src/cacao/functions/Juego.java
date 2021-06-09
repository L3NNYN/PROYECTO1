/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.functions;

import java.io.Serializable;

/**
 *
 * @author Pipo
 */
public class Juego implements Serializable{
    
    private String nombre;
    
    private int edad;
    
    private String mensaje;
    public Juego(String jg, int e, String sms){
        this.nombre = jg;
        this.edad = e;
        this.mensaje = sms;
    }
    
    public void setNombre(String jg){
        this.nombre = jg;
    }
    
    public void setEdad(int e){
        this.edad = e;
    }
    
    public void setMensaje(String sms){
        this.mensaje = sms;
    }
    
    public String getNombre(){

        return nombre;
    }
    
    public int getEdad(){

        return edad;
    }
    
    public String getMensaje(){

        return mensaje;
    }
    
    
}
