package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.CompraController;
import co.edu.uniquindio.model.Compra;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

public class HistorialViewController {

    @FXML private ListView<Compra> lstCompras;
    @FXML private Label lblDetalle;
    @FXML private Label lblMensaje;

    private CompraController compraController;

    public void setCompraController(CompraController compraController) {
        this.compraController = compraController;
        cargarHistorial();
    }

    @FXML
    public void initialize() {
        lstCompras.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Compra compra, boolean empty) {
                super.updateItem(compra, empty);
                if (empty || compra == null) {
                    setText(null);
                } else {
                    setText(compra.getEvento().getNombre()
                            + " | $" + compra.getTotal()
                            + " | " + compra.getEstado()
                            + " | " + compra.getFechaCreacion().toLocalDate());
                }
            }
        });

        lstCompras.getSelectionModel().selectedItemProperty().addListener(
                (obs, anterior, seleccionada) -> {
                    if (seleccionada != null) mostrarDetalle(seleccionada);
                });
    }

    @FXML
    public void onCancelarCompra() {
        Compra seleccionada = lstCompras.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            lblMensaje.setText("Selecciona una compra.");
            return;
        }
        CompraController.ResultadoCompra resultado =
                compraController.cancelarCompra(seleccionada.getIdCompra());

        if (resultado.exitoso()) {
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText(resultado.mensaje());
            cargarHistorial();
        } else {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText(resultado.mensaje());
        }
    }

    @FXML
    public void onVolver() {
        navegarA("/fxml/usuario/EventosView.fxml");
    }

    private void cargarHistorial() {
        List<Compra> historial = compraController.obtenerHistorial();
        lstCompras.setItems(FXCollections.observableArrayList(historial));
        if (historial.isEmpty()) {
            lblMensaje.setText("No tienes compras registradas.");
        }
    }

    private void mostrarDetalle(Compra compra) {
        StringBuilder sb = new StringBuilder();
        sb.append("Evento: ").append(compra.getEvento().getNombre()).append("\n");
        sb.append("Fecha: ").append(compra.getFechaCreacion().toLocalDate()).append("\n");
        sb.append("Total: $").append(compra.getTotal()).append("\n");
        sb.append("Estado: ").append(compra.getEstado()).append("\n");
        sb.append("Entradas: ").append(compra.getEntradas().size()).append("\n");
        if (!compra.getServiciosAdicionales().isEmpty()) {
            sb.append("Servicios: ")
                    .append(String.join(", ", compra.getServiciosAdicionales()));
        }
        lblDetalle.setText(sb.toString());
    }

    private void navegarA(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) lstCompras.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}