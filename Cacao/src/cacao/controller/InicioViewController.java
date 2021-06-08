/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.controller;

import cacao.util.FlowController;
import cacao.util.Mensajes;
import cacao.util.SocketServices;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.net.*;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Pipo
 */
public class InicioViewController extends Controller implements Initializable, Observer{

    @FXML
    private JFXTextField txtIppartida;
    @FXML
    private JFXButton btnComenzar;
    @FXML
    private JFXSpinner spSpinner;
    @FXML
    private JFXTextField txtPuerto;
    @FXML
    private JFXTextField txtNickName;
    
    Mensajes sms;
    
    SocketServices conexion;
    
    @FXML
    private JFXComboBox<String> cbColores;

    @FXML
    private AnchorPane apColorSeleccionado;

    /**
     * Initializes the controller class.
     */
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       anadirColores();
       spSpinner.setVisible(false);
       sms = new Mensajes();
    }    

    @Override
    public void initialize() {

    }
    
    
    
    @FXML
    private void onActionComenzar(ActionEvent event) throws IOException, ClassNotFoundException {
        String datos = "";
        if(("".equals(txtIppartida.getText()) || ("".equals(txtPuerto.getText())) || ("".equals(txtNickName.getText())))){
            boolean primero = false;
            if(txtIppartida.getText().equals("")){
                if(primero == false){
                    datos += "Ip partida";
                    primero = true;
                }else{
                    datos += "," + "Ip partida";
                }
            }
            if(txtPuerto.getText().equals("")){
                if(primero == false){
                    datos += "Puerto";
                    primero = true;
                }else{
                    datos += "," + "Puerto";
                }
            }
            
            if(txtNickName.getText().equals("")){
                if(primero == false){
                    datos += "NickName";
                    primero = true;
                }else{
                    datos += "," + "NickName";
                }
            }
            sms.show(Alert.AlertType.ERROR, "Error", "Faltan datos de ingresar: "+datos);
            datos = "";
        } else {

          
            spSpinner.setVisible(true);  
            FlowController.getInstance().goViewInNewStage("MesaJuegoView", stage);
        }
       
    }

    @FXML
    private void onActionColores(ActionEvent event) {
        
              apColorSeleccionado.getStyleClass().clear();
                      
        if(null != cbColores.getSelectionModel().getSelectedItem())switch (cbColores.getSelectionModel().getSelectedItem()) {
            case "Rojo":
              apColorSeleccionado.getStyleClass().add("rojo");
                break;
            case "Azul":
              apColorSeleccionado.getStyleClass().add("azul");
                break;
            case "Amarillo":
              apColorSeleccionado.getStyleClass().add("amarillo");
                break;
            case "Verde":
              apColorSeleccionado.getStyleClass().add("verde");
                break;
            case "Celeste":
              apColorSeleccionado.getStyleClass().add("celeste");
                break;
            case "Morado":
              apColorSeleccionado.getStyleClass().add("morado");
                break;
            case "Rosado":
              apColorSeleccionado.getStyleClass().add("rosado");
                break;
            case "Turqueza":
              apColorSeleccionado.getStyleClass().add("turqueza");
                break;
            case "Cian":
              apColorSeleccionado.getStyleClass().add("cian");
                break;
            case "Gris":
              apColorSeleccionado.getStyleClass().add("gris");
                break;
            case "Negro":
              apColorSeleccionado.getStyleClass().add("negro");
                break;
            case "Marron":
              apColorSeleccionado.getStyleClass().add("marron");
                break;
            case "Anaranjado":
              apColorSeleccionado.getStyleClass().add("naranja");
                break;
            case "Magenta":
              apColorSeleccionado.getStyleClass().add("magenta");
                break;
            case "Verde Oscuro":
              apColorSeleccionado.getStyleClass().add("verdeOscuro");
                break;
            case "Violeta":
              apColorSeleccionado.getStyleClass().add("violeta");
                break;
            case "Aguamarina":
              apColorSeleccionado.getStyleClass().add("aguamarina");
                break;
            case "Dorado":
              apColorSeleccionado.getStyleClass().add("dorado");
                break;
            case "Fucsia":
              apColorSeleccionado.getStyleClass().add("fucsia");
                break;
            case "Lima":
              apColorSeleccionado.getStyleClass().add("lima");
                break;
            default:
                break;
        }
        
    }
    
    private void anadirColores(){
        cbColores.getItems().add("Rojo");
        cbColores.getItems().add("Azul");
        cbColores.getItems().add("Amarillo");
        cbColores.getItems().add("Verde");
        cbColores.getItems().add("Celeste");
        cbColores.getItems().add("Morado");
        cbColores.getItems().add("Rosado");
        cbColores.getItems().add("Turqueza");
        cbColores.getItems().add("Cian");
        cbColores.getItems().add("Gris");
        cbColores.getItems().add("Negro");
        cbColores.getItems().add("Marron");
        cbColores.getItems().add("Anaranjado");
        cbColores.getItems().add("Magenta");
        cbColores.getItems().add("Verde Oscuro");
        cbColores.getItems().add("Violeta");
        cbColores.getItems().add("Aguamarina");
        cbColores.getItems().add("Dorado");
        cbColores.getItems().add("Fucsia");
        cbColores.getItems().add("Lima");
    }


    @Override
    public void update(Observable o, Object arg) {

    }
    
}
