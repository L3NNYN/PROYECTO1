/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Juego.Juego;

/**
 *
 * @author Pipo
 */
public class Respuesta extends Observable implements Runnable{
   
    private Socket sc;
    
    private ObjectInputStream in;
    
    private Juego jg;
    
    public Respuesta(Socket socket) throws IOException{
        this.sc = socket;
        //recibirMensaje();
    }


    @Override
    public void run() {
 System.out.println("recibir Mensaje");
        while(true){
                
     try {
         in = new ObjectInputStream(sc.getInputStream());
         
         Juego jg = (Juego) in.readObject();

         System.out.println(jg.getNombre());
           
         //enviarInfo("dsdfsf");
         
                
     } catch (IOException ex) {
         Logger.getLogger(Respuesta.class.getName()).log(Level.SEVERE, null, ex);
     } catch (ClassNotFoundException ex) {
         Logger.getLogger(Respuesta.class.getName()).log(Level.SEVERE, null, ex);
     }

     }
   }
    
    public void enviarInfo() {
        
        Server sv = new Server();
        
        for (Socket sock : sv.getClientes()) {

            try {
                ObjectOutputStream dos = new ObjectOutputStream(sock.getOutputStream());
                dos.writeObject(jg);
                //enviarInfo(mensaje);
                //this.setChanged();
                //this.notifyObservers(jg);
                //this.clearChanged(); 
               
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    } 
     
}
