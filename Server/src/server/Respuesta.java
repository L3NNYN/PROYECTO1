/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import server.Server;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import server.Juego.Partida;
import server.util.FlowController;

/**
 *
 * @author Pipo
 */
public class Respuesta extends Observable implements Runnable {

    private Socket sc;

    private DataInputStream in;

    private String recivido;

    //Constructor de Server
    public Server sv = new Server();

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

                String me = in.readUTF();

                Gson gs = new Gson();
                Partida jg = gs.fromJson(me, Partida.class);

                revisarPeticion(jg);

                //Se envian los cambios a todos los clientes conectados
                //enviarInfo();
                //Se notofocan a los observadores
                this.setChanged();
                this.notifyObservers();
                this.clearChanged();
            }
        } catch (IOException ex) {
            Logger.getLogger(Respuesta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void revisarPeticion(Partida partida) {

        if (partida.getPeticion().equals("registrar jugador")) {

            FlowController.getInstance().partida.agregarJugador(partida.getJugadores()[0]);
            FlowController.getInstance().partida.setPeticion("Jugadores");
            FlowController.getInstance().partida.setTurnoJugador(FlowController.getInstance().partida.getJugadores()[0].getNombre());
            Gson g = new Gson();
            String r = g.toJson(FlowController.getInstance().partida);
            enviarInfo(r);

        } else if (partida.getPeticion().equals("pasar turno")) {
            boolean escogido = false;
            for (int i = 0; i < cantidad(); i++) {
                if (!escogido) {
                    if (FlowController.getInstance().partida.getJugadores()[i].getNombre().equals(FlowController.getInstance().partida.getTurnoJugador())) {
                        if (i < cantidad() - 1) {
                            FlowController.getInstance().partida.setTurnoJugador(FlowController.getInstance().partida.getJugadores()[i+1].getNombre());
                            System.out.print("Turno nuevo: " + FlowController.getInstance().partida.getTurnoJugador());
                            escogido = true;
                        } else {
                            FlowController.getInstance().partida.setTurnoJugador(partida.getJugadores()[0].getNombre());
                        }
                    }
                }
            }
            System.out.print("Turno actual: " + FlowController.getInstance().partida.getTurnoJugador());
            FlowController.getInstance().partida.setPeticion("pasar turno");
            Gson g = new Gson();
            String r = g.toJson(FlowController.getInstance().partida);
            enviarInfo(r);

        } else if (partida.getPeticion().equals("actualizar cartas jugadores")) {

            for (int i = 0; i < 4; i++) {
                if (FlowController.getInstance().partida.getJugadores()[i] != null) {
                    FlowController.getInstance().partida.getJugadores()[i].setCartasJugador(partida.getJugadores()[i].getCartasJugador());
                }
            }
            int tm = 0;
            for (int i = 0; i < 11; i++) {
                if (FlowController.getInstance().partida.getJugadores()[0].getCartasJugador()[i] != null) {
                    tm++;
                }
            }
            FlowController.getInstance().partida.setPeticion("actualizar cartas jugadores");
            Gson g = new Gson();
            String r = g.toJson(FlowController.getInstance().partida);
            enviarInfo(r);

        } else if (partida.getPeticion().equals("colocar jugador")) {

        } else if (partida.getPeticion().equals("colocar selva")) {

        } else if (partida.getPeticion().equals("salir")) {

        } else if (partida.getPeticion().equals("ganador")) {

        } else if (partida.getPeticion().equals("actualizar")) {

        }

    }

    /*Este metodo notificara todos los clientes conectados*/
    public void enviarInfo(String peticion) {

        //Server sv = new Server();
        for (Socket sock : sv.getClientes()) {

            try {
                DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
                dos.writeUTF(peticion);
                //recivido = null;
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private int cantidad() {

        int num = 0;

        for (int i = 0; i < FlowController.getInstance().partida.getJugadores().length; i++) {
            if (FlowController.getInstance().partida.getJugadores()[i] != null) {
                num++;
            }
        }
        return num;
    }

}
