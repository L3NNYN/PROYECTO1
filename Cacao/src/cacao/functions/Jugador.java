/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.functions;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Pipo
 */
public class Jugador {

    private String nombre;

    private LocalDate edad;

    private String mensaje;

    private String color;

    private Cartas cartasJugador[] = new Cartas[11];

    private int frutos;

    private int fichasSol;

    private int monedas;
    
    private String listo;

    public Jugador(String jg, LocalDate e, String color) {
        this.nombre = jg;
        this.edad = e;
        this.color = color;
        this.listo = "no";
    }

    public Jugador() {

    }

    public void crearCartas(String nombre, String Color) {
 
        //Tbr = trabajador
        for (int i = 0; i < 5; i++) {
            Cartas c1 = new Cartas("Tbr", nombre, 2, 1, 0, 1, color, 0);
            anadirCarta(c1);
            if (i < 4) {
                Cartas c2 = new Cartas("Tbr", nombre, 1, 1, 1, 1, color, 0);
                anadirCarta(c2);
            }
        }

        Cartas c3 = new Cartas("Tbr", nombre, 3, 0, 0, 1, color, 0);
        anadirCarta(c3);
        Cartas c4 = new Cartas("Tbr", nombre, 3, 1, 0, 0, color, 0);
        anadirCarta(c4);

        Random r = new Random();

        for (int i = 0; i < cartasJugador.length; i++) {
            int posAleatoria = r.nextInt(cartasJugador.length);
            Cartas temp = cartasJugador[i];
            cartasJugador[i] = cartasJugador[posAleatoria];
            cartasJugador[posAleatoria] = temp;
        }

    }

    private void anadirCarta(Cartas carta) {

        for (int i = 0; i < 11; i++) {
            if (cartasJugador[i] == null) {
                cartasJugador[i] = carta;
                break;
            }
        }

    }

    public void iniciarArrays() {
        for (int i = 0; i < 11; i++) {
            cartasJugador[i] = null;
        }
    }

    public void borrarCarta(int pos){
        this.cartasJugador[pos] = null;
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
    
    public String getColorCss() {
        String css = "#"+color;
        return css;
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

    public Cartas[] getCartasJugador() {
        return cartasJugador;
    }

    public void setCartasJugador(Cartas[] cartasJugador) {
        this.cartasJugador = cartasJugador;
    }
    
    public String getListo() {
        return listo;
    }

    public void setListo(String listo) {
        this.listo = listo;
    }

}
