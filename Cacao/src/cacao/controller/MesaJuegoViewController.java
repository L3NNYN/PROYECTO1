/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cacao.controller;

import cacao.functions.Cartas;
import cacao.functions.Jugador;
import cacao.functions.Partida;
import cacao.util.SocketServices;
import cacao.util.Variables;
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
    private JFXButton btnEnviar;
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

    private static Jugador array[] = new Jugador[4];

    private Variables variables = new Variables();

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
        btnIzquierda.setDisable(true);
        btnDerecha.setDisable(true);
        btnCentrar.setDisable(true);
        variables.setCartaTrabajador(true);
        variables.setCartaTablero(false);
        vbContenedorJ1.setVisible(false);
        vbContenedorJ2.setVisible(false);
        vbContenedorJ3.setVisible(false);
        vbContenedorJ4.setVisible(false);

        try {
            Jugador g = new Jugador(nombre, edad, color);
            g.crearCartas(nombre, color);
            p.iniciarArrays();
            p.agregarJugador(g);
            p.crearCartasJungla();
            cartasUsables();
            p.setPeticion("registrar jugador");
            Gson gson = new Gson();
            String json = gson.toJson(p);
            c.enviarDatos(json);
            init = true;
            if (logicas[0] != null) {
                agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[0], 0, 0);
            }
            if (logicas[1] != null) {
                agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[1], 0, 1);
            }
            if (logicas[2] != null) {
                agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[2], 0, 2);
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

    @FXML
    private void onActionEnviar(ActionEvent event) throws IOException {

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
                    p.setJugadores(llegada.getJugadores());
                    p.setTurnoJugador(llegada.getTurnoJugador());
                    txtTurnoJugador.setText(p.getTurnoJugador());
                    System.out.print(cantidad());
                    int contador = 1;
                    for (int i = 0; i < p.getJugadores().length; i++) {    
                        if (p.getJugadores()[i] != null) {
                            if (p.getJugadores()[i].getNombre().equals(nombre)) {
                                vbContenedorJ1.setVisible(true);
                                txtNombreJ1.setText(p.getJugadores()[i].getNombre());
                            } else {
                                if (contador == 1) {
                                    vbContenedorJ2.setVisible(true);
                                    txtNombreJ2.setText(p.getJugadores()[i].getNombre());
                                } else if (contador == 2) {
                                    vbContenedorJ3.setVisible(true);
                                    txtNombreJ3.setText(p.getJugadores()[i].getNombre());
                                } else if (contador == 3) {
                                    vbContenedorJ4.setVisible(true);
                                    txtNombreJ4.setText(p.getJugadores()[i].getNombre());
                                }
                                contador++;
                            }
                        }
                    }
                    System.out.print("Cantidad Jugador: "+contador);

                } else if ("actualizar cartas jugadores".equals(llegada.getPeticion())) {
                    System.out.print("actualizar cartas jugadores");
                    for (int i = 0; i < 4; i++) {
                        if (p.getJugadores()[i] != null) {
                            p.getJugadores()[i].setCartasJugador(llegada.getJugadores()[i].getCartasJugador());
                        }
                    }
                } else if ("pasar turno".equals(llegada.getPeticion())) {
                    p.setTurnoJugador(llegada.getTurnoJugador());
                    txtTurnoJugador.setText(p.getTurnoJugador());
                    if(p.getTurnoJugador().equals(nombre)){
                        variables.setCartaTrabajador(true);
                    }
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
                            btnIzquierda.setDisable(false);
                            btnDerecha.setDisable(false);
                            btnCentrar.setDisable(false);
                            variables.setCartaTrabajador(false);
                            variables.setCartaTablero(false);
                            borrarImagen(fl, cl, gpMatrizJuego);
                            matrizVbox[fl][cl].setId("opacity");
                            matrizVbox[fl][cl].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                            agregarImagen(2, gpMatrizJuego, null, null, matrizVbox, p.getMatrizLogica(), logicas[cSeleccionada], fl, cl);
                            logicas[cSeleccionada] = null;
                            borrarImagen(0, cSeleccionada, vbJugador1);
                        }
                    }
                });
            }
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
                        cSeleccionada = cs;
                        borrarImagen(0, cs, vbJugador1);
                        botonesJ[cs].setId("opacity");
                        botonesJ[cs].getStylesheets().add(getClass().getResource("/cacao/view/style.css").toExternalForm());
                        agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[cs], 0, cs);
                        System.out.print(cs);
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

        if (tipo == 1) {

            if (vbox[c].getChildren().size() > 0) {
                vbox[c].getChildren().remove(0);
                borrarImagen(f, c, gridpane);
            }
            Image im = new Image("/cacao/resources/Cartas/" + carta.getTipo() + carta.getDerecha() + carta.getAbajo() + carta.getIzquierda() + carta.getArriba() + ".png", 73, 73, false, true);
            ImageView im2 = new ImageView(im);
            vbox[c].getChildren().add(im2);
            gridpane.add(vbox[c], c, f);
        }

        if (tipo == 2) {
            if (mVBox[f][c].getChildren().size() > 0) {
                mVBox[f][c].getChildren().remove(0);
                borrarImagen(f, c, gridpane);
            }
            Image im = new Image("/cacao/resources/Cartas/" + carta.getTipo() + carta.getDerecha() + carta.getAbajo() + carta.getIzquierda() + carta.getArriba() + ".png", 89, 89, false, true);
            ImageView im2 = new ImageView(im);
            mVBox[f][c].getChildren().add(im2);
            gridpane.add(mVBox[f][c], c, f);
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
    private void onActiomPasarTurno(ActionEvent event) throws IOException {
        //Cartas trabajador
        cartasUsables();

        if (logicas[0] != null) {
            agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[0], 0, 0);
        }
        if (logicas[1] != null) {
            agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[1], 0, 1);
        }
        if (logicas[2] != null) {
            agregarImagen(1, vbJugador1, logicas, botonesJ, null, null, logicas[2], 0, 2);
        }

        p.setPeticion("pasar turno");
        Gson gson = new Gson();
        String json = gson.toJson(p);
        c.enviarDatos(json);
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
    }

    @FXML
    private void onActionDerecha(ActionEvent event) {

        borrarImagen(fla, clm, gpMatrizJuego);

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

        borrarImagen(fla, clm, gpMatrizJuego);

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

    private void cartasUsables() throws IOException {
        int j = 0;
        boolean b = false;
        for (int i = 0; i < p.getCartasJungla().length; i++) {
            b = false;
            while (b == false) {
                if (j < 3) {
                    if (logicas[j] == null) {
                        if (i < 11) {
                            if (p.getJugadores()[jugadorActual()].getCartasJugador()[i] != null) {
                                logicas[j] = p.getJugadores()[jugadorActual()].getCartasJugador()[i];
                                p.getJugadores()[jugadorActual()].borrarCarta(i);
                                b = true;
                                j++;
                            } else {
                                b = true;
                            }
                        } else {
                            break;
                        }
                    } else {
                        j++;
                    }
                } else {
                    break;
                }
            }
        }
        if (init) {
            p.setPeticion("actualizar cartas jugadores");
            Gson gs = new Gson();
            String js = gs.toJson(p);
            c.enviarDatos(js);
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

    public String getNombre() {
        return nombre;
    }

    public LocalDate getEdad() {
        return edad;
    }

    public String getColor() {
        return color;
    }

}
