package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.EventoController;
import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.enums.CategoriaEvento;
import co.edu.uniquindio.patterns.creacionales.FactoryMethod.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CrearEventoViewController {

    @FXML private TextField     txtNombre;
    @FXML private TextField     txtCiudad;
    @FXML private TextArea      txtDescripcion;
    @FXML private TextArea      txtPoliticas;
    @FXML private ComboBox<String>  cmbCategoria;
    @FXML private ComboBox<Recinto> cmbRecinto;
    @FXML private DatePicker    dateFecha;
    @FXML private TextField     txtHora;
    @FXML private Label         lblMensaje;

    private EventoController eventoController;

    public void setEventoController(EventoController eventoController) {
        this.eventoController = eventoController;
        cargarDatos();
    }

    @FXML
    public void initialize() {
        cmbCategoria.setItems(FXCollections.observableArrayList(
                "CONCIERTO", "TEATRO", "CONFERENCIA",
                "DEPORTE", "FESTIVAL", "OTRO"));
        cmbCategoria.setValue("CONCIERTO");

        cmbRecinto.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Recinto r, boolean empty) {
                super.updateItem(r, empty);
                setText(empty || r == null ? null
                        : r.getNombre() + " — " + r.getCiudad());
            }
        });
        cmbRecinto.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Recinto r, boolean empty) {
                super.updateItem(r, empty);
                setText(empty || r == null ? null
                        : r.getNombre() + " — " + r.getCiudad());
            }
        });
    }

    private void cargarDatos() {
        List<Recinto> recintos =
                eventoController.getRecintoServicio().listarTodos();
        cmbRecinto.setItems(FXCollections.observableArrayList(recintos));
        if (!recintos.isEmpty()) cmbRecinto.setValue(recintos.get(0));
    }

    @FXML
    public void onCrearEvento() {
        String nombre      = txtNombre.getText().trim();
        String ciudad      = txtCiudad.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String politicas   = txtPoliticas.getText().trim();
        String categoriaStr= cmbCategoria.getValue();
        Recinto recinto    = cmbRecinto.getValue();
        LocalDate fecha    = dateFecha.getValue();
        String horaStr     = txtHora.getText().trim();

        if (nombre.isBlank() || ciudad.isBlank() || recinto == null || fecha == null) {
            mostrarError("Nombre, ciudad, recinto y fecha son obligatorios.");
            return;
        }

        LocalTime hora;
        try {
            hora = LocalTime.parse(horaStr.isBlank() ? "00:00" : horaStr);
        } catch (DateTimeParseException e) {
            mostrarError("Formato de hora inválido. Usa HH:mm (ej: 18:00).");
            return;
        }

        LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);
        if (fechaHora.isBefore(LocalDateTime.now())) {
            mostrarError("La fecha debe ser futura.");
            return;
        }

        EventoFactory factory = resolverFactory(categoriaStr);
        co.edu.uniquindio.model.Evento evento = factory.crearEvento(
                nombre, descripcion, ciudad, fechaHora, recinto);

        if (!politicas.isBlank()) evento.setPoliticas(politicas);

        EventoController.ResultadoEstado r = eventoController.crearEvento(evento);

        if (r.exitoso()) {
            mostrarExito(r.mensaje());
            limpiarFormulario();
        } else {
            mostrarError(r.mensaje());
        }
    }

    @FXML
    public void onVolver() {
        navegarA("/fxml/admin/AdminView.fxml");
    }

    private EventoFactory resolverFactory(String categoria) {
        return switch (categoria) {
            case "TEATRO"      -> new TeatroFactory();
            case "CONFERENCIA" -> new ConferenciaFactory();
            case "DEPORTE"     -> new DeporteFactory();
            case "FESTIVAL"    -> new FestivalFactory();
            default            -> new ConciertoFactory();
        };
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtCiudad.clear();
        txtDescripcion.clear();
        txtPoliticas.clear();
        txtHora.clear();
        dateFecha.setValue(null);
    }

    private void mostrarError(String msg) {
        lblMensaje.setStyle("-fx-text-fill: #a32d2d;");
        lblMensaje.setText(msg);
    }

    private void mostrarExito(String msg) {
        lblMensaje.setStyle("-fx-text-fill: #0f6e56;");
        lblMensaje.setText(msg);
    }

    private void navegarA(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            AdminViewController vc = loader.getController();
            vc.setControllers(
                    co.edu.uniquindio.App.adminController,
                    co.edu.uniquindio.App.eventoController
            );
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarError("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}