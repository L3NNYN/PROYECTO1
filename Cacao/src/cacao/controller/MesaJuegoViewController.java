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
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Pipo
 */
public class MesaJuegoViewController extends Controller implements Initializable, Observer {

    //Componentes interfaz principal
    @FXML
    private JFXTextField txtMensaje;
    @FXML
    private JFXButton btnEnviar;
    @FXML
    private JFXButton txtConectar;
    @FXML
    private VBox vbImagen;

    public static String nombreJ;
    @FXML
    private ScrollPane scScroll;
    @FXML
    private AnchorPane paneMesaJuego;

    //Componentes Socket Service
    private SocketServices sk = new SocketServices();

    private static SocketServices c = new SocketServices();

    //Componentes de matriz juego
    @FXML
    private GridPane gpMatrizJuego;

    private VBox matrizVbox[][] = new VBox[32][32];

    //Componentes del jugador 1
    @FXML
    private VBox vbContenedorJ1;
    @FXML
    private Text txtNombreJ1;
    @FXML
    private GridPane vbJugador1;

    //Componentes del jugador 2
    //Componenetes del jugador 3
    //Componetes del jugador 4
    //Componentes losetas disponibles
    /**
     * Initializes the controller class.
     */
    public MesaJuegoViewController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c.addObserver(this);
        //onPress();
        agregarVbox();
    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionEnviar(ActionEvent event) throws IOException {
        Juego jg = new Juego(nombreJ, 88, txtMensaje.getText());
        Gson g = new Gson();
        String r = g.toJson(jg);

        c.enviarDatos(r);
    }

    @FXML
    private void onActiomConectar(ActionEvent event) {
        agregarImg();
    }

    private void agregarImg() {
        Image im = new Image("/cacao/resources/font1.png", 60, 65, false, true);
        ImageView im2 = new ImageView(im);
        vbImagen.getChildren().add(im2);
    }

    @Override
    public void update(Observable o, Object arg) {
        String llegada = (String) arg;
        Gson gs = new Gson();
        Juego obj2 = gs.fromJson(llegada, Juego.class);

        System.out.print(obj2.getNombre() + ": " + obj2.getMensaje() + "\n");

        if ("Insertar".equals(obj2.getMensaje())) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    Image im = new Image("/cacao/resources/font1.png", 60, 65, false, true);
                    ImageView im2 = new ImageView(im);
                    vbImagen.getChildren().add(im2);

                }
            });
        }
    }

    private void onPress() {
        gpMatrizJuego.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                for (Node node : gpMatrizJuego.getChildren()) {
                    if (e.getTarget().equals((ImageView) node)) {
                        int fila = GridPane.getRowIndex((ImageView)node).intValue();
                        int columna = GridPane.getColumnIndex((ImageView)node).intValue();

                        System.out.print("Fila: " + fila + " Columna: " + columna);
                    }
                }
            }
        });
    }

    private void agregarVbox() {
        for (int i = 0; i <= 31; i++) {
            for (int j = 0; j <= 31; j++) {

                matrizVbox[i][j] = new VBox();
                matrizVbox[i][j].setPrefHeight(73);
                matrizVbox[i][j].setPrefWidth(90);
                //Image im = new Image("/cacao/resources/prueba.png", 90, 73, false, true);
                //ImageView im2 = new ImageView(im);
                //matrizVbox[i][j].getChildren().add(im2);

                gpMatrizJuego.add(matrizVbox[i][j], j, i);
                final int fl = i;
                final int cl = j;

                matrizVbox[i][j].addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        System.out.println("PRESSED: " + fl + " " + cl);
                        agregarImagen(fl,cl,gpMatrizJuego);
                        Image im = new Image("/cacao/resources/prueba.png", 90, 73, false, true);
                        ImageView im2 = new ImageView(im);
                        
                        if(matrizVbox[fl][cl].getChildren().size() > 0){
                        matrizVbox[fl][cl].getChildren().remove(0); 
                        }
                       
                        matrizVbox[fl][cl].getChildren().add(im2);
                        gpMatrizJuego.add(matrizVbox[fl][cl], cl, fl);
                       
                    }
                });
            }
        }

    }

    public void agregarImagen(final int row, final int column, GridPane gridPane) {

        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (node instanceof VBox && gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                gridPane.getChildren().remove(node);
                break;
            }
        }

    }
    
    public SocketServices getSocket() {
        return c;
    }

    public void setNombre(String nombre) {
        this.nombreJ = nombre;
    }
}
