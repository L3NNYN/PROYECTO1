/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.util;

/**
 *
 * @author Pipo
 */
public class Variables {

    public static boolean cartaTrabajador;

    public static boolean cartaTablero;
    
    public static boolean cartaJungla;
    
    public static boolean num;

    public Variables() {

    }

    public boolean getCartaTrabajador() {
        return cartaTrabajador;
    }

    public void setCartaTrabajador(boolean cartaTrabajador) {
        Variables.cartaTrabajador = cartaTrabajador;
    }

    public boolean getCartaTablero() {
        return cartaTablero;
    }

    public void setCartaTablero(boolean cartaTablero) {
        Variables.cartaTablero = cartaTablero;
    }
    
    public boolean getCartaJungla() {
        return cartaJungla;
    }

    public void setCartaJungla(boolean cartaJungla) {
        Variables.cartaJungla = cartaJungla;
    }
    
    public boolean getNum() {
        return num;
    }

    public void setNum(boolean num) {
        Variables.num = num;
    }
}
