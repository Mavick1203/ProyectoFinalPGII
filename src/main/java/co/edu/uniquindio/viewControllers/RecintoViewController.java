package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.RecintoController;
import co.edu.uniquindio.controllers.ZonaController;
import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.Zona;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class RecintoViewController {

    // ── Recinto ───────────────────────────────────────────────────────────────
    @FXML private TextField     txtNombreRecinto;
    @FXML private TextField     txtDireccionRecinto;
    @FXML private TextField     txtCiudadRecinto;
    @FXML private ListView<Recinto> lstRecintos;
    @FXML private Label         lblEstadoRecinto;

    // ── Zona ──────────────────────────────────────────────────────────────────
    @FXML private TextField     txtNombreZona;
    @FXML private TextField     txtCapacidadZona;
    @FXML private TextField     txtPrecioBaseZona;
    @FXML private TextField     txtConfiguracionZona;
    @FXML private ListView<Zona> lstZonas;
    @FXML private Label         lblEstadoZona;

    @FXML private Label lblMensaje;

    private RecintoController recintoController;
    private ZonaController    zonaController;

    public void setControllers(RecintoController recintoController,
                               ZonaController zonaController) {
        this.recintoController = recintoController;
        this.zonaController    = zonaController;
        cargarRecintos();
    }

    @FXML
    public void initialize() {
        lstRecintos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Recinto r, boolean empty) {
                super.updateItem(r, empty);
                setText(empty || r == null ? null
                        : r.getNombre() + " | " + r.getCiudad()
                        + " | Cap: " + r.getCapacidad());
            }
        });

        lstZonas.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Zona z, boolean empty) {
                super.updateItem(z, empty);
                setText(empty || z == null ? null
                        : z.getNombre() + " | Cap: " + z.getCapacidad()
                        + " | $" + z.getPrecioBase());
            }
        });

        lstRecintos.getSelectionModel().selectedItemProperty()
                .addListener((obs, ant, sel) -> {
                    if (sel != null) cargarZonasDeRecinto(sel);
                });
    }

    // ── Acciones Recinto ──────────────────────────────────────────────────────

    @FXML
    public void onCrearRecinto() {
        RecintoController.ResultadoRecinto r = recintoController.crearRecinto(
                txtNombreRecinto.getText(),
                txtDireccionRecinto.getText(),
                txtCiudadRecinto.getText());

        lblEstadoRecinto.setStyle(r.exitoso()
                ? "-fx-text-fill: #0f6e56;" : "-fx-text-fill: #a32d2d;");
        lblEstadoRecinto.setText(r.mensaje());

        if (r.exitoso()) {
            limpiarFormRecinto();
            cargarRecintos();
        }
    }

    @FXML
    public void onEliminarRecinto() {
        Recinto sel = lstRecintos.getSelectionModel().getSelectedItem();
        if (sel == null) { lblEstadoRecinto.setText("Selecciona un recinto."); return; }

        RecintoController.ResultadoRecinto r =
                recintoController.eliminarRecinto(sel.getIdRecinto());
        lblEstadoRecinto.setText(r.mensaje());
        cargarRecintos();
        lstZonas.setItems(FXCollections.emptyObservableList());
    }

    // ── Acciones Zona ─────────────────────────────────────────────────────────

    @FXML
    public void onCrearZona() {
        Recinto recinto = lstRecintos.getSelectionModel().getSelectedItem();
        if (recinto == null) {
            lblEstadoZona.setText("Selecciona primero un recinto.");
            return;
        }

        int capacidad;
        double precioBase;
        try {
            capacidad  = Integer.parseInt(txtCapacidadZona.getText().trim());
            precioBase = Double.parseDouble(txtPrecioBaseZona.getText().trim());
        } catch (NumberFormatException e) {
            lblEstadoZona.setText("Capacidad y precio deben ser numéricos.");
            return;
        }

        ZonaController.ResultadoZona r = zonaController.crearZona(
                txtNombreZona.getText(),
                capacidad,
                precioBase,
                txtConfiguracionZona.getText(),
                recinto.getIdRecinto());

        lblEstadoZona.setStyle(r.exitoso()
                ? "-fx-text-fill: #0f6e56;" : "-fx-text-fill: #a32d2d;");
        lblEstadoZona.setText(r.mensaje());

        if (r.exitoso()) {
            limpiarFormZona();
            cargarZonasDeRecinto(recinto);
        }
    }

    @FXML
    public void onEliminarZona() {
        Recinto recinto = lstRecintos.getSelectionModel().getSelectedItem();
        Zona    zona    = lstZonas.getSelectionModel().getSelectedItem();
        if (recinto == null || zona == null) {
            lblEstadoZona.setText("Selecciona un recinto y una zona.");
            return;
        }

        RecintoController.ResultadoRecinto r =
                recintoController.eliminarZona(recinto.getIdRecinto(), zona.getIdZona());
        lblEstadoZona.setText(r.mensaje());
        cargarZonasDeRecinto(recinto);
    }

    @FXML
    public void onVolver() {
        navegarA("/fxml/admin/AdminView.fxml");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void cargarRecintos() {
        List<Recinto> recintos = recintoController.listarTodos();
        lstRecintos.setItems(FXCollections.observableArrayList(recintos));
    }

    private void cargarZonasDeRecinto(Recinto recinto) {
        List<Zona> zonas = recintoController.listarZonas(recinto.getIdRecinto());
        lstZonas.setItems(FXCollections.observableArrayList(zonas));
    }

    private void limpiarFormRecinto() {
        txtNombreRecinto.clear();
        txtDireccionRecinto.clear();
        txtCiudadRecinto.clear();
    }

    private void limpiarFormZona() {
        txtNombreZona.clear();
        txtCapacidadZona.clear();
        txtPrecioBaseZona.clear();
        txtConfiguracionZona.clear();
    }

    private void navegarA(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) lstRecintos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}