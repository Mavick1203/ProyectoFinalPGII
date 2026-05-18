package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.CompraController;
import co.edu.uniquindio.model.*;
import co.edu.uniquindio.model.enums.EstadoAsiento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CompraViewController {

    @FXML private Label lblEvento;
    @FXML private ComboBox<Zona> cmbZona;
    @FXML private ComboBox<Asiento> cmbAsiento;
    @FXML private ComboBox<String> cmbTipoEntrada;
    @FXML private ComboBox<String> cmbMetodoPago;
    @FXML private TextField txtDatosPago;
    @FXML private CheckBox chkParqueadero;
    @FXML private CheckBox chkSeguro;
    @FXML private CheckBox chkMerchandising;
    @FXML private CheckBox chkAccesoPreferencial;
    @FXML private CheckBox chkVIP;
    @FXML private Label lblTotal;
    @FXML private Label lblMensaje;

    private CompraController compraController;
    private Evento evento;
    private Compra compraActual;

    public void setCompraController(CompraController compraController) {
        this.compraController = compraController;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
        lblEvento.setText(evento.getNombre() + " — " + evento.getFechaHora().toLocalDate());
        cargarZonas();
    }

    @FXML
    public void initialize() {
        cmbTipoEntrada.setItems(FXCollections.observableArrayList(
                "GENERAL", "NUMERADA", "VIP"));
        cmbTipoEntrada.setValue("GENERAL");

        cmbMetodoPago.setItems(FXCollections.observableArrayList(
                "EFECTIVO", "TARJETACREDITO", "TARJETADEBITO", "PSE"));
        cmbMetodoPago.setValue("EFECTIVO");

        cmbZona.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Zona zona, boolean empty) {
                super.updateItem(zona, empty);
                setText(empty || zona == null ? null
                        : zona.getNombre() + " ($" + zona.getPrecioBase() + ")");
            }
        });
        cmbZona.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Zona zona, boolean empty) {
                super.updateItem(zona, empty);
                setText(empty || zona == null ? null
                        : zona.getNombre() + " ($" + zona.getPrecioBase() + ")");
            }
        });

        cmbAsiento.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Asiento asiento, boolean empty) {
                super.updateItem(asiento, empty);
                setText(empty || asiento == null ? null
                        : "Fila " + asiento.getFila() + " - N° " + asiento.getNumero());
            }
        });
        cmbAsiento.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Asiento asiento, boolean empty) {
                super.updateItem(asiento, empty);
                setText(empty || asiento == null ? null
                        : "Fila " + asiento.getFila() + " - N° " + asiento.getNumero());
            }
        });
    }

    @FXML
    public void onZonaSeleccionada() {
        Zona zona = cmbZona.getValue();
        if (zona == null) return;

        List<Asiento> disponibles = zona.getAsientos().stream()
                // Usamos la flecha (lambda) para comparar el estado del asiento con el Enum
                .filter(asiento -> asiento.getEstado() == EstadoAsiento.DISPONIBLE)
                .toList();

        cmbAsiento.setItems(FXCollections.observableArrayList(disponibles));
    }

    @FXML
    public void onCrearCompra() {
        Zona zona = cmbZona.getValue();
        Asiento asiento = cmbAsiento.getValue();
        String tipoEntrada = cmbTipoEntrada.getValue();

        if (zona == null || asiento == null) {
            lblMensaje.setText("Selecciona una zona y un asiento.");
            return;
        }

        List<String> servicios = obtenerServiciosSeleccionados();
        CompraController.ResultadoCompra resultado =
                compraController.crearCompra(evento, zona, asiento, tipoEntrada, servicios);

        if (resultado.exitoso()) {
            compraActual = resultado.compra();
            lblTotal.setText("Total: $" + compraActual.getTotal());
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText(resultado.mensaje());
        } else {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText(resultado.mensaje());
        }
    }

    @FXML
    public void onPagar() {
        if (compraActual == null) {
            lblMensaje.setText("Primero crea la compra.");
            return;
        }

        String metodo = cmbMetodoPago.getValue();
        String datos  = txtDatosPago.getText();

        CompraController.ResultadoCompra resultado =
                compraController.procesarPago(compraActual.getIdCompra(), metodo, datos);

        if (resultado.exitoso()) {
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText(resultado.mensaje());
            navegarA("/fxml/usuario/HistorialView.fxml");
        } else {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText(resultado.mensaje());
        }
    }

    @FXML
    public void onCancelar() {
        navegarA("/fxml/usuario/EventosView.fxml");
    }

    private void cargarZonas() {
        if (evento.getRecinto() != null) {
            cmbZona.setItems(FXCollections.observableArrayList(
                    evento.getRecinto().getZonas()));
        }
    }

    private List<String> obtenerServiciosSeleccionados() {
        List<String> servicios = new ArrayList<>();
        if (chkParqueadero.isSelected())        servicios.add("PARQUEADERO");
        if (chkSeguro.isSelected())             servicios.add("SEGUROCANCELACION");
        if (chkMerchandising.isSelected())      servicios.add("MERCHANDISING");
        if (chkAccesoPreferencial.isSelected()) servicios.add("ACCESOPREFERENCIAL");
        if (chkVIP.isSelected())                servicios.add("VIP");
        return servicios;
    }

    private void navegarA(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) lblEvento.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}