package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.AsientoController;
import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.enums.EstadoAsiento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class AsientoViewController {

    @FXML private TextField     txtFila;
    @FXML private TextField     txtNumero;
    @FXML private TextField     txtIdZona;
    @FXML private ListView<Asiento> lstAsientos;
    @FXML private Label         lblEstadoSeleccionado;
    @FXML private Label         lblMensaje;

    private AsientoController asientoController;
    private String            idZonaActual;

    public void setAsientoController(AsientoController asientoController) {
        this.asientoController = asientoController;
    }

    /** Puede ser llamado desde otro ViewController para pre-cargar una zona. */
    public void cargarZona(String idZona) {
        this.idZonaActual = idZona;
        txtIdZona.setText(idZona);
        refrescarMapa();
    }

    @FXML
    public void initialize() {
        lstAsientos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Asiento a, boolean empty) {
                super.updateItem(a, empty);
                if (empty || a == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText("Fila " + a.getFila() + " — N°" + a.getNumero()
                            + "  [" + a.getEstado() + "]");
                    switch (a.getEstado()) {
                        case DISPONIBLE -> setStyle("-fx-text-fill: #0f6e56;");
                        case RESERVADO  -> setStyle("-fx-text-fill: #ba7517;");
                        case VENDIDO    -> setStyle("-fx-text-fill: #185fa5;");
                        case BLOQUEADO  -> setStyle("-fx-text-fill: #a32d2d;");
                    }
                }
            }
        });

        lstAsientos.getSelectionModel().selectedItemProperty()
                .addListener((obs, ant, sel) -> {
                    if (sel != null)
                        lblEstadoSeleccionado.setText(
                                "Seleccionado: Fila " + sel.getFila()
                                        + " — N°" + sel.getNumero()
                                        + " | Estado: " + sel.getEstado());
                });
    }

    // ── Crear ─────────────────────────────────────────────────────────────────

    @FXML
    public void onCrearAsiento() {
        String idZona = txtIdZona.getText().trim();
        if (idZona.isBlank()) { lblMensaje.setText("Ingresa el ID de zona."); return; }

        int numero;
        try {
            numero = Integer.parseInt(txtNumero.getText().trim());
        } catch (NumberFormatException e) {
            lblMensaje.setText("El número de asiento debe ser un entero.");
            return;
        }

        AsientoController.ResultadoAsiento r =
                asientoController.crearAsiento(txtFila.getText(), numero, idZona);
        lblMensaje.setStyle(r.exitoso()
                ? "-fx-text-fill: #0f6e56;" : "-fx-text-fill: #a32d2d;");
        lblMensaje.setText(r.mensaje());

        if (r.exitoso()) {
            this.idZonaActual = idZona;
            txtFila.clear();
            txtNumero.clear();
            refrescarMapa();
        }
    }

    // ── Estado ────────────────────────────────────────────────────────────────

    @FXML
    public void onHabilitar() {
        cambiarEstado(EstadoAsiento.DISPONIBLE);
    }

    @FXML
    public void onBloquear() {
        cambiarEstado(EstadoAsiento.BLOQUEADO);
    }

    @FXML
    public void onEliminar() {
        Asiento sel = lstAsientos.getSelectionModel().getSelectedItem();
        if (sel == null) { lblMensaje.setText("Selecciona un asiento."); return; }

        AsientoController.ResultadoAsiento r =
                asientoController.eliminarAsiento(sel.getIdAsiento());
        lblMensaje.setText(r.mensaje());
        refrescarMapa();
    }

    @FXML
    public void onCargarMapa() {
        String idZona = txtIdZona.getText().trim();
        if (idZona.isBlank()) { lblMensaje.setText("Ingresa el ID de zona."); return; }
        this.idZonaActual = idZona;
        refrescarMapa();
    }

    @FXML
    public void onVolver() {
        navegarA("/fxml/admin/AdminView.fxml");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void cambiarEstado(EstadoAsiento estado) {
        Asiento sel = lstAsientos.getSelectionModel().getSelectedItem();
        if (sel == null) { lblMensaje.setText("Selecciona un asiento."); return; }

        AsientoController.ResultadoAsiento r = estado == EstadoAsiento.DISPONIBLE
                ? asientoController.habilitar(sel.getIdAsiento())
                : asientoController.bloquear(sel.getIdAsiento());

        lblMensaje.setText(r.mensaje());
        refrescarMapa();
    }

    private void refrescarMapa() {
        if (idZonaActual == null) return;
        List<Asiento> mapa = asientoController.mapaAsientos(idZonaActual);
        lstAsientos.setItems(FXCollections.observableArrayList(mapa));
        lblMensaje.setText("Zona cargada — " + mapa.size() + " asientos.");
    }

    private void navegarA(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) lstAsientos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}