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
import com.google.gson.Gson;
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

public class SocketServices extends Observable implements Runnable {

    private int puerto;

    private String HOST;

    private static Socket sc;

    private ArrayList<Socket> clientes;

    private Juego j;

    public boolean ev = false;

    public SocketServices() {

    }

    public SocketServices(int puerto, String ip) throws IOException {
        this.puerto = puerto;
        this.HOST = ip;
    }

    public void registrar(String host, int puerto) throws IOException {
        //Creo el socket para conectarme con el cliente
        sc = new Socket(host, 5000);

    }

    public Juego getRespuesta() {

        return j;

    }

    @Override
    public void run() {
//Juego obj2 = null;
        DataInputStream dis;
        DataOutputStream out;
        try {

            dis = new DataInputStream(sc.getInputStream());
            out = new DataOutputStream(sc.getOutputStream());
            String mensaje;
            double valor;
            while (true) {
                
                //System.out.print("Dentro a respuesta");
                mensaje = dis.readUTF();

                Gson gs = new Gson();
                j = gs.fromJson(mensaje, Juego.class);
                //System.out.print(j.getNombre()+": "+j.getMensaje()+"\n");

                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();
            }

        } catch (IOException ex) {
            Logger.getLogger(SocketServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarDatos(String mensaje) throws IOException {
        DataOutputStream dos = new DataOutputStream(sc.getOutputStream());
        //System.out.print(mensaje);
        dos.writeUTF(mensaje);
    }

}
