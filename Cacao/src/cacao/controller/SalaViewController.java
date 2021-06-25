/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Pipo
 */
public class SalaViewController extends Controller implements Initializable, Observer {

    @FXML
    private JFXButton btnComenzar;
    @FXML
    private Text txtJ1;
    @FXML
    private Text txtJ2;
    @FXML
    private Text txtJ3;
    @FXML
    private Text txtJ4;
    
    private static boolean pasar = true;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionComenzar(ActionEvent event) {
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.print("Iniciar");
        while(true){           
            while(pasar){
                
            }   
            System.out.print("paso");
        }        
    }   
    
     public static boolean isPasar() {
        return pasar;
    }

    public static void setPasar(boolean pasar) {
        SalaViewController.pasar = pasar;
    }
}
