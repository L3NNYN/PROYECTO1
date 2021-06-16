/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.functions;

import java.util.Random;

/**
 *
 * @author Pipo
 */
public class Partida{

    
    
    private Jugador jugadores[] = new Jugador[4];

    private Cartas matrizLogica[][] = new Cartas[32][32];
    
    private Cartas cartasJungla[] = new Cartas[28];

    private Cartas cartaJugada;

    private String turnoJugador;

    private String peticion;

    private int x;

    private int y;

    public Partida() {
     
    }

    public void crearCartasJungla() {

        
        for (int i = 0; i < 28; i++) {
           cartasJungla[i] = null; 
        }
        
        //Plantaciones simples
        for (int i = 0; i < 6; i++) {
            Cartas c1 = new Cartas("Pla", "Jungla", 1, 0);
            anadirCarta(c1);
        }

        for (int i = 0; i < 2; i++) {
            Cartas c1 = new Cartas("Plb", "Jungla", 2, 0);
            anadirCarta(c1);
        }

        //Mercados
        for (int i = 0; i < 4; i++) {
            Cartas c1 = new Cartas("Mcb", "Jungla", 3, 0);
            anadirCarta(c1);

            if (i < 2) {
                Cartas c2 = new Cartas("Mca", "Jungla", 3, 0);
                anadirCarta(c2);
            }
        }

        Cartas c1 = new Cartas("Mcc", "Jungla", 4, 0);
        anadirCarta(c1);

        Random r = new Random();

        for (int i = 0; i < cartasJungla.length; i++) {
            int posAleatoria = r.nextInt(cartasJungla.length);
            Cartas temp = cartasJungla[i];
            cartasJungla[i] = cartasJungla[posAleatoria];
            cartasJungla[posAleatoria] = temp;
        }
        
    }
    
    private void anadirCarta(Cartas carta){
        
        for (int i = 0; i < 28; i++) {
            if(cartasJungla[i] == null){
              cartasJungla[i] = carta;
              break;
            }
        }
        
    }
    
    public void agregarJugador(Jugador jugador){
        for(int i = 0; i < 4; i++){
            if(jugadores[i] == null){
              jugadores[i] = jugador;
              break;
            }
        }
    }
    
    public void iniciarArrays(){
        for (int i = 0; i < 28; i++) {
           cartasJungla[i] = null; 
        }
      
      for (int i = 0; i < 4; i++) {
           jugadores[i] = null; 
        }
    }
    
    public Cartas[] getCartasJungla() {
        return cartasJungla;
    }

    public void setCartasJungla(Cartas[] cartasJungla) {
        this.cartasJungla = cartasJungla;
    }
    
    public Jugador[] getJugadores() {
        return jugadores;
    }

    public void setJugadores(Jugador[] jugadores) {
        this.jugadores = jugadores;
    }
    
    public Cartas[][] getMatrizLogica() {
        return matrizLogica;
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
