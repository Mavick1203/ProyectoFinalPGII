package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.IncidenciaController;
import co.edu.uniquindio.model.Incidencia;
import co.edu.uniquindio.model.enums.TipoIncidencia;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class IncidenciaViewController {

    @FXML private ComboBox<String>     cmbTipo;
    @FXML private TextArea             txtDescripcion;
    @FXML private TextField            txtIdEntidad;
    @FXML private ComboBox<String>     cmbTipoEntidad;
    @FXML private ComboBox<String>     cmbFiltroTipo;
    @FXML private DatePicker           dateFiltroDesde;
    @FXML private DatePicker           dateFiltroHasta;
    @FXML private ListView<Incidencia> lstIncidencias;
    @FXML private Label                lblMensaje;

    private IncidenciaController incidenciaController;

    public void setIncidenciaController(IncidenciaController incidenciaController) {
        this.incidenciaController = incidenciaController;
        cargarTodas();
    }

    @FXML
    public void initialize() {
        List<String> tipos = List.of(
                "DOBLE_COMPRA", "ERROR_PAGO",
                "CANCELACION_MASIVA", "ASIENTO_NO_DISPONIBLE", "OTRO");

        cmbTipo.setItems(FXCollections.observableArrayList(tipos));
        cmbTipo.setValue("OTRO");

        cmbTipoEntidad.setItems(FXCollections.observableArrayList(
                "Evento", "Compra", "Usuario"));
        cmbTipoEntidad.setValue("Evento");

        List<String> filtroTipos = new java.util.ArrayList<>(tipos);
        filtroTipos.add(0, "TODOS");
        cmbFiltroTipo.setItems(FXCollections.observableArrayList(filtroTipos));
        cmbFiltroTipo.setValue("TODOS");

        lstIncidencias.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Incidencia inc, boolean empty) {
                super.updateItem(inc, empty);
                setText(empty || inc == null ? null
                        : inc.getTipo() + " | "
                        + inc.getFecha().toLocalDate() + " | "
                        + inc.getTipoEntidadAfectada() + " | "
                        + inc.getDescripcion());
            }
        });
    }

    @FXML
    public void onRegistrar() {
        String tipoStr     = cmbTipo.getValue();
        String descripcion = txtDescripcion.getText().trim();
        String idEntidad   = txtIdEntidad.getText().trim();
        String tipoEntidad = cmbTipoEntidad.getValue();

        TipoIncidencia tipo;
        try {
            tipo = TipoIncidencia.valueOf(tipoStr);
        } catch (Exception e) {
            mostrarError("Tipo de incidencia inválido.");
            return;
        }

        IncidenciaController.ResultadoIncidencia r =
                incidenciaController.registrar(tipo, descripcion,
                        idEntidad, tipoEntidad);

        if (r.exitoso()) {
            mostrarExito(r.mensaje());
            txtDescripcion.clear();
            txtIdEntidad.clear();
            cargarTodas();
        } else {
            mostrarError(r.mensaje());
        }
    }

    @FXML
    public void onFiltrar() {
        String tipoStr = cmbFiltroTipo.getValue();

        LocalDateTime desde = dateFiltroDesde.getValue() != null
                ? dateFiltroDesde.getValue().atStartOfDay() : null;
        LocalDateTime hasta = dateFiltroHasta.getValue() != null
                ? dateFiltroHasta.getValue().atTime(LocalTime.MAX) : null;

        List<Incidencia> resultado =
                incidenciaController.filtrar(tipoStr, desde, hasta);
        lstIncidencias.setItems(FXCollections.observableArrayList(resultado));
    }

    @FXML
    public void onVolver() {
        navegarA("/fxml/admin/AdminView.fxml");
    }

    private void cargarTodas() {
        List<Incidencia> todas = incidenciaController.listarTodas();
        lstIncidencias.setItems(FXCollections.observableArrayList(todas));
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
            Stage stage = (Stage) lstIncidencias.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarError("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}