package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.RegistroController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroViewController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private PasswordField txtContrasenia;
    @FXML private PasswordField txtConfirmarContrasenia;
    @FXML private Label lblMensaje;

    private RegistroController registroController;

    public void setRegistroController(RegistroController registroController) {
        this.registroController = registroController;
    }

    @FXML
    public void onRegistrar() {
        RegistroController.ResultadoRegistro resultado = registroController.registrar(
                txtNombre.getText(),
                txtCorreo.getText(),
                txtTelefono.getText(),
                txtContrasenia.getText(),
                txtConfirmarContrasenia.getText()
        );

        if (resultado.exitoso()) {
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText(resultado.mensaje());
            navegarA("/fxml/LoginView.fxml");
        } else {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText(resultado.mensaje());
        }
    }

    @FXML
    public void onIrALogin() {
        navegarA("/fxml/LoginView.fxml");
    }

    private void navegarA(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) txtNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}
