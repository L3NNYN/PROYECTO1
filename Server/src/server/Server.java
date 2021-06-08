/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Pipo
 */
public class Server extends Observable implements Runnable{

    private static ArrayList<Socket> clientes;

    private int puerto;
    
    private Socket sc = null;
   
    public Server(int puerto) throws IOException {
        this.puerto = puerto;
        this.clientes = new ArrayList();
    }

    public Server() {

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
    
    public ArrayList<Socket> getClientes(){
   
        return clientes; 
    }
    
}
