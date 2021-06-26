/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.functions;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Pipo
 */
public class Validaciones {

    public void cartasUsables(Cartas[] cartasJungla, Cartas[] logicas, Partida partida, int jugadorActual) throws IOException {
        int j = 0;
        boolean b = false;
        for (int i = 0; i < cartasJungla.length; i++) {
            b = false;
            while (b == false) {
                if (j < 3) {
                    if (logicas[j] == null) {
                        if (i < 11) {
                            if (partida.getJugadores()[jugadorActual].getCartasJugador()[i] != null) {
                                logicas[j] = partida.getJugadores()[jugadorActual].getCartasJugador()[i];
                                partida.getJugadores()[jugadorActual].borrarCarta(i);
                                b = true;
                                j++;
                            } else {
                                b = true;
                            }
                        } else {
                            break;
                        }
                    } else {
                        j++;
                    }
                } else {
                    break;
                }
            }
        }

    }

    public void llenarCartasJungla(Cartas[] cartasJungla, Cartas[] logicasSelva, Partida partida) throws IOException {
        int j = 0;
        boolean b = false;
        for (int i = 0; i < cartasJungla.length; i++) {
            b = false;
            while (b == false) {
                if (j < 2) {
                    if (logicasSelva[j] == null) {
                        //System.out.print("Si hay nulas");
                        if (i < 28) {

                            if (cartasJungla[i] != null) {
                                logicasSelva[j] = cartasJungla[i];
                                partida.borrarCarta(i);
                                b = true;
                                j++;
                            } else {
                                b = true;
                            }
                        } else {
                            break;
                        }
                    } else {
                        j++;
                    }
                } else {
                    break;
                }
            }
        }

    }

  public Boolean validarCartaJugador(Cartas[][] m, String tipo, int x, int y){
        boolean permitir = true;
        
        ArrayList<Cartas> ady = new ArrayList<Cartas>();
        ady = getAdyacentes(m, x, y);
        
        int i = 0;
        boolean s = false;
        while(permitir && i < 9){
            if(i == 0 || i == 2 || i == 6 || i == 8){ //Si la carta es diagonal
                if(ady.get(i) != null){
                    s = true;
                    if(tipo.equals("Tbr")){
                        if(!ady.get(i).getTipo().equals(tipo)){ //Si existe, valida que no sea diferente del tipo
                            permitir = false;
                        }
                    } else if(!ady.get(i).getNombre().equals(tipo)){ //Si existe, valida que no sea diferente del tipo
                        permitir = false;
                    }
                }
            } else if(ady.get(i) != null){ //Carta en vertical/horizontal
                    s = true;
                    if(tipo.equals("Tbr")){
                        if(ady.get(i).getTipo().equals(tipo)){ //Si existe, valida que no sea diferente del tipo
                            permitir = false;
                        }
                    }else if( ady.get(i).getNombre().equals(tipo) ){ //Si existe, valida que no sea igual del tipo 
                    permitir = false;
                }
            }
            i++;
        }
        if(s && permitir ){
            return true;
        }
        return false;
    }
    
    public ArrayList<Cartas> getAdyacentes(Cartas[][] m, int x, int y){
        //Se obtienen las cartas adyacentes
        ArrayList<Cartas> ady = new ArrayList<Cartas>();
        ady.add(m[x-1][y-1]);
        ady.add(m[x-1][y]);
        ady.add(m[x-1][y+1]);
        ady.add(m[x][y-1]);
        ady.add(null); //Posicion del centro
        ady.add(m[x][y+1]);
        ady.add(m[x+1][y-1]);
        ady.add(m[x+1][y]);
        ady.add(m[x+1][y+1]);
        
        return ady;
    }

}
