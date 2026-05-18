package co.edu.uniquindio;

import co.edu.uniquindio.controllers.*;
import co.edu.uniquindio.model.*;
import co.edu.uniquindio.model.enums.*;
import co.edu.uniquindio.patterns.creacionales.FactoryMethod.*;
import co.edu.uniquindio.service.impl.*;
import co.edu.uniquindio.service.interfaces.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class App extends Application {

    public static IUsuarioServicio  usuarioServicio;
    public static IEventoServicio   eventoServicio;
    public static IRecintoServicio  recintoServicio;
    public static IZonaServicio     zonaServicio;
    public static IAsientoServicio  asientoServicio;
    public static ICompraServicio   compraServicio;
    public static IEntradaServicio  entradaServicio;
    public static IIncidenciaServicio incidenciaServicio;
    public static IReporteServicio  reporteServicio;

    @Override
    public void start(Stage stage) throws Exception {
        inicializarServicios();
        cargarDatosPrueba();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/LoginView.fxml"));
        Parent root = loader.load();

        co.edu.uniquindio.viewControllers.LoginViewController vc =
                loader.getController();
        vc.setLoginController(new LoginController(usuarioServicio));

        stage.setScene(new Scene(root));
        stage.setTitle("EventosUQ");
        stage.show();
    }

    private void inicializarServicios() {
        usuarioServicio    = new UsuarioServicioImpl();
        recintoServicio    = new RecintoServicioImpl();
        zonaServicio       = new ZonaServicioImpl(recintoServicio);
        asientoServicio    = new AsientoServicioImpl(zonaServicio);
        eventoServicio     = new EventoServicioImpl();
        compraServicio     = new CompraServicioImpl();
        entradaServicio    = new EntradaServicioImpl(compraServicio);
        incidenciaServicio = new IncidenciaServicioImpl();
        reporteServicio    = new ReporteServicioImpl(compraServicio);
    }

    private void cargarDatosPrueba() {
        // ── Recintos ──────────────────────────────────────────────
        Recinto estadio = new Recinto("Estadio Centenario",
                "Calle 10 #5-20", "Armenia");
        Recinto teatro  = new Recinto("Teatro Municipal",
                "Carrera 14 #8-50", "Pereira");
        recintoServicio.crear(estadio);
        recintoServicio.crear(teatro);

        // ── Zonas + Asientos ──────────────────────────────────────
        Zona vip    = new Zona("VIP",    30, 250000, "NUMERADA");
        Zona pref   = new Zona("Preferencial", 60, 150000, "NUMERADA");
        Zona general= new Zona("General",200,  80000, "GENERAL");

        zonaServicio.crear(vip,     estadio.getIdRecinto());
        zonaServicio.crear(pref,    estadio.getIdRecinto());
        zonaServicio.crear(general, estadio.getIdRecinto());

        // Asientos VIP
        for (int i = 1; i <= 10; i++) {
            Asiento a = new Asiento("A", i);
            asientoServicio.crear(a, vip.getIdZona());
        }
        // Asientos Preferencial
        for (int i = 1; i <= 20; i++) {
            Asiento a = new Asiento("B", i);
            asientoServicio.crear(a, pref.getIdZona());
        }
        // Asientos General
        for (int i = 1; i <= 50; i++) {
            Asiento a = new Asiento("C", i);
            asientoServicio.crear(a, general.getIdZona());
        }

        // ── Zona teatro ───────────────────────────────────────────
        Zona platea = new Zona("Platea", 50, 120000, "NUMERADA");
        zonaServicio.crear(platea, teatro.getIdRecinto());
        for (int i = 1; i <= 20; i++) {
            asientoServicio.crear(new Asiento("P", i), platea.getIdZona());
        }

        // ── Eventos ───────────────────────────────────────────────
        EventoFactory conciertoFactory  = new ConciertoFactory();
        EventoFactory teatroFactory     = new TeatroFactory();
        EventoFactory conferenciaFactory= new ConferenciaFactory();

        Evento concierto = conciertoFactory.crearEvento(
                "Concierto Juanes",
                "El mejor concierto del año",
                "Armenia",
                LocalDateTime.now().plusDays(30),
                estadio);
        concierto.publicar();

        Evento obraTeatro = teatroFactory.crearEvento(
                "Hamlet",
                "Clásico de Shakespeare",
                "Pereira",
                LocalDateTime.now().plusDays(15),
                teatro);
        obraTeatro.publicar();

        Evento conferencia = conferenciaFactory.crearEvento(
                "Tech Summit 2026",
                "Conferencia de tecnología e innovación",
                "Armenia",
                LocalDateTime.now().plusDays(10),
                estadio);
        conferencia.publicar();

        eventoServicio.crear(concierto);
        eventoServicio.crear(obraTeatro);
        eventoServicio.crear(conferencia);

        // ── Usuario extra de prueba ───────────────────────────────
        usuarioServicio.registrar(
                "María López", "maria@mail.com",
                "3209876543", "maria123", RolUsuario.USUARIO);
    }

    public static void main(String[] args) {
        launch(args);
    }
}