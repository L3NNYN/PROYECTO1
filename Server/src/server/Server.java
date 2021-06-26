/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import server.Juego.Cartas;
import server.Juego.Jugador;
import server.Juego.Partida;
import server.util.FlowController;

/**
 *
 * @author Pipo
 */
public class Server extends Observable implements Runnable, Initializable {

    private static Socket[] clientes = new Socket[4];

    private static Thread[] sockets = new Thread[4];

    private Socket clientesC[] = new Socket[2];

    private int contador = 0;

    private int puerto;

    public Partida pr;

    public Server(int puerto) throws IOException {
        this.puerto = puerto;
        for (int i = 0; i < 4; i++) {
            clientes[i] = null;
        }
    }

    public Server() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FlowController.getInstance().partida.crearCartasJungla();
    }

    @Override
    public void run() {
        ServerSocket servidor = null;

        try {
            //Creamos el socket del servidor
            servidor = new ServerSocket(puerto);
            System.out.println("Servidor iniciado");
            FlowController.getInstance().partida.crearCartasJungla();
            //Siempre estara escuchando peticiones
            int i = 0;
            while (true) {

                //Espero a que un cliente se conecte
                Socket sc = new Socket();
                sc = servidor.accept();

                if (contador < 4) {

                    agregarCliente(sc);

                    Respuesta rp = new Respuesta(clientes[getNumCliente()], getNumCliente());

                    Thread t = new Thread(rp);
                    agregarThread(t);
                    sockets[getNumThread()].start();
                    i++;
                    contador++;
                } else {
                    DataOutputStream dos = new DataOutputStream(sc.getOutputStream());
                    FlowController.getInstance().partida.setPeticion("lleno");
                    Gson g = new Gson();
                    String r = g.toJson(FlowController.getInstance().partida);
                    dos.writeUTF(r);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void eliminarSocket(int socket) throws IOException {
        sockets[socket] = null;
        clientes[socket] = null;
    }

    public void agregarCliente(Socket sk) {

        for (int i = 0; i < 4; i++) {
            if (clientes[i] == null) {
                clientes[i] = sk;
                break;
            }
        }
    }

    public void agregarThread(Thread t) {

        for (int i = 0; i < 4; i++) {
            if (sockets[i] == null) {
                sockets[i] = t;
                break;
            }
        }
    }

    public int getNumCliente() {
        int salida = 0;
        boolean enc = false;
        for (int i = 0; i < 4; i++) {
            if (!enc) {
                if (clientes[i] != null) {
                    salida = i;
                } else {
                    enc = true;
                }
            }
        }
        return salida;
    }
    
    public int getNumThread() {
        int salida = 0;
        boolean enc = false;
        for (int i = 0; i < 4; i++) {
            if (!enc) {
                if (sockets[i] != null) {
                    salida = i;
                } else {
                    enc = true;
                }
            }
        }
        return salida;
    }

    public Socket[] getClientes() {

        return clientes;
    }

    public Partida getPartida() {
        return pr;
    }

}
