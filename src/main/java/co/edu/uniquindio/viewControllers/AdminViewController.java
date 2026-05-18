package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.AdminController;
import co.edu.uniquindio.controllers.EventoController;
import co.edu.uniquindio.model.Compra;
import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Usuario;
import co.edu.uniquindio.patterns.creacionales.Singleton.GestorSesion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class AdminViewController {

    // ── Pestaña Eventos ───────────────────────────────────────────────────────
    @FXML private ListView<Evento>  lstEventos;
    @FXML private Label             lblEstadoEvento;

    // ── Pestaña Usuarios ──────────────────────────────────────────────────────
    @FXML private ListView<Usuario> lstUsuarios;
    @FXML private Label             lblEstadoUsuario;

    // ── Pestaña Compras ───────────────────────────────────────────────────────
    @FXML private ListView<Compra>  lstCompras;
    @FXML private Label             lblEstadoCompra;

    // ── Pestaña Métricas ──────────────────────────────────────────────────────
    @FXML private Label lblTotalVentas;
    @FXML private Label lblIngresoTotal;
    @FXML private Label lblTotalCancelaciones;
    @FXML private Label lblTasaCancelacion;

    @FXML private Label lblMensaje;

    private AdminController   adminController;
    private EventoController  eventoController;

    public void setAdminController(AdminController adminController,
                                   EventoController eventoController) {
        this.adminController  = adminController;
        this.eventoController = eventoController;
        cargarEventos();
        cargarUsuarios();
        cargarCompras();
        cargarMetricas();
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        lstEventos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Evento e, boolean empty) {
                super.updateItem(e, empty);
                setText(empty || e == null ? null
                        : e.getNombre() + " | " + e.getEstado()
                        + " | " + e.getCiudad());
            }
        });

        lstUsuarios.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Usuario u, boolean empty) {
                super.updateItem(u, empty);
                setText(empty || u == null ? null
                        : u.getNombre() + " | " + u.getCorreo()
                        + " | " + u.getRolUsuario());
            }
        });

        lstCompras.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Compra c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null
                        : c.getUsuario().getNombre()
                        + " | " + c.getEvento().getNombre()
                        + " | $" + c.getTotal()
                        + " | " + c.getEstado());
            }
        });
    }

    @FXML
    public void onPublicarEvento() {
        Evento evento = lstEventos.getSelectionModel().getSelectedItem();
        if (evento == null) { lblEstadoEvento.setText("Selecciona un evento."); return; }
        EventoController.ResultadoEstado r = eventoController.publicar(evento.getIdEvento());
        lblEstadoEvento.setText(r.mensaje());
        cargarEventos();
    }

    @FXML
    public void onPausarEvento() {
        Evento evento = lstEventos.getSelectionModel().getSelectedItem();
        if (evento == null) { lblEstadoEvento.setText("Selecciona un evento."); return; }
        EventoController.ResultadoEstado r = eventoController.pausar(evento.getIdEvento());
        lblEstadoEvento.setText(r.mensaje());
        cargarEventos();
    }

    @FXML
    public void onCancelarEvento() {
        Evento evento = lstEventos.getSelectionModel().getSelectedItem();
        if (evento == null) { lblEstadoEvento.setText("Selecciona un evento."); return; }
        EventoController.ResultadoEstado r = eventoController.cancelar(evento.getIdEvento());
        lblEstadoEvento.setText(r.mensaje());
        cargarEventos();
    }

    @FXML
    public void onEliminarEvento() {
        Evento evento = lstEventos.getSelectionModel().getSelectedItem();
        if (evento == null) { lblEstadoEvento.setText("Selecciona un evento."); return; }
        adminController.eliminarEvento(evento.getIdEvento());
        lblEstadoEvento.setText("Evento eliminado.");
        cargarEventos();
    }

    // ── Usuarios ──────────────────────────────────────────────────────────────

    @FXML
    public void onEliminarUsuario() {
        Usuario usuario = lstUsuarios.getSelectionModel().getSelectedItem();
        if (usuario == null) { lblEstadoUsuario.setText("Selecciona un usuario."); return; }
        adminController.eliminarUsuario(usuario.getIdUsuario());
        lblEstadoUsuario.setText("Usuario eliminado.");
        cargarUsuarios();
    }

    // ── Compras ───────────────────────────────────────────────────────────────

    @FXML
    public void onCancelarCompra() {
        Compra compra = lstCompras.getSelectionModel().getSelectedItem();
        if (compra == null) { lblEstadoCompra.setText("Selecciona una compra."); return; }
        boolean ok = adminController.cancelarCompra(compra.getIdCompra());
        lblEstadoCompra.setText(ok ? "Compra cancelada." : "No se pudo cancelar.");
        cargarCompras();
    }

    @FXML
    public void onReembolsarCompra() {
        Compra compra = lstCompras.getSelectionModel().getSelectedItem();
        if (compra == null) { lblEstadoCompra.setText("Selecciona una compra."); return; }
        boolean ok = adminController.reembolsarCompra(compra.getIdCompra());
        lblEstadoCompra.setText(ok ? "Reembolso procesado." : "No se pudo reembolsar.");
        cargarCompras();
    }

    // ── Métricas ──────────────────────────────────────────────────────────────

    @FXML
    public void onActualizarMetricas() {
        cargarMetricas();
    }

    @FXML
    public void onCerrarSesion() {
        GestorSesion.getInstance().cerrarSesion();
        navegarA("/fxml/LoginView.fxml");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void cargarEventos() {
        List<Evento> eventos = adminController.listarEventos();
        lstEventos.setItems(FXCollections.observableArrayList(eventos));
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = adminController.listarUsuarios();
        lstUsuarios.setItems(FXCollections.observableArrayList(usuarios));
    }

    private void cargarCompras() {
        List<Compra> compras = adminController.listarCompras();
        lstCompras.setItems(FXCollections.observableArrayList(compras));
    }

    private void cargarMetricas() {
        lblTotalVentas.setText("Total ventas: " + adminController.totalVentas());
        lblIngresoTotal.setText("Ingreso total: $" + adminController.ingresoTotal());
        lblTotalCancelaciones.setText("Cancelaciones: " + adminController.totalCancelaciones());
        lblTasaCancelacion.setText(String.format("Tasa cancelación: %.1f%%",
                adminController.tasaCancelacion()));
    }

    private void navegarA(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) lblMensaje.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}