package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import server.Server;

public class Client {
    
    private static Server s;
     
    public static void main(String[] args) throws IOException {
        //Se crea un hilo para iniciar el server, se envian el puerto en el que va a trabajar
        s = new Server(5000);
        Thread t = new Thread(s);
        t.start();
    }
}
