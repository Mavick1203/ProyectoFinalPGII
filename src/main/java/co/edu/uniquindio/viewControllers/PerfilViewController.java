package co.edu.uniquindio.viewControllers;

import co.edu.uniquindio.controllers.UsuarioController;
import co.edu.uniquindio.model.Usuario;
import co.edu.uniquindio.patterns.creacionales.Singleton.GestorSesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PerfilViewController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private PasswordField txtContraseniaActual;
    @FXML private PasswordField txtNuevaContrasenia;
    @FXML private PasswordField txtConfirmarContrasenia;
    @FXML private Label lblMensaje;

    private UsuarioController usuarioController;

    public void setUsuarioController(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
        cargarDatos();
    }

    private void cargarDatos() {
        Usuario usuario = GestorSesion.getInstance().getUsuarioActual();
        if (usuario != null) {
            txtNombre.setText(usuario.getNombre());
            txtCorreo.setText(usuario.getCorreo());
            txtTelefono.setText(usuario.getTelefono());
        }
    }

    @FXML
    public void onActualizarPerfil() {
        Usuario usuario = GestorSesion.getInstance().getUsuarioActual();
        if (usuario == null) return;

        UsuarioController.ResultadoUsuario resultado = usuarioController.actualizarPerfil(
                usuario.getIdUsuario(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtTelefono.getText()
        );

        if (resultado.exitoso()) {
            lblMensaje.setStyle("-fx-text-fill: green;");
        } else {
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
        lblMensaje.setText(resultado.mensaje());
    }

    @FXML
    public void onCambiarContrasenia() {
        Usuario usuario = GestorSesion.getInstance().getUsuarioActual();
        if (usuario == null) return;

        UsuarioController.ResultadoUsuario resultado = usuarioController.cambiarContrasenia(
                usuario.getIdUsuario(),
                txtContraseniaActual.getText(),
                txtNuevaContrasenia.getText(),
                txtConfirmarContrasenia.getText()
        );

        if (resultado.exitoso()) {
            lblMensaje.setStyle("-fx-text-fill: green;");
            txtContraseniaActual.clear();
            txtNuevaContrasenia.clear();
            txtConfirmarContrasenia.clear();
        } else {
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
        lblMensaje.setText(resultado.mensaje());
    }

    @FXML
    public void onVolver() {
        navegarA("/fxml/usuario/EventosView.fxml");
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
