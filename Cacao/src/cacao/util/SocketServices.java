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

import cacao.functions.Juego;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SocketServices extends Observable implements Runnable{
    
private int puerto;

private String HOST;

private static Socket sc;

private ArrayList<Socket> clientes;

private DataInputStream dis;

private Juego j;

//private DataOutputStream dos;

public boolean ev = false;

    public SocketServices() {

    }

    public SocketServices(int puerto, String ip) {
        this.puerto = puerto;
        this.HOST = ip;
    }

    @Override
    public void run() {
        
        ObjectInputStream dis;
        DataOutputStream out;
        try {
            
            //Creo el socket para conectarme con el cliente
            sc = new Socket(HOST, 5000);
            dis = new ObjectInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            String nombre;
            double valor;
            
            while (true) {

                j = (Juego) dis.readObject();
                //System.out.print(nombre);
                System.out.print(j.getNombre());
                this.setChanged();
                this.notifyObservers(j);
                this.clearChanged();
                
            }

        } catch (IOException ex) {
            Logger.getLogger(SocketServices.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
        Logger.getLogger(SocketServices.class.getName()).log(Level.SEVERE, null, ex);
    }

    }
    
    public void enviarDatos(String dato) throws IOException{
        Juego jg = new Juego("Juan",98);
        ObjectOutputStream dos = new ObjectOutputStream(sc.getOutputStream());
        dos.writeObject(jg);
    }

}
