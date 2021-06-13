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
public class Cartas {

   private String tipo;
   
   private String nombre;
    
   private String color;
   
   private int derecha;
   
   private int abajo;
   
   private int izquierda;
   
   private int arriba;
   
   private int valor;
   
   private int valor1Templo;
   
   private int valor2Templo;
   
   private int grados;
   
   //Losetas jungla
   public Cartas(String tipo, String nombre, int derecha, int abajo, int izquierda,
   int arriba,String color,int grados){
      this.tipo = tipo;
      this.nombre = nombre;
      this.derecha = derecha;
      this.abajo = abajo;
      this.izquierda = izquierda;
      this.arriba = arriba;
      this.grados = grados;
   }
   
   //Plantaciones, mercado, minas
   public Cartas(String tipo, String nombre, int valor, int grados){
       this.tipo = tipo;
       this.nombre = nombre;
       this.valor = valor;      
       this.grados = grados;
   }
   
   //Templos 
   public Cartas(String tipo, String nombre, int valorT1, int valorT2, int grados){
       this.tipo = tipo;
       this.nombre = nombre;
       this.valor1Templo = valorT1;
       this.grados = grados;
   }
   
   //Agua
   public Cartas(String tipo, String nombre, int grados){
     this.tipo = tipo;
     this.nombre = nombre;
     this.grados = grados;
   }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDerecha() {
        return derecha;
    }

    public void setDerecha(int derecha) {
        this.derecha = derecha;
    }

    public int getAbajo() {
        return abajo;
    }

    public void setAbajo(int abajo) {
        this.abajo = abajo;
    }

    public int getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(int izquierda) {
        this.izquierda = izquierda;
    }

    public int getArriba() {
        return arriba;
    }

    public void setArriba(int arriba) {
        this.arriba = arriba;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getValor1Templo() {
        return valor1Templo;
    }

    public void setValor1Templo(int valor1Templo) {
        this.valor1Templo = valor1Templo;
    }

    public int getValor2Templo() {
        return valor2Templo;
    }

    public void setValor2Templo(int valor2Templo) {
        this.valor2Templo = valor2Templo;
    }
   
   
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
}

