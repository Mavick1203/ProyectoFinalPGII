package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.LoginController;
import co.edu.uniquindio.model.enums.RolUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasenia;
    @FXML private Label lblMensaje;

    private LoginController loginController;

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    @FXML
    public void onIniciarSesion() {
        String correo = txtCorreo.getText();
        String contrasenia = txtContrasenia.getText();

        LoginController.ResultadoLogin resultado =
                loginController.login(correo, contrasenia);

        if (resultado.exitoso()) {
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText(resultado.mensaje());

            if (resultado.esAdmin()) {
                navegarA("/fxml/admin/AdminView.fxml");
            } else {
                navegarA("/fxml/usuario/EventosView.fxml");
            }
        } else {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText(resultado.mensaje());
        }
    }

    @FXML
    public void onIrARegistro() {
        navegarA("/fxml/RegistroView.fxml");
    }

    private void navegarA(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            lblMensaje.setText("Error al cargar la pantalla.");
            e.printStackTrace();
        }
    }
}