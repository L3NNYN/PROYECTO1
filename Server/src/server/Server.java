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
import jdk.net.Sockets;
import server.Juego.Cartas;
import server.Juego.Jugador;
import server.Juego.Partida;
import server.util.FlowController;

/**
 *
 * @author Pipo
 */
public class Server extends Observable implements Runnable, Initializable {

    private static ObservableList<Socket> clientes;
    private static Socket[] clientes2 = new Socket[4];

    private static ObservableList<Thread> sockets = FXCollections.observableArrayList();

    private Socket clientesC[] = new Socket[2];

    private int contador = 0;

    private int puerto;

    public Partida pr;

    public Server(int puerto) throws IOException {
        this.puerto = puerto;
        this.clientes = FXCollections.observableArrayList();
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
                    
                    clientes.add(sc);

                    System.out.println("Cliente conectado");

                    Respuesta rp = new Respuesta(clientes.get(clientes.size() - 1), i);

                    Thread t = new Thread(rp);
                    sockets.add(t);
                    sockets.get(sockets.size() - 1).start();
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
        sockets.remove(socket);
        clientes.remove(socket);
        System.out.print("Clientes: " + clientes.size());
    }

    public ObservableList<Socket> getClientes() {

        return clientes;
    }

    public Partida getPartida() {
        return pr;
    }

}
