package the_train_company;

import java.time.LocalDateTime;
import java.io.IOException;
import java.io.File;
import javax.swing.JFileChooser;
import org.ini4j.Ini;
import org.ini4j.Profile;

public class Main {

    public static String empresa = "";
    public static LocalDateTime fechaInicial = LocalDateTime.now();
    public static int minutoTotal = 0;
    public static Tren tren;
    public static double precioKilometro = 0;

    public static void main(String[] args) {
        Grafo grafo;
        Estacion[] estaciones;
        try {
            Ini ini = new Ini(new File("estaciones.ini"));
            grafo = new Grafo(ini.size());
            estaciones = new Estacion[ini.size()];

            for (String seccionNombre : ini.keySet()) {
                Profile.Section seccion = ini.get(seccionNombre);
                Estacion estacion = new Estacion(seccionNombre);
                estaciones[Integer.parseInt(seccion.get("id"))] = estacion;
                grafo.agregarArista(Integer.parseInt(seccion.get("id")), Integer.parseInt(seccion.get("siguiente")), new int[]{Integer.parseInt(seccion.get("kilometros")), Integer.parseInt(seccion.get("minutos"))});
            }
        } catch (IOException e) {
            Interfaz.mostrarError("Error al leer estaciones.ini");
            return;
        }

        tren = new Tren(estaciones, grafo, 0);

        tren.start();
        while (true) {
            String[] opciones = {"Vender Tiquete", "Agregar Personas Desde Archivo", "Ver Créditos"};
            int seleccion = Interfaz.menu("¡Bienvenido al sistema de trenes!", opciones);

            switch (seleccion) {
                case 0:
                    Persona nuevoPasajero = new Persona();
                    if (nuevoPasajero == null || nuevoPasajero.getNombreApellidos() == null) {
                        break;
                    }
                    if (nuevoPasajero.isDiscapacitado()) {
                        tren.getEstaciones()[nuevoPasajero.getEstacionOrigenId()].getColaDiscapacitados().encolar(nuevoPasajero);
                    } else {
                        tren.getEstaciones()[nuevoPasajero.getEstacionOrigenId()].getColaRegular().encolar(nuevoPasajero);
                    }
                    tren.actualizarTablaFilas();
                    Interfaz.informar("Pasajero agregado exitosamente.");
                    break;
                case 1:
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        Persona.cargarDesdeArchivo(fileChooser.getSelectedFile().getAbsolutePath());
                        tren.actualizarTablaFilas();
                    }
                    break;
                case 2:
                    Interfaz.informar("Sistema de trenes creado por:\nJeffry Samuel Eduarte Rojas\nCristopher Rodríguez Fernández\nBrenda Karina Rojas Cortés\nMelani Tamar Vargas Arrieta");
                    break;
                case -1:
                    System.exit(0);
                    break;
                default:
                    Interfaz.mostrarError("Opción no válida.");
            }
        }
    }
}
