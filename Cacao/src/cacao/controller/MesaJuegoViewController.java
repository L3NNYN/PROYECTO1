/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.controller;

import cacao.util.SocketServices;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Pipo
 */
public class MesaJuegoViewController extends Controller implements Initializable,Observer {

    @FXML
    private JFXTextField txtMensaje;
    @FXML
    private JFXButton btnEnviar;

    private SocketServices sk;
    
    private SocketServices c;
    
    @FXML
    private JFXButton txtConectar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sk = new SocketServices();
    }    

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionEnviar(ActionEvent event) throws IOException {
        
        //SocketServices sk = new SocketServices();
        c.enviarDatos("Hola");
    }

    @FXML
    private void onActiomConectar(ActionEvent event) {
        c = new SocketServices(4040,"localhost");
        c.addObserver(this);
        Thread t = new Thread(c);
        t.start();
    }

    @Override
    public void update(Observable o, Object arg) {
         String nombre = (String) arg;
         System.out.print("Main: "+nombre);
    }
    
}
