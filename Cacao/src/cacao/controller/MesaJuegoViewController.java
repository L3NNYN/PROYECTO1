/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.controller;

import cacao.functions.Jugador;
import cacao.functions.Partida;
import cacao.util.SocketServices;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
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

    @FXML
    private JFXButton btnCentrar;
    @FXML
    private JFXButton btnDerecha;
    @FXML
    private JFXButton btnIzquierda;

    private int fla = 0;

    private int clm = 0;

    private int rot = 0;

    //Jugador pricipal
    private Partida p;

    //Componentes del jugador 1
    @FXML
    private VBox vbContenedorJ1;
    @FXML
    private Text txtNombreJ1;
    @FXML
    private GridPane vbJugador1;

    private static String nombre;

    private static LocalDate edad;

    private static String color;

    //Componentes del jugador 2
    @FXML
    private VBox vbContenedorJ2;
    @FXML
    private Text txtNombreJ2;
    @FXML
    private GridPane vbJugador2;

    //Componenetes del jugador 3
    @FXML
    private VBox vbContenedorJ3;
    @FXML
    private Text txtNombreJ3;
    @FXML
    private GridPane vbJugador3;

    //Componetes del jugador 4
    @FXML
    private VBox vbContenedorJ4;
    @FXML
    private Text txtNombreJ4;
    @FXML
    private GridPane vbJugador4;

    //Componentes losetas disponibles
    /**
     * Initializes the controller class.
     */
    public MesaJuegoViewController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        c.addObserver(this);
        agregarVbox();
        vbContenedorJ1.setVisible(false);
        vbContenedorJ2.setVisible(false);
        vbContenedorJ3.setVisible(false);
        vbContenedorJ4.setVisible(false);
        p = new Partida();

    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onActionEnviar(ActionEvent event) throws IOException {

        /*
        Jugador jg = new Jugador(nombreJ, 88, txtMensaje.getText());
        Gson g = new Gson();
        String r = g.toJson(jg);

        c.enviarDatos(r); 
         */
        Jugador jg = new Jugador(nombre, edad, color);
        p.agregarJugador(jg);
        //System.out.print(p.getJugadores().get(0).getColor());
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
        Partida obj2 = gs.fromJson(llegada, Partida.class);

        if ("Jugadores".equals(obj2.getPeticion())) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    int tm = obj2.getParticipantes().size();

                    if (tm == 1) {
                        vbContenedorJ1.setVisible(true);
                    } else if (tm == 2) {
                        vbContenedorJ1.setVisible(true);
                        vbContenedorJ2.setVisible(true);
                    } else if (tm == 3) {
                        vbContenedorJ1.setVisible(true);
                        vbContenedorJ2.setVisible(true);
                        vbContenedorJ3.setVisible(true);
                    } else if (tm == 4) {
                        vbContenedorJ1.setVisible(true);
                        vbContenedorJ2.setVisible(true);
                        vbContenedorJ3.setVisible(true);
                        vbContenedorJ4.setVisible(true);
                    }

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
                        int fila = GridPane.getRowIndex((ImageView) node).intValue();
                        int columna = GridPane.getColumnIndex((ImageView) node).intValue();

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

                gpMatrizJuego.add(matrizVbox[i][j], j, i);
                final int fl = i;
                final int cl = j;

                matrizVbox[i][j].addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        agregarImagen(fl, cl, gpMatrizJuego);
                        Image im = new Image("/cacao/resources/font2.png", 88, 88, false, true);
                        ImageView im2 = new ImageView(im);
                        fla = fl;
                        clm = cl;
                        if (matrizVbox[fl][cl].getChildren().size() > 0) {
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

    public void setDatos(String nombre, LocalDate edad, String color) {

        MesaJuegoViewController.nombre = nombre;
        MesaJuegoViewController.edad = edad;
        MesaJuegoViewController.color = color;

    }

    @FXML
    private void onActionCentrar(ActionEvent event) {

    }

    @FXML
    private void onActionDerecha(ActionEvent event) {
        //System.out.print("Derecha");
        //System.out.println("PRESSED: " + fla + " " + clm);
        agregarImagen(fla, clm, gpMatrizJuego);

        if (rot == 0) {
            rot = 90;
        } else if (rot == 90) {
            rot = 180;
        } else if (rot == 180) {
            rot = 270;
        } else if (rot == 270) {
            rot = 0;
        }

        matrizVbox[fla][clm].getChildren().get(0).setRotate(rot);

        gpMatrizJuego.add(matrizVbox[fla][clm], clm, fla);

    }

    @FXML
    private void onActionIzquierda(ActionEvent event) {

        agregarImagen(fla, clm, gpMatrizJuego);

        if (rot == 0) {
            rot = 270;
        } else if (rot == 90) {
            rot = 0;
        } else if (rot == 180) {
            rot = 90;
        } else if (rot == 270) {
            rot = 180;
        }

        matrizVbox[fla][clm].getChildren().get(0).setRotate(rot);

        gpMatrizJuego.add(matrizVbox[fla][clm], clm, fla);

    }

    private void registrarJugador() throws IOException {

        //Jugador jg = new Jugador(txtNombreJ1.getText(), color);
        //Gson g = new Gson();
        //String r = g.toJson(jg);
        //c.enviarDatos(r);
    }
}
