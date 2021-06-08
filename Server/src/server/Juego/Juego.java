/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Juego;

/**
 *
 * @author Pipo
 */
public class Juego {
    
    private String nombre;
    
    private int edad;
    
    public Juego(String jg, int e){
        this.nombre = jg;
        this.edad = e;
    }
    
    public void setNombre(String jg){
        this.nombre = jg;
    }
    
    public void setEdad(int e){
        this.edad = e;
    }
    
    public String getNombre(){

        return nombre;
    }
    
    public int getEdad(){

        return edad;
    }
    
    
}
