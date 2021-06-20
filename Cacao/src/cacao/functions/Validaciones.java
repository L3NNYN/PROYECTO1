/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.functions;

import java.io.IOException;

/**
 *
 * @author Pipo
 */
public class Validaciones {
    
    
    public void cartasUsables(Cartas[] cartasJungla,Cartas[] logicas, Partida partida, int jugadorActual) throws IOException {
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
                                System.out.print("Vacio: " + i);
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
    
}
