/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.controller;

import cacao.functions.Partida;
import cacao.functions.Variables;
import cacao.util.ClassController;
import cacao.util.FlowController;
import cacao.util.Mensajes;
import cacao.util.SocketServices;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import java.net.*;
import java.time.LocalDate;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Pipo
 */
public class InicioViewController extends Controller implements Initializable, Observer {

    MesaJuegoViewController mj = new MesaJuegoViewController();

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

    public static String nombre;

    private Mensajes sms;

    private static SocketServices conexion;

    @FXML
    private JFXComboBox<String> cbColores;

    @FXML
    private AnchorPane apColorSeleccionado;
    @FXML
    private JFXDatePicker dtEdadJugador;
    @FXML
    private Text txtJ1;
    @FXML
    private Text txtJ2;
    @FXML
    private Text txtJ3;
    @FXML
    private Text txtJ4;
    @FXML
    private AnchorPane apBienvenido;
    @FXML
    private AnchorPane apSalaEspera;
    @FXML
    private JFXButton btnlISTO;

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
    private void onActionComenzar(ActionEvent event) throws IOException, ClassNotFoundException, InterruptedException {
        String datos = "";
        if (("".equals(txtIppartida.getText()) || ("".equals(txtPuerto.getText())) || ("".equals(txtNickName.getText())))) {
            boolean primero = false;
            if (txtIppartida.getText().equals("")) {
                if (primero == false) {
                    datos += "Ip partida";
                    primero = true;
                } else {
                    datos += "," + "Ip partida";
                }
            }
            if (txtPuerto.getText().equals("")) {
                if (primero == false) {
                    datos += "Puerto";
                    primero = true;
                } else {
                    datos += "," + "Puerto";
                }
            }

            if (txtNickName.getText().equals("")) {
                if (primero == false) {
                    datos += "NickName";
                    primero = true;
                } else {
                    datos += "," + "NickName";
                }
            }
            sms.show(Alert.AlertType.ERROR, "Error", "Faltan datos de ingresar: " + datos);
            datos = "";
        } else {

            LocalDate edad = dtEdadJugador.getValue();

            //txtNickName.getText(),edad, cbColores.getSelectionModel().getSelectedItem()
            mj.getSocket().registrar(txtIppartida.getText(), 0);

            Thread t = new Thread(mj.getSocket());
            t.start();

            mj.enviarPeticion("actualizar jugadores");
            spSpinner.setVisible(true);
            TranslateTransition carta1 = new TranslateTransition();
            carta1.setNode((Node) apColorSeleccionado);
            carta1.setDelay(Duration.seconds(0.5));
            carta1.play();
            carta1.setOnFinished((ActionEvent e) -> {

                if (!"lleno".equals(ClassController.getInstance().partida.getPeticion())) {
                    int mn = 0;
                    boolean pasar = true;
                    for (int i = 0; i < ClassController.getInstance().partida.getJugadores().length; i++) {
                        if (ClassController.getInstance().partida.getJugadores()[i] != null) {
                            if (ClassController.getInstance().partida.getJugadores()[i].getNombre().equals(txtNickName.getText())
                                    || ClassController.getInstance().partida.getJugadores()[i].getColor().equals(cbColores.getSelectionModel().getSelectedItem())) {
                                pasar = false;
                            }
                        }
                    }

                    if (pasar) {
                        mj.setDatos(txtNickName.getText(), edad, cbColores.getSelectionModel().getSelectedItem());

                        FlowController.getInstance().goViewInNewStage("MesaJuegoView", stage);
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                spSpinner.setVisible(false);
                                new Mensajes().showModal(Alert.AlertType.ERROR, "Error", getStage(), "Usuario o color ya seleccionados");
                            }

                        });

                    }
                }else{
                  Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                spSpinner.setVisible(false);
                                new Mensajes().showModal(Alert.AlertType.ERROR, "Error", getStage(), "La partida esta llena");
                            }

                        });  
                }
            });
        }

    }

    @FXML
    private void onActionColores(ActionEvent event) {

        apColorSeleccionado.getStyleClass().clear();

        if (null != cbColores.getSelectionModel().getSelectedItem()) {
            switch (cbColores.getSelectionModel().getSelectedItem()) {
                case "Rojo":
                    apColorSeleccionado.getStyleClass().add("Rojo");
                    break;
                case "Azul":
                    apColorSeleccionado.getStyleClass().add("Azul");
                    break;
                case "Amarillo":
                    apColorSeleccionado.getStyleClass().add("Amarillo");
                    break;
                case "Verde":
                    apColorSeleccionado.getStyleClass().add("Verde");
                    break;
                case "Celeste":
                    apColorSeleccionado.getStyleClass().add("Celeste");
                    break;
                case "Morado":
                    apColorSeleccionado.getStyleClass().add("Morado");
                    break;
                case "Rosado":
                    apColorSeleccionado.getStyleClass().add("Rosado");
                    break;
                case "Turqueza":
                    apColorSeleccionado.getStyleClass().add("Turqueza");
                    break;
                case "Cian":
                    apColorSeleccionado.getStyleClass().add("Cian");
                    break;
                case "Gris":
                    apColorSeleccionado.getStyleClass().add("Gris");
                    break;
                case "Negro":
                    apColorSeleccionado.getStyleClass().add("Negro");
                    break;
                case "Marron":
                    apColorSeleccionado.getStyleClass().add("Marron");
                    break;
                case "Anaranjado":
                    apColorSeleccionado.getStyleClass().add("Naranja");
                    break;
                case "Magenta":
                    apColorSeleccionado.getStyleClass().add("Magenta");
                    break;
                case "Verde Oscuro":
                    apColorSeleccionado.getStyleClass().add("VerdeOscuro");
                    break;
                case "Violeta":
                    apColorSeleccionado.getStyleClass().add("Violeta");
                    break;
                case "Aguamarina":
                    apColorSeleccionado.getStyleClass().add("Aguamarina");
                    break;
                case "Dorado":
                    apColorSeleccionado.getStyleClass().add("Dorado");
                    break;
                case "Fucsia":
                    apColorSeleccionado.getStyleClass().add("Fucsia");
                    break;
                case "Lima":
                    apColorSeleccionado.getStyleClass().add("Lima");
                    break;
                default:
                    break;
            }
        }

    }

    private void anadirColores() {
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
        System.out.print("Dentro en Inicio");
    }

    public String getNombre() {
        return nombre;
    }

    public SocketServices getSocket() {

        return conexion;
    }

    @FXML
    private void onActionEdadJugador(ActionEvent event) {
    }

    @FXML
    private void onActionListo(ActionEvent event) {
    }

}
