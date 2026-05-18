package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.EventoController;
import co.edu.uniquindio.model.Evento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class EventoViewController {

    @FXML private TextField txtBusqueda;
    @FXML private ComboBox<String> cmbCiudad;
    @FXML private ComboBox<String> cmbCategoria;
    @FXML private ListView<Evento> lstEventos;
    @FXML private Label lblMensaje;

    private EventoController eventoController;

    public void setEventoController(EventoController eventoController) {
        this.eventoController = eventoController;
        cargarFiltros();
        cargarEventos();
    }

    @FXML
    public void initialize() {
        lstEventos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Evento evento, boolean empty) {
                super.updateItem(evento, empty);
                if (empty || evento == null) {
                    setText(null);
                } else {
                    setText(evento.getNombre() + " — " + evento.getCiudad()
                            + " | " + evento.getFechaHora().toLocalDate()
                            + " | " + evento.getCategoria());
                }
            }
        });
    }

    @FXML
    public void onFiltrar() {
        List<Evento> resultado = eventoController.filtrar(
                txtBusqueda.getText(),
                cmbCiudad.getValue(),
                cmbCategoria.getValue(),
                null, null
        );
        lstEventos.setItems(FXCollections.observableArrayList(resultado));
        lblMensaje.setText(resultado.isEmpty() ? "No se encontraron eventos." : "");
    }

    @FXML
    public void onSeleccionarEvento() {
        Evento seleccionado = lstEventos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            lblMensaje.setText("Selecciona un evento.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/usuario/CompraView.fxml"));
            Parent root = loader.load();
            CompraViewController vc = loader.getController();
            vc.setEvento(seleccionado);
            Stage stage = (Stage) lstEventos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al abrir el evento.");
            e.printStackTrace();
        }
    }

    @FXML
    public void onIrAPerfil() {
        navegarA("/fxml/usuario/PerfilView.fxml");
    }

    @FXML
    public void onIrAHistorial() {
        navegarA("/fxml/usuario/HistorialView.fxml");
    }

    @FXML
    public void onCerrarSesion() {
        co.edu.uniquindio.patterns.creacionales.Singleton.GestorSesion
                .getInstance().cerrarSesion();
        navegarA("/fxml/LoginView.fxml");
    }

    private void cargarFiltros() {
        List<String> ciudades = eventoController.ciudadesDisponibles();
        ciudades.add(0, "TODAS");
        cmbCiudad.setItems(FXCollections.observableArrayList(ciudades));
        cmbCiudad.setValue("TODAS");

        cmbCategoria.setItems(FXCollections.observableArrayList(
                "TODOS", "CONCIERTO", "TEATRO", "CONFERENCIA",
                "DEPORTE", "FESTIVAL", "OTRO"));
        cmbCategoria.setValue("TODOS");
    }

    private void cargarEventos() {
        List<Evento> eventos = eventoController.listarPublicados();
        lstEventos.setItems(FXCollections.observableArrayList(eventos));
    }

    private void navegarA(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) lstEventos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}