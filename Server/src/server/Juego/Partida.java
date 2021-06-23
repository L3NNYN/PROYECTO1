/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Juego;

import java.util.Random;

/**
 *
 * @author Pipo
 */
public class Partida {

    private Jugador jugadores[] = new Jugador[4];

    private Cartas matrizLogica[][] = new Cartas[32][32];

    private Cartas cartasJungla[] = new Cartas[28];
    
    private Cartas cartasIniciales[] = new Cartas[2];

    private Cartas cartaJugada;

    private String turnoJugador;

    private String peticion;
    
    private String salir;

    private int x;

    private int y;
    
    private int mazo;

    public Partida() {

    }

    public void crearCartasJungla() {

        for (int i = 0; i < 28; i++) {
            cartasJungla[i] = null;
        }

        //Plantaciones simples
        for (int i = 0; i < 6; i++) {
            Cartas p1 = new Cartas("Pla", "Jungla", 1, 0);
            anadirCarta(p1);
        }

        for (int i = 0; i < 2; i++) {
            Cartas p2 = new Cartas("Pla", "Jungla", 2, 0);
            anadirCarta(p2);
        }

        //Mercados
        for (int i = 0; i < 4; i++) {
            Cartas m1 = new Cartas("Mca", "Jungla", 3, 0);
            anadirCarta(m1);

            if (i < 2) {
                Cartas m2 = new Cartas("Mca", "Jungla", 2, 0);
                anadirCarta(m2);
            }
            if (i < 1) {
                Cartas c1 = new Cartas("Mca", "Jungla", 4, 0);
                anadirCarta(c1);
            }
        }

        //Minas de oro, adoracion al sol, agua y templos
        for (int i = 0; i < 5; i++) {
            Cartas t1 = new Cartas("Tmp", "Jungla", 3, 6, 0);
            anadirCarta(t1);
            if (i < 3) {
                Cartas a1 = new Cartas("Ag", "Jungla", 0, 0);
                anadirCarta(a1);
            }
            if (i < 2) {
                Cartas m1 = new Cartas("Mns", "Jungla", 1, 0);
                anadirCarta(m1);
                Cartas ad1 = new Cartas("Ads", "Jungla", 0, 0);
                anadirCarta(ad1);
            }
            if (i < 1) {
                Cartas m2 = new Cartas("Mns", "Jungla", 2, 0);
                anadirCarta(m2);
            }
        }

        
        Random r = new Random();

        for (int i = 0; i < cartasJungla.length; i++) {
            int posAleatoria = r.nextInt(cartasJungla.length);
            Cartas temp = cartasJungla[i];
            cartasJungla[i] = cartasJungla[posAleatoria];
            cartasJungla[posAleatoria] = temp;
        }

        cartasIniciales[0] = cartasJungla[0];
        cartasJungla[0] = null;
        cartasIniciales[1] = cartasJungla[1];
        cartasJungla[1] = null;
    }

    public void borrarCarta(int carta) {
       this.cartasJungla[carta] = null;
    }

    private void anadirCarta(Cartas carta) {

        for (int i = 0; i < 28; i++) {
            if (cartasJungla[i] == null) {
                cartasJungla[i] = carta;
                break;
            }
        }

    }

    public void agregarJugador(Jugador jugador) {
        for (int i = 0; i < 4; i++) {
            //Inserta al final
            if (jugadores[i] == null) {
                jugadores[i] = jugador;
                break;
            }else if(jugador.getEdad().compareTo(jugadores[i].getEdad()) < 0 ){
                Jugador aux = null;
                Jugador aux2 = null; 
                aux = jugadores[i];
                jugadores[i] = jugador;
                //Mover todos los demás un campo
                int pos = i+1;
                while(pos < 4){
                    if(jugadores[pos] == null){
                        jugadores[pos] = aux;
                        pos = 4;
                    }else{
                        aux2 = jugadores[pos];
                        jugadores[pos] = aux;
                        aux = aux2;
                        pos++;
                    }
                }
                break;
            }
        }
    }

    public void iniciarArrays() {
        for (int i = 0; i < 28; i++) {
            cartasJungla[i] = null;
        }

        for (int i = 0; i < 4; i++) {
            jugadores[i] = null;
        }
    }

    public void agregarCarta(int x, int y, Cartas carta) {
        carta.setGrados(0);
        matrizLogica[x][y] = carta;
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

    public void setCartaJugada(Cartas cartaJugada, int x, int y, int mazo) {
        this.cartaJugada = cartaJugada;
        this.x = x;
        this.y = y;
        this.mazo = mazo;
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
    
    public int getMazo() {
        return mazo;
    }

    public void setMazo(int mazo) {
        this.mazo = mazo;
    }

     public Cartas[] getCartasIniciales() {
        return cartasIniciales;
    }

    public void setCartasIniciales(Cartas[] cartasIniciales) {
        this.cartasIniciales = cartasIniciales;
    }
    
    public String getSalir() {
        return salir;
    }

    public void setSalir(String salir) {
        this.salir = salir;
    }
}
