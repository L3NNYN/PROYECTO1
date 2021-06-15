 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Juego;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Pipo
 */
public class Jugador{
    
    private String nombre;
    
    private LocalDate edad;
    
    private String mensaje;
    
    private String color;

    private static Cartas cartasJugador[] = new Cartas[11];
    
    private int frutos;
    
    private int fichasSol;
    
    private int monedas;
    
    public Jugador(String jg, LocalDate e, String color){
        this.nombre = jg;
        this.edad = e;
        this.color = color;
    }   
    
    public Jugador(){
        
    }
    
    public void crearCartas(){
      
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getEdad() {
        return edad;
    }

    public void setEdad(LocalDate edad) {
        this.edad = edad;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public int getFrutos() {
        return frutos;
    }

    public void setFrutos(int frutos) {
        this.frutos = frutos;
    }

    public int getFichasSol() {
        return fichasSol;
    }

    public void setFichasSol(int fichasSol) {
        this.fichasSol = fichasSol;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }  
  
    public static Cartas[] getCartasJugador() {
        return cartasJugador;
    }

    public static void setCartasJugador(Cartas[] cartasJugador) {
        Jugador.cartasJugador = cartasJugador;
    }
}
