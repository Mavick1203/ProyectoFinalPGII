package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.ReporteController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReporteViewController {

    @FXML private ComboBox<String> cmbTipoReporte;
    @FXML private ComboBox<String> cmbFormato;
    @FXML private DatePicker       dateFiltroDesde;
    @FXML private DatePicker       dateFiltroHasta;
    @FXML private TextField        txtIdEvento;
    @FXML private TextField        txtRutaSalida;
    @FXML private Label            lblMensaje;

    private ReporteController reporteController;

    public void setReporteController(ReporteController reporteController) {
        this.reporteController = reporteController;
    }

    @FXML
    public void initialize() {
        cmbTipoReporte.setItems(FXCollections.observableArrayList(
                "VENTAS_POR_PERIODO",
                "OCUPACION_POR_ZONA",
                "INGRESOS_POR_SERVICIO",
                "TASA_CANCELACION",
                "TOP_EVENTOS"));
        cmbTipoReporte.setValue("VENTAS_POR_PERIODO");

        cmbFormato.setItems(FXCollections.observableArrayList("CSV", "PDF"));
        cmbFormato.setValue("CSV");
    }

    @FXML
    public void onExaminar() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Guardar reporte");
        String formato = cmbFormato.getValue();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        formato + " files",
                        "*." + formato.toLowerCase()));
        File archivo = chooser.showSaveDialog(
                txtRutaSalida.getScene().getWindow());
        if (archivo != null) {
            txtRutaSalida.setText(archivo.getAbsolutePath());
        }
    }

    @FXML
    public void onExportar() {
        String tipo      = cmbTipoReporte.getValue();
        String formato   = cmbFormato.getValue();
        String ruta      = txtRutaSalida.getText().trim();
        String idEvento  = txtIdEvento.getText().trim();

        if (ruta.isBlank()) {
            mostrarError("Indica la ruta de salida.");
            return;
        }

        LocalDateTime desde = dateFiltroDesde.getValue() != null
                ? dateFiltroDesde.getValue().atStartOfDay() : null;
        LocalDateTime hasta = dateFiltroHasta.getValue() != null
                ? dateFiltroHasta.getValue().atTime(LocalTime.MAX) : null;

        ReporteController.ResultadoReporte r = switch (tipo) {
            case "VENTAS_POR_PERIODO" ->
                    reporteController.exportarVentasPorPeriodo(
                            desde, hasta, formato, ruta);
            case "OCUPACION_POR_ZONA" ->
                    reporteController.exportarOcupacionPorZona(
                            idEvento, formato, ruta);
            case "INGRESOS_POR_SERVICIO" ->
                    reporteController.exportarIngresosPorServicio(
                            desde, hasta, formato, ruta);
            case "TASA_CANCELACION" ->
                    reporteController.exportarTasaCancelacion(
                            desde, hasta, formato, ruta);
            case "TOP_EVENTOS" ->
                    reporteController.exportarTopEventos(5, formato, ruta);
            default ->
                    new ReporteController.ResultadoReporte(
                            false, "Tipo de reporte no reconocido.");
        };

        if (r.exitoso()) {
            mostrarExito(r.mensaje());
        } else {
            mostrarError(r.mensaje());
        }
    }

    @FXML
    public void onVolver() {
        navegarA("/fxml/admin/AdminView.fxml");
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
            Stage stage = (Stage) txtRutaSalida.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            mostrarError("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}