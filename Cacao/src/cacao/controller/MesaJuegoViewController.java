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
import javafx.scene.control.Button;
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

    private static Cartas slc = new Cartas();

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
    @FXML

    //Sala de espera
    private Text txtTd;
    @FXML
    private Text txtJ1;
    @FXML
    private Text txtJ2;
    @FXML
    private Text txtJ3;
    @FXML
    private Text txtJ4;
    @FXML
    private JFXButton btnListo;
    @FXML
    private AnchorPane vbSalaEspera;
    @FXML
    private VBox vbCuadroSeleccion;
    @FXML
    private VBox vbDinero;
    @FXML
    private Button btnArriba;
    @FXML
    private Button btnAbajo;
    @FXML
    private Button btnNIzquierda;
    @FXML
    private Button btnNDerecha;

    private int p1 = 0;
    private int p2 = 0;
    @FXML
    private AnchorPane vbGanador;
    @FXML
    private JFXButton btnSalirGn;
    @FXML
    private Text txtGanador;

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
        vbCuadroSeleccion.setVisible(false);

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

        visibilidad(false);
    }

    @Override
    public void initialize() {

    }

    private void visibilidad(boolean visibilidad) {
        btnSalir.setVisible(visibilidad);
        scScroll.setVisible(visibilidad);
        btnCentrar.setVisible(visibilidad);
        btnDerecha.setVisible(visibilidad);
        btnPasarTurno.setVisible(visibilidad);
        vbImagen.setVisible(visibilidad);
        txtTd.setVisible(visibilidad);
        txtTurnoJugador.setVisible(visibilidad);
        vbJungla.setVisible(visibilidad);

    }

    private void actualizar() {

        vbCuadroSeleccion.setVisible(true);

        if (vbDinero.getChildren().size() > 0) {
            vbDinero.getChildren().remove(0);
        }

        Image im = new Image("/cacao/resources/Cartas/" + slc.getTipo() + slc.getDerecha() + slc.getAbajo() + slc.getIzquierda() + slc.getArriba() + ".png", 86, 86, false, true);
        ImageView nw = new ImageView(im);
        nw.setRotate(slc.getGrados());
        vbDinero.getChildren().add(nw);

        vbDinero.setId(color);
        vbDinero.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());

        int x = p1;
        int y = p2;
        //Derecha
        if (p.getMatrizLogica()[x][y + 1] != null) {
            btnNDerecha.setText(String.valueOf(p.getMatrizLogica()[x][y].getDerechaG()));
            btnNDerecha.setDisable(false);
        } else {
            btnNDerecha.setText("");
            btnNDerecha.setDisable(true);
        }
        //Abajo
        if (p.getMatrizLogica()[x + 1][y] != null) {
            btnAbajo.setText(String.valueOf(p.getMatrizLogica()[x][y].getAbajoG()));
            btnAbajo.setDisable(false);
        } else {
            btnAbajo.setText("");
            btnAbajo.setDisable(true);
        }

        //Izquierda
        if (p.getMatrizLogica()[x][y - 1] != null) {
            btnNIzquierda.setText(String.valueOf(p.getMatrizLogica()[x][y].getIzquierdaG()));
            btnNIzquierda.setDisable(false);
        } else {
            btnNIzquierda.setText("");
            btnNIzquierda.setDisable(true);
        }

        //Arriba
        if (p.getMatrizLogica()[x - 1][y] != null) {
            btnArriba.setText(String.valueOf(p.getMatrizLogica()[x][y].getArribaG()));
            btnArriba.setDisable(false);
        } else {
            btnArriba.setText("");
            btnArriba.setDisable(true);
        }
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
                                txtJ1.setText(p.getJugadores()[i].getNombre());
                                if (p.getJugadores()[i].getListo().equals("listo")) {
                                    txtJ1.setStyle("-fx-fill: Blue");
                                } else {

                                }
                            } else {
                                if (contador == 1) {
                                    vbContenedorJ2.setVisible(true);
                                    vbContenedorJ2.setId(p.getJugadores()[i].getColor());
                                    vbContenedorJ2.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    txtNombreJ2.setText(p.getJugadores()[i].getNombre());
                                    txtJ2.setText(p.getJugadores()[i].getNombre());
                                    if (p.getJugadores()[i].getListo().equals("listo")) {
                                        txtJ2.setStyle("-fx-fill: Blue");
                                    } else {

                                    }
                                } else if (contador == 2) {
                                    vbContenedorJ3.setVisible(true);
                                    vbContenedorJ3.setId(p.getJugadores()[i].getColor());
                                    vbContenedorJ3.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    txtNombreJ3.setText(p.getJugadores()[i].getNombre());
                                    txtJ3.setText(p.getJugadores()[i].getNombre());
                                    if (p.getJugadores()[i].getListo().equals("listo")) {
                                        txtJ3.setStyle("-fx-fill: Blue");
                                    } else {

                                    }
                                } else if (contador == 3) {
                                    vbContenedorJ4.setVisible(true);
                                    vbContenedorJ4.setId(p.getJugadores()[i].getColor());
                                    vbContenedorJ4.getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                                    txtNombreJ4.setText(p.getJugadores()[i].getNombre());
                                    txtJ4.setText(p.getJugadores()[i].getNombre());
                                    if (p.getJugadores()[i].getListo().equals("listo")) {
                                        txtJ4.setStyle("-fx-fill: Blue");
                                    } else {

                                    }
                                }
                                contador++;
                            }
                        }
                    }

                    boolean iniciar = true;
                    for (int i = 0; i < 4; i++) {
                        if (p.getJugadores()[i] != null) {
                            if (p.getJugadores()[i].getListo().equals("listo")) {

                            } else {
                                iniciar = false;
                            }
                        }
                    }
                    if (iniciar) {
                        vbSalaEspera.setVisible(false);
                        visibilidad(true);
                    }
                } else if ("actualizar cartas jugadores".equals(llegada.getPeticion())) {
                    for (int i = 0; i < 4; i++) {
                        if (p.getJugadores()[i] != null) {
                            p.getJugadores()[i].setCartasJugador(llegada.getJugadores()[i].getCartasJugador());
                        }
                    }
                } else if ("actualizar puntaje".equals(llegada.getPeticion())) {

                    p.setJugadores(llegada.getJugadores());
                    int contador = 1;
                    for (int i = 0; i < p.getJugadores().length; i++) {
                        if (p.getJugadores()[i] != null) {
                            if (p.getJugadores()[i].getNombre().equals(nombre)) {

                            } else {
                                if (contador == 1) {
                                    System.out.print(p.getJugadores()[i].getNueces());
                                    txtNuecesJ2.setText(String.valueOf(p.getJugadores()[i].getNueces()));
                                    txtAguaJ2.setText(String.valueOf(p.getJugadores()[i].getAgua()));
                                    txtSolJ2.setText(String.valueOf(p.getJugadores()[i].getFichasSol()));
                                    txtOroJ2.setText(String.valueOf(p.getJugadores()[i].getMonedas()));
                                } else if (contador == 2) {
                                    txtNuecesJ3.setText(String.valueOf(p.getJugadores()[i].getNueces()));
                                    txtAguaJ3.setText(String.valueOf(p.getJugadores()[i].getAgua()));
                                    txtSolJ3.setText(String.valueOf(p.getJugadores()[i].getFichasSol()));
                                    txtOroJ3.setText(String.valueOf(p.getJugadores()[i].getMonedas()));
                                } else if (contador == 3) {
                                    txtNuecesJ4.setText(String.valueOf(p.getJugadores()[i].getNueces()));
                                    txtAguaJ4.setText(String.valueOf(p.getJugadores()[i].getAgua()));
                                    txtSolJ4.setText(String.valueOf(p.getJugadores()[i].getFichasSol()));
                                    txtOroJ4.setText(String.valueOf(p.getJugadores()[i].getMonedas()));
                                }
                                contador++;
                            }
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
                        p.setMatrizLogica(llegada.getMatrizLogica());
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

                }else if("ganador".equals(llegada.getPeticion())){
                    txtGanador.setText(llegada.getGanador());
                    vbGanador.setVisible(true);
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
                variables.setComprobacion(true);
                matrizVbox[i][j].addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        if (p.getTurnoJugador().equals(nombre)) {
                            if (variables.getCartaTablero()) {
                                fla = fl;
                                clm = cl;

                                //If else para difereciar si es carta de trabajor o jungla
                                if (variables.getNum()) {
                                    if (vl.validarCartaJugador(p.getMatrizLogica(), "Jungla", fl, cl)) {
                                        borrarImagen(fl, cl, gpMatrizJuego);
                                        btnDerecha.setDisable(true);
                                        btnCentrar.setDisable(true);
                                        btnPasarTurno.setDisable(false);
                                        agregarImagen(4, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), logicasSelva[jSeleccionada], fl, cl);
                                        p.agregarCarta(fl, cl, logicasSelva[jSeleccionada]);
                                        p.setCartaJugada(logicasSelva[jSeleccionada], fl, cl, jSeleccionada);
                                        logicasSelva[jSeleccionada] = null;
                                        variables.setNum(false);
                                        borrarImagen(jSeleccionada, 0, vbJungla);
                                        enviarPeticion("colocar carta jungla");
                                        vbCuadroSeleccion.setVisible(true);
                                        actualizar();
                                    }

                                } else {
                                    if (vl.validarCartaJugador(p.getMatrizLogica(), "Tbr", fl, cl)) {
                                        System.out.print("Validaciones F: " + fl + " C: " + cl + "\n");
                                        variables.setCartaTrabajador(false);
                                        variables.setCartaJungla(true);
                                        borrarImagen(fl, cl, gpMatrizJuego);
                                        btnDerecha.setDisable(false);
                                        btnCentrar.setDisable(false);
                                        slc = logicas[cSeleccionada];
                                        p1 = fl;
                                        p2 = cl;
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
                    if (p.getTurnoJugador().equals(nombre)) {
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
                    if (p.getTurnoJugador().equals(nombre)) {
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
        vbCuadroSeleccion.setVisible(false);
        btnPasarTurno.setDisable(true);
        if (logicasSelva[0] == null && logicasSelva[1] == null) {
            enviarPeticion("ganador");
        }
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

        btnDerecha.setDisable(true);
        btnCentrar.setDisable(true);
    }

    @FXML
    private void onActionDerecha(ActionEvent event) {

        borrarImagen(fla, clm, gpMatrizJuego);

        if (p.getMatrizLogica()[fla][clm].getGrados() == 0) {
            rot = 90;
            p.getMatrizLogica()[fla][clm].setGrados(90);
            slc.setGrados(rot);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 90) {
            rot = 180;
            slc.setGrados(rot);
            p.getMatrizLogica()[fla][clm].setGrados(180);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 180) {
            rot = 270;
            p.getMatrizLogica()[fla][clm].setGrados(270);
            slc.setGrados(rot);
        } else if (p.getMatrizLogica()[fla][clm].getGrados() == 270) {
            rot = 0;
            p.getMatrizLogica()[fla][clm].setGrados(0);
            slc.setGrados(rot);
        }

        int derecha = p.getMatrizLogica()[fla][clm].getDerechaG();
        int abajo = p.getMatrizLogica()[fla][clm].getAbajoG();
        int izquierda = p.getMatrizLogica()[fla][clm].getIzquierdaG();
        int arriba = p.getMatrizLogica()[fla][clm].getArribaG();

        p.getMatrizLogica()[fla][clm].setDerechaG(arriba);
        p.getMatrizLogica()[fla][clm].setAbajoG(derecha);
        p.getMatrizLogica()[fla][clm].setIzquierdaG(abajo);
        p.getMatrizLogica()[fla][clm].setArribaG(izquierda);

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

    @FXML
    private void onActionListo(ActionEvent event) {
        p.setListo(nombre);
        int cantJ = 0;
        for (int i = 0; i < 4; i++) {
            if (p.getJugadores()[i] != null) {
                cantJ++;
            }
        }
        System.out.print("Jugadores: " + cantJ);
        if (cantJ < 3) {
            int aux1 = 0;
            int aux2 = 0;
            int aux3 = 0;
            int aux4 = 0;
            int aux5 = 0;
            int aux6 = 0;
            for (int i = 0; i < 28; i++) {
                if (p.getCartasJungla()[i] != null) {
                    if (p.getCartasJungla()[i].getTipo().equals("Pla") && p.getCartasJungla()[i].getValor() == 1) {
                        if (aux1 < 2) {
                            p.getCartasJungla()[i] = null;
                            aux1++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Mca") && p.getCartasJungla()[i].getValor() == 3) {
                        if (aux2 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux2++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Mns") && p.getCartasJungla()[i].getValor() == 1) {
                        if (aux3 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux3++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Ag")) {
                        if (aux4 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux4++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Tmp")) {
                        if (aux5 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux5++;
                        }
                    }else if (p.getCartasJungla()[i].getTipo().equals("Ads")) {
                        if (aux6 < 1) {
                            p.getCartasJungla()[i] = null;
                            aux6++;
                        }
                    }
                }
            }

        } else if (cantJ >= 3) {
            boolean term1 = false;
            boolean term2 = false;
            for (int i = 0; i < 4; i++) {
                term1 = false;
                term2 = false;
                if (p.getJugadores()[i] != null) {
                    for (int j = 0; j < 11; j++) {
                        if (!term1) {
                            if (p.getJugadores()[i].getCartasJugador()[j] != null) {
                                Cartas nc = p.getJugadores()[i].getCartasJugador()[j];
                                if (nc.getTipo().equals("Tbr") && nc.getDerecha() == 1 && nc.getAbajo() == 1 && nc.getIzquierda() == 1 && nc.getArriba() == 1) {
                                    System.out.print("Ingresado");
                                    p.getJugadores()[i].getCartasJugador()[j] = null;
                                    term1 = true;
                                }
                            }
                        }
                        if (!term2 && cantJ == 4) {
                            Cartas nc = p.getJugadores()[i].getCartasJugador()[j];
                            if (p.getJugadores()[i].getCartasJugador()[j] != null) {
                                if (nc.getTipo().equals("Tbr") && nc.getDerecha() == 1 && nc.getAbajo() == 1 && nc.getIzquierda() == 1 && nc.getArriba() == 1) {
                                    System.out.print("Carta eliminada 2");
                                    p.getJugadores()[i].getCartasJugador()[i] = null;
                                    term2 = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        enviarPeticion("listo");
    }

    @FXML
    private void onActionArriba(ActionEvent event) {
        btnArriba.setDisable(true);
        vl.validarValores(p.getMatrizLogica(), p.getMatrizLogica()[p1][p2], btnNDerecha, btnAbajo, btnNIzquierda, btnArriba, txtNuecesJ1, txtAguaJ1, txtSolJ1, txtOroJ1, p1 - 1, p2, "arriba");
        actualizarPuntajes();
    }

    @FXML
    private void onActionAbajo(ActionEvent event) {
        btnAbajo.setDisable(true);
        vl.validarValores(p.getMatrizLogica(), p.getMatrizLogica()[p1][p2], btnNDerecha, btnAbajo, btnNIzquierda, btnArriba, txtNuecesJ1, txtAguaJ1, txtSolJ1, txtOroJ1, p1 + 1, p2, "abajo");
        actualizarPuntajes();
    }

    @FXML
    private void onActionNIzquierda(ActionEvent event) {
        btnNIzquierda.setDisable(true);
        vl.validarValores(p.getMatrizLogica(), p.getMatrizLogica()[p1][p2], btnNDerecha, btnAbajo, btnNIzquierda, btnArriba, txtNuecesJ1, txtAguaJ1, txtSolJ1, txtOroJ1, p1, p2 - 1, "izquierda");
        actualizarPuntajes();
    }

    @FXML
    private void onActionNDerecha(ActionEvent event) {
        btnNDerecha.setDisable(true);
        vl.validarValores(p.getMatrizLogica(), p.getMatrizLogica()[p1][p2], btnNDerecha, btnAbajo, btnNIzquierda, btnArriba, txtNuecesJ1, txtAguaJ1, txtSolJ1, txtOroJ1, p1, p2 + 1, "derecha");
        actualizarPuntajes();
    }

    private void actualizarPuntajes() {
        for (int i = 0; i < 4; i++) {
            if (p.getJugadores()[i] != null) {
                if (p.getJugadores()[i].getNombre().equals(nombre)) {
                    p.getJugadores()[i].setNueces(Integer.parseInt(txtNuecesJ1.getText()));
                    p.getJugadores()[i].setAgua(Integer.parseInt(txtAguaJ1.getText()));
                    p.getJugadores()[i].setMonedas(Integer.parseInt(txtOroJ1.getText()));
                    p.getJugadores()[i].setFichasSol(Integer.parseInt(txtSolJ1.getText()));
                    p.setTurnoJugador(nombre);
                }
            }

        }
        enviarPeticion("actualizar puntajes");
    }

    @FXML
    private void onActionSalirGn(ActionEvent event) {
        p.setSalir(nombre);
        enviarPeticion("salir");
        FlowController.getInstance().goViewInNewStage("InicioView", stage);
    }
}
