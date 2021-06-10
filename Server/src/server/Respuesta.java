/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.google.gson.Gson;
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
public class Respuesta extends Observable implements Runnable {

    private Socket sc;

    private DataInputStream in;

    private String recivido;
    
    //Constructor de Respuesta
    public Respuesta(Socket socket) throws IOException {
        this.sc = socket;
    }

    
    /*Se utiliza el Runnable para recivir peticiones de los clientes, el Runnable
    estara ejecutanse continuamente reciviendo peticiones.
    */
    
    @Override
    public void run() {

        try {
            //System.out.println("recibir Mensaje");

            //Se crea objeto de tipo Data Input para recivir peticiones
            in = new DataInputStream(sc.getInputStream());
            
            //While para recivir peticiones
            while (true) {

                String mensaje = in.readUTF();
                recivido = mensaje;
                //Se utiliza Gson para convertir el String recivido en un objeto
                Gson gs = new Gson();
                Juego obj2 = gs.fromJson(mensaje, Juego.class);
                System.out.print(obj2.getNombre()+": "+obj2.getMensaje()+"\n");
                
                //Se envian los cambios a todos los clientes conectados
                enviarInfo();
                
                //Se notofocan a los observadores
                this.setChanged();
                this.notifyObservers(recivido);
                this.clearChanged(); 
            }
        } catch (IOException ex) {
            Logger.getLogger(Respuesta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*Este metodo notificara todos los clientes conectados*/
    public void enviarInfo() {

        Server sv = new Server();

        for (Socket sock : sv.getClientes()) {

            try {
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                dos.writeUTF(recivido);
                //recivido = null;
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
