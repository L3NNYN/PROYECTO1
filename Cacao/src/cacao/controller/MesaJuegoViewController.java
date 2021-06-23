/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.controller;

import cacao.functions.AgregarImagen;
import cacao.functions.Cartas;
import cacao.functions.Jugador;
import cacao.functions.Partida;
import cacao.functions.Validaciones;
import cacao.util.SocketServices;
import cacao.functions.Variables;
import cacao.util.FlowController;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    private VBox vbImagen;

    public static String nombreJ;
    @FXML
    private ScrollPane scScroll;
    @FXML
    private AnchorPane paneMesaJuego;
    @FXML
    private JFXButton btnPasarTurno;
    @FXML
    private Text txtTurnoJugador;
    @FXML
    private GridPane vbJungla;

    private VBox matrizJungla[] = new VBox[2];

    private Cartas logicasSelva[] = new Cartas[2];

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

    private static int fla = 0;

    private static int clm = 0;

    private int rot = 0;

    private int cSeleccionada = 0;

    private int jSeleccionada = 0;

    private boolean init = false;

    //Jugador pricipal
    private Partida p = new Partida();

    //Componentes del jugador 1
    @FXML
    private VBox vbContenedorJ1;
    @FXML
    private Text txtNombreJ1;
    @FXML
    private GridPane vbJugador1;

    private VBox botonesJ[] = new VBox[3];

    private Cartas logicas[] = new Cartas[3];

    public static String nombre;

    public static LocalDate edad;

    public static String color;

    @FXML
    private Text txtNuecesJ1;
    @FXML
    private Text txtAguaJ1;
    @FXML
    private Text txtSolJ1;
    @FXML
    private Text txtOroJ1;

    //Componentes del jugador 2
    @FXML
    private VBox vbContenedorJ2;
    @FXML
    private Text txtNombreJ2;
    @FXML
    private GridPane vbJugador2;
    @FXML
    private Text txtNuecesJ2;
    @FXML
    private Text txtAguaJ2;
    @FXML
    private Text txtSolJ2;
    @FXML
    private Text txtOroJ2;

    //Componenetes del jugador 3
    @FXML
    private VBox vbContenedorJ3;
    @FXML
    private Text txtNombreJ3;
    @FXML
    private GridPane vbJugador3;
    @FXML
    private Text txtNuecesJ3;
    @FXML
    private Text txtAguaJ3;
    @FXML
    private Text txtSolJ3;
    @FXML
    private Text txtOroJ3;
    //Componetes del jugador 4
    @FXML
    private VBox vbContenedorJ4;
    @FXML
    private Text txtNombreJ4;
    @FXML
    private GridPane vbJugador4;

    private static Jugador array[] = new Jugador[4];

    public Variables variables = new Variables();

    private Validaciones vl = new Validaciones();

    public boolean recivido = false;
    @FXML
    private JFXButton btnSalir;

    @FXML
    private Text txtNuecesJ4;
    @FXML
    private Text txtAguaJ4;
    @FXML
    private Text txtSolJ4;
    @FXML
    private Text txtOroJ4;

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
        cartasJugador();
        cartasJungla();
        btnIzquierda.setDisable(true);
        btnDerecha.setDisable(true);
        btnCentrar.setDisable(true);
        btnPasarTurno.setDisable(true);
        variables.setCartaTrabajador(true);
        variables.setCartaTablero(false);
        variables.setCartaJungla(false);
        variables.setNum(false);
        variables.setLlenarJungla(false);
        vbContenedorJ1.setVisible(false);
        vbContenedorJ2.setVisible(false);
        vbContenedorJ3.setVisible(false);
        vbContenedorJ4.setVisible(false);

        try {
            Jugador g = new Jugador(nombre, edad, color);
            g.crearCartas(nombre, color);
            p.iniciarArrays();
            p.agregarJugador(g);
            cartasUsables();
            enviarPeticion("registrar jugador");

            init = true;
            //llenarCartasJungla();
            for (int i = 0; i < 3; i++) {
                if (logicas[i] != null) {
                    agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[i], 0, i);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(MesaJuegoViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize() {

    }

    private int cantidad() {

        int num = 0;

        for (int i = 0; i < p.getJugadores().length; i++) {
            if (p.getJugadores()[i] != null) {
                num++;
            }
        }
        return num;
    }

    private void onActionEnviar(ActionEvent event) throws IOException {
        Validaciones vl = new Validaciones();
        int num = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                if (p.getMatrizLogica()[i][j] != null) {
                    num++;
                }
            }
        }

        System.out.print(num);
    }

    @Override
    public void update(Observable o, Object arg) {

        String cadena = (String) arg;
        Gson gs = new Gson();
        Partida llegada = gs.fromJson(cadena, Partida.class);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                int tm = 0;
                for (int i = 0; i < 4; i++) {
                    if (llegada.getJugadores()[i] != null) {
                        tm++;
                    }
                }

                if ("Jugadores".equals(llegada.getPeticion())) {

                    borrarImagen(14, 15, gpMatrizJuego);
                    p.agregarCarta(14, 15, llegada.getCartasIniciales()[0]);
                    agregarImagen(4, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), llegada.getCartasIniciales()[0], 14, 15);

                    borrarImagen(15, 16, gpMatrizJuego);
                    p.agregarCarta(15, 16, llegada.getCartasIniciales()[1]);
                    agregarImagen(4, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), llegada.getCartasIniciales()[1], 15, 16);

                    p.setJugadores(llegada.getJugadores());
                    p.setTurnoJugador(llegada.getTurnoJugador());
                    txtTurnoJugador.setText(p.getTurnoJugador());
                    if (!variables.getLlenarJungla()) {
                        variables.setLlenarJungla(true);
                        p.setCartasJungla(llegada.getCartasJungla());
                        try {
                            llenarCartasJungla();
                            for (int i = 0; i < 2; i++) {
                                agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(MesaJuegoViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    int contador = 1;
                    for (int i = 0; i < p.getJugadores().length; i++) {
                        if (p.getJugadores()[i] != null) {
                            if (p.getJugadores()[i].getNombre().equals(nombre)) {
                                vbContenedorJ1.setVisible(true);
                                vbContenedorJ1.setId(p.getJugadores()[i].getColor());
                                vbContenedorJ1.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                txtNombreJ1.setText(p.getJugadores()[i].getNombre());
                            } else {
                                if (contador == 1) {
                                    vbContenedorJ2.setVisible(true);
                                    vbContenedorJ2.setId(p.getJugadores()[i].getColor());
                                    vbContenedorJ2.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    txtNombreJ2.setText(p.getJugadores()[i].getNombre());
                                } else if (contador == 2) {
                                    vbContenedorJ3.setVisible(true);
                                    vbContenedorJ3.setId(p.getJugadores()[i].getColor());
                                    vbContenedorJ3.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    txtNombreJ3.setText(p.getJugadores()[i].getNombre());
                                } else if (contador == 3) {
                                    vbContenedorJ4.setVisible(true);
                                    vbContenedorJ4.setId(p.getJugadores()[i].getNombre());
                                    vbContenedorJ4.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    txtNombreJ4.setText(p.getJugadores()[i].getNombre());
                                }
                                contador++;
                            }
                        }
                    }

                } else if ("actualizar cartas jugadores".equals(llegada.getPeticion())) {
                    for (int i = 0; i < 4; i++) {
                        if (p.getJugadores()[i] != null) {
                            p.getJugadores()[i].setCartasJugador(llegada.getJugadores()[i].getCartasJugador());
                        }
                    }
                } else if ("pasar turno".equals(llegada.getPeticion())) {
                    p.setTurnoJugador(llegada.getTurnoJugador());
                    String turno = llegada.getTurnoJugador();
                    txtTurnoJugador.setText(p.getTurnoJugador());
                    if (p.getTurnoJugador().equals(nombre)) {
                        variables.setCartaTrabajador(true);
                    }

                } else if ("colocar carta jugador".equals(llegada.getPeticion())) {
                    p.setMatrizLogica(llegada.getMatrizLogica());
                    p.setCartaJugada(llegada.getCartaJugada(), llegada.getX(), llegada.getY(), 0);
                    if (!p.getTurnoJugador().equals(nombre)) {
                        borrarImagen(llegada.getX(), llegada.getY(), gpMatrizJuego);
                        matrizVbox[p.getX()][p.getY()].setId(p.getCartaJugada().getColor());
                        matrizVbox[p.getX()][p.getY()].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                        agregarImagen(2, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), p.getCartaJugada(), p.getX(), p.getY());
                    }
                    //nueces
                } else if ("colocar carta jungla".equals(llegada.getPeticion())) {
                    if (!p.getTurnoJugador().equals(nombre)) {
                        p.setCartasJungla(llegada.getCartasJungla());
                        p.setCartaJugada(llegada.getCartaJugada(), llegada.getX(), llegada.getY(), llegada.getMazo());
                        p.agregarCarta(p.getX(), p.getY(), p.getCartaJugada());
                        logicasSelva[llegada.getMazo()] = null;
                        borrarImagen(p.getX(), p.getY(), gpMatrizJuego);
                        agregarImagen(4, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), p.getCartaJugada(), p.getX(), p.getY());
                        borrarImagen(p.getMazo(), 0, vbJungla);

                    }
                } else if ("actualizar jugadores".equals(llegada.getPeticion())) {

                    p.setJugadores(llegada.getJugadores());
                    int ct = 0;
                    variables.setNum(true);
                    for (int i = 0; i < 4; i++) {
                        if (p.getJugadores()[i] != null) {
                            ct++;
                            variables.setNum(false);
                        }
                    }

                } else if ("actualizar jungla".equals(llegada.getPeticion())) {
                    if (!p.getTurnoJugador().equals(nombre)) {
                        try {
                            llenarCartasJungla();
                        } catch (IOException ex) {
                            Logger.getLogger(MesaJuegoViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        for (int i = 0; i < 2; i++) {
                            if (logicasSelva[i] != null) {
                                matrizJungla[i].setId("color");
                                matrizJungla[i].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                                matrizJungla[i].getStyleClass().clear();
                                agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                            }
                        }
                    }

                } else if ("cerrar".equals(llegada.getPeticion())) {

                }
            }
        });
    }

    private void agregarVbox() {

        for (int i = 0; i <= 31; i++) {
            for (int j = 0; j <= 31; j++) {

                matrizVbox[i][j] = new VBox();
                matrizVbox[i][j].setAlignment(Pos.CENTER);
                matrizVbox[i][j].setPrefHeight(89);
                matrizVbox[i][j].setPrefWidth(89);
                gpMatrizJuego.add(matrizVbox[i][j], j, i);
                final int fl = i;
                final int cl = j;
                matrizVbox[i][j].addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        if (variables.getCartaTablero()) {
                            fla = fl;
                            clm = cl;

                            //If else para difereciar si es carta de trabajor o jungla
                            if (variables.getNum()) {

                                borrarImagen(fl, cl, gpMatrizJuego);
                                btnIzquierda.setDisable(true);
                                btnDerecha.setDisable(true);
                                btnCentrar.setDisable(true);
                                agregarImagen(4, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), logicasSelva[jSeleccionada], fl, cl);
                                p.agregarCarta(fl, cl, logicasSelva[jSeleccionada]);
                                p.setCartaJugada(logicasSelva[jSeleccionada], fl, cl, jSeleccionada);
                                logicasSelva[jSeleccionada] = null;
                                variables.setNum(false);
                                borrarImagen(jSeleccionada, 0, vbJungla);
                                enviarPeticion("colocar carta jungla");
                            } else {
                                if (vl.validarCartaJugador(p.getMatrizLogica(), "Tbr", fl, cl)) {
                                    variables.setCartaTrabajador(false);
                                    variables.setCartaJungla(true);
                                    borrarImagen(fl, cl, gpMatrizJuego);
                                    btnIzquierda.setDisable(false);
                                    btnDerecha.setDisable(false);
                                    btnCentrar.setDisable(false);
                                    matrizVbox[fl][cl].setId("opacity");
                                    matrizVbox[fl][cl].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    agregarImagen(2, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), logicas[cSeleccionada], fl, cl);
                                    p.agregarCarta(fl, cl, logicas[cSeleccionada]);
                                    p.setCartaJugada(logicas[cSeleccionada], fl, cl, 0);
                                    logicas[cSeleccionada] = null;
                                    borrarImagen(0, cSeleccionada, vbJugador1);
                                    enviarPeticion("colocar carta jugador");
                                }

                            }

                        }
                    }
                });
            }
        }
    }

    public void enviarPeticion(String peticion) {
        p.setPeticion(peticion);
        Gson gson = new Gson();
        String json = gson.toJson(p);
        try {
            c.enviarDatos(json);
        } catch (IOException ex) {
            Logger.getLogger(MesaJuegoViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cartasJugador() {

        for (int i = 0; i < 3; i++) {

            botonesJ[i] = new VBox();
            botonesJ[i].setAlignment(Pos.CENTER);
            botonesJ[i].setId(color);
            botonesJ[i].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
            botonesJ[i].setPrefHeight(73);
            botonesJ[i].setPrefWidth(73);
            final int cs = i;
            botonesJ[i].addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (variables.getCartaTrabajador()) {
                        variables.setCartaTablero(true);
                        variables.setCartaJungla(true);
                        cSeleccionada = cs;
                        logicas[cs].setColor(color);
                        borrarImagen(0, cs, vbJugador1);
                        botonesJ[cs].setId("opacity");
                        botonesJ[cs].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                        agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[cs], 0, cs);
                    }
                }

            });
        }
    }

    private void cartasJungla() {

        for (int i = 0; i < 2; i++) {

            matrizJungla[i] = new VBox();
            matrizJungla[i].setAlignment(Pos.CENTER);
            matrizJungla[i].setPrefHeight(98);
            matrizJungla[i].setPrefWidth(98);
            final int cs = i;
            //vbJungla.add(matrizJungla[i], 0, i);
            matrizJungla[i].addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (variables.getCartaJungla()) {
                        variables.setCartaTablero(true);
                        variables.setNum(true);
                        variables.setCartaJungla(false);
                        jSeleccionada = cs;
                        matrizJungla[cs].setId("opacity");
                        matrizJungla[cs].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                        agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[cs], cs, 0);
                    }
                }
            });
        }

    }

    public void borrarImagen(final int row, final int column, GridPane gridPane) {

        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (node instanceof VBox && gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                gridPane.getChildren().remove(node);
                break;
            }
        }
    }

    public void agregarImagen(int tipo, GridPane gridpane, Cartas[] cartas, VBox[] vbox, VBox[][] mVBox, Cartas[][] cartasM, Cartas carta, int f, int c) {
        AgregarImagen add = new AgregarImagen();
        add.agregarImagen(tipo, gridpane, cartas, vbox, mVBox, cartasM, carta, f, c);
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
    private void onActiomPasarTurno(ActionEvent event) throws IOException {
        //Cartas trabajador
        llenarCartasJungla();
        cartasUsables();
        pasarTurno();
        enviarPeticion("actualizar jungla");
        enviarPeticion("pasar turno");
    }

    private void pasarTurno() {
        for (int i = 0; i < 3; i++) {
            if (logicas[i] != null) {
                agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[i], 0, i);
            }
            if (i < 2) {
                if (logicasSelva[i] != null) {
                    matrizJungla[i].setId("color");
                    matrizJungla[i].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                    agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                    matrizJungla[i].getStyleClass().clear();
                    agregarImagen(3, vbJungla, logicasSelva, matrizJungla, null, null, logicasSelva[i], i, 0);
                }
            }
        }
    }

    @FXML
    private void onActionCentrar(ActionEvent event) throws IOException {

        botonesJ[cSeleccionada].setId(color);
        botonesJ[cSeleccionada].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());

        borrarImagen(fla, clm, gpMatrizJuego);
        matrizVbox[fla][clm].setId(color);
        matrizVbox[fla][clm].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
        gpMatrizJuego.add(matrizVbox[fla][clm], clm, fla);

        btnIzquierda.setDisable(true);
        btnDerecha.setDisable(true);
        btnCentrar.setDisable(true);
        btnPasarTurno.setDisable(false);
    }

    @FXML
    private void onActionDerecha(ActionEvent event) {

        borrarImagen(fla, clm, gpMatrizJuego);

        if (p.getMatrizLogica()[fla][clm].getGrados() == 0) {
            rot = 90;
            p.getMatrizLogica()[fla][clm].setGrados(90);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 90) {
            rot = 180;
            p.getMatrizLogica()[fla][clm].setGrados(180);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 180) {
            rot = 270;
            p.getMatrizLogica()[fla][clm].setGrados(270);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 270) {
            rot = 0;
            p.getMatrizLogica()[fla][clm].setGrados(0);
        }

        matrizVbox[fla][clm].getChildren().get(0).setRotate(rot);

        gpMatrizJuego.add(matrizVbox[fla][clm], clm, fla);

        p.setCartaJugada(p.getMatrizLogica()[fla][clm], fla, clm, 0);

        enviarPeticion("colocar carta jugador");
    }

    @FXML
    private void onActionIzquierda(ActionEvent event) {

        borrarImagen(fla, clm, gpMatrizJuego);

        if (p.getMatrizLogica()[fla][clm].getGrados() == 0) {
            rot = 270;
            p.getMatrizLogica()[fla][clm].setGrados(270);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 90) {
            rot = 0;
            p.getMatrizLogica()[fla][clm].setGrados(0);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 180) {
            rot = 90;
            p.getMatrizLogica()[fla][clm].setGrados(90);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 270) {
            rot = 180;
            p.getMatrizLogica()[fla][clm].setGrados(180);
        }

        matrizVbox[fla][clm].getChildren().get(0).setRotate(rot);

        gpMatrizJuego.add(matrizVbox[fla][clm], clm, fla);

        p.setCartaJugada(p.getMatrizLogica()[fla][clm], fla, clm, 0);

        enviarPeticion("colocar carta jugador");
    }

    private void cartasUsables() throws IOException {
        Validaciones vl = new Validaciones();
        vl.cartasUsables(p.getCartasJungla(), logicas, p, jugadorActual());

        int sm = 0;
        for (int i = 0; i < 11; i++) {
            if (p.getJugadores()[jugadorActual()].getCartasJugador()[i] != null) {
                sm++;
            }
        }
        if (init) {
            enviarPeticion("actualizar cartas jugadores");
        }

    }

    private void llenarCartasJungla() throws IOException {
        Validaciones vl = new Validaciones();
        vl.llenarCartasJungla(p.getCartasJungla(), logicasSelva, p);

        if (init) {
            //enviarPeticion("actualizar cartas jugadores");
        }

    }

    private int jugadorActual() {
        int jugador = 0;
        boolean escogido = false;
        for (int i = 0; i < p.getJugadores().length; i++) {
            if (escogido == false) {
                if (p.getJugadores()[i].getNombre().equals(nombre)) {
                    jugador = i;
                    escogido = true;
                }
            }
        }
        return jugador;
    }

    public Partida getP() {
        return p;
    }

    public void setP(Partida p) {
        this.p = p;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getEdad() {
        return edad;
    }

    public String getColor() {
        return color;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setVariables(Variables variables) {
        this.variables = variables;
    }

    @FXML
    private void onActionSalir(ActionEvent event) throws IOException {
        p.setSalir(nombre);
        enviarPeticion("salir");
        FlowController.getInstance().goViewInNewStage("InicioView", stage);
    }
}
