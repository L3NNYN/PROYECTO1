/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.Juego;
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
import server.Partida;
import server.util.FlowController;
/**
 *
 * @author Pipo
 */
public class Server extends Observable implements Runnable, Initializable{

    private static ObservableList<Socket> clientes;

    private int puerto;
    
    public Partida pr;
    
    private Socket sc = null;
   
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

            //Siempre estara escuchando peticiones
            while (true) {

               //Espero a que un cliente se conecte
                sc = servidor.accept();
                
                clientes.add(sc);
                
                System.out.println("Cliente conectado");

                Respuesta rp = new Respuesta(sc);
                Thread t = new Thread(rp);
                t.start();
                System.out.println("Seguir");
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }    

    } 
    
    public ObservableList<Socket> getClientes(){
   
        return clientes; 
    }
 
    public Partida getPartida()
    {
      return pr;   
    }
         
    }
