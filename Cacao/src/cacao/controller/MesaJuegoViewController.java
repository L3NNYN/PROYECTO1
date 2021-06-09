/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.controller;

import cacao.functions.Juego;
import cacao.util.SocketServices;
import com.google.gson.Gson;
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

    InicioViewController ini = new InicioViewController();
    
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
        c = new SocketServices();
    }    

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionEnviar(ActionEvent event) throws IOException {
        Juego jg = new Juego(ini.getNombre(),88,txtMensaje.getText());
        Gson g = new Gson();
        String r = g.toJson(jg);
        
        c.enviarDatos(r);
    }

    @FXML
    private void onActiomConectar(ActionEvent event) {
        c = new SocketServices(5000,"localhost");
        c.addObserver(this);
        Thread t = new Thread(c);
        t.start();
    }

    @Override
    public void update(Observable o, Object arg) {
         
    }
    
}
