/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Juego;

import java.net.Socket;
import java.util.ArrayList;
import static java.util.Collections.list;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pipo
 */
public class Partida {

    private ObservableList<Jugador> participantes = FXCollections.observableArrayList();

    private Cartas matrizLogica[][] = new Cartas[32][32];
    
    private ObservableList<Cartas> cartasJungla = FXCollections.observableArrayList();

    private Cartas cartaJugada;

    private String turnoJugador;

    private String peticion;

    private int x;

    private int y;

    public Partida() {

    }

    public void crearCartasJungla() {

        //Plantaciones simples
        for (int i = 0; i < 6; i++) {
            Cartas c1 = new Cartas("Pla", "Reyner", 1, 0);
            cartasJungla.add(c1);
        }

        for (int i = 0; i < 2; i++) {
            Cartas c1 = new Cartas("Plb", "Reyner", 2, 0);
            cartasJungla.add(c1);
        }

        //Mercados
        for (int i = 0; i < 4; i++) {
            Cartas c1 = new Cartas("Mcb", "Reyner", 3, 0);
            cartasJungla.add(c1);

            if (i < 2) {
                Cartas c2 = new Cartas("Mca", "Reyner", 3, 0);
                cartasJungla.add(c2);
            }
        }

        Cartas c1 = new Cartas("Mcc", "Reyner", 4, 0);
        cartasJungla.add(c1);

    }
    
     public void agregarJugador(Jugador jg){
        participantes.add(jg);   
    }
     
     public ObservableList<Jugador> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(ObservableList<Jugador> participantes) {
        this.participantes = participantes;
    }
    
    public Cartas[][] getMatrizLogica() {
        return matrizLogica;
    }

    public ObservableList<Cartas> getCartasJungla() {
        return cartasJungla;
    }

    public Cartas getCartaJugada() {
        return cartaJugada;
    }

    public String getTurnoJugador() {
        return turnoJugador;
    }

    public String getPeticion() {
        return peticion;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setMatrizLogica(Cartas[][] matrizLogica) {
        this.matrizLogica = matrizLogica;
    }

    public void setCartasJungla(ObservableList<Cartas> cartasJungla) {
        this.cartasJungla = cartasJungla;
    }

    public void setCartaJugada(Cartas cartaJugada) {
        this.cartaJugada = cartaJugada;
    }

    public void setTurnoJugador(String turnoJugador) {
        this.turnoJugador = turnoJugador;
    }

    public void setPeticion(String peticion) {
        this.peticion = peticion;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
