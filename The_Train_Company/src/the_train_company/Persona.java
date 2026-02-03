package the_train_company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.io.File;
import org.ini4j.Ini;
import org.ini4j.Profile;
import java.io.IOException;

public class Persona {

    String nombreApellidos, estado;
    int edad, estacionOrigenId, estacionDestinoId;
    boolean discapacitado;

    public Persona(String nombreApellidos, int edad, int estacionOrigenId, int estacionDestinoId, boolean discapacitado) {
        this.nombreApellidos = nombreApellidos;
        this.edad = edad;
        this.estacionOrigenId = estacionOrigenId;
        this.estacionDestinoId = estacionDestinoId;
        this.discapacitado = discapacitado;
        this.estado = "EN_COLA";
    }

    public Persona() {
        //Agrega los datos de la persona
        String nombre = Interfaz.preguntar("Ingrese el nombre del pasajero", "");
        if (nombre == null) {
            return;
        }

        String edad_string = Interfaz.preguntarEntero("Ingrese edad del pasajero", "");
        if (edad_string == null) {
            return;
        }
        int edad = Integer.parseInt(edad_string);

        String[] estacionesStrings = new String[Main.tren.getEstaciones().length];
        for (int i = 0; i < estacionesStrings.length; i++) {
            estacionesStrings[i] = Main.tren.getEstaciones()[i].toString();
        }

        int estacionOrigenId = 0;
        while (true) {
            estacionOrigenId = Interfaz.menu("Ingrese la estación de origen del pasajero", estacionesStrings);
            if (estacionOrigenId == -1) {
                return;
            }
            if (estacionOrigenId < 0 || estacionOrigenId >= Main.tren.getEstaciones().length) {
                Interfaz.mostrarError("Estación de origen no encontrada.");
            } else {
                break;
            }
        }

        int estacionDestinoId = 0;
        while (true) {
            estacionDestinoId = Interfaz.menu("Ingrese la estación de destino del pasajero", estacionesStrings);
            if (estacionDestinoId == -1) {
                return;
            }
            if (estacionDestinoId < 0 || estacionDestinoId >= Main.tren.getEstaciones().length) {
                Interfaz.mostrarError("Estación de origen no encontrada.");
            } else {
                break;
            }
        }

        int cantidadKilometrosViaje = Main.tren.grafo.dijkstra(estacionOrigenId, estacionDestinoId)[0];

        boolean discapacitado = Interfaz.preguntarSiNo("¿El pasajero es discapacitado?");

        if (Interfaz.preguntarSiNo("Cantidad de kilometros: " + cantidadKilometrosViaje
                + "\nPrecio por kilometro: " + Main.precioKilometro
                + "\nTotal: " + (cantidadKilometrosViaje * Main.precioKilometro)
                + "\n¿Esta seguro de que desea comprar el tiquete?")) {
            //controla el agregar nuevos pasajeros
            this.nombreApellidos = nombre;
            this.edad = edad;
            this.estacionOrigenId = estacionOrigenId;
            this.estacionDestinoId = estacionDestinoId;
            this.discapacitado = discapacitado;
            this.estado = "EN_COLA";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("facturas.txt", true))) {
                writer.newLine();
                writer.write("[" + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(Main.fechaInicial.plusMinutes(Main.minutoTotal)) + "]--------\n"
                        + nombre
                        + "\nDe estacion: " + Main.tren.getEstaciones()[estacionOrigenId].getNombre() + " (" + estacionOrigenId + ")"
                        + "\nHacia estacion: " + Main.tren.getEstaciones()[estacionDestinoId].getNombre() + " (" + estacionDestinoId + ")"
                        + "\nCantidad Kilometros: " + cantidadKilometrosViaje
                        + "\nPrecio por kilometro: " + Main.precioKilometro
                        + "\nPrecio total: " + (cantidadKilometrosViaje * Main.precioKilometro)
                        + "\n--------");
            } catch (IOException e) {
            }
        }
    }

    public static void cargarDesdeArchivo(String ruta) {
        try {
            Ini ini = new Ini(new File(ruta));
            for (String seccionNombre : ini.keySet()) {
                Profile.Section seccion = ini.get(seccionNombre);
                Persona persona = new Persona(seccionNombre, Integer.parseInt(seccion.get("edad")), Integer.parseInt(seccion.get("estacionOrigenId")), Integer.parseInt(seccion.get("estacionDestinoId")), seccion.get("discapacitado").equalsIgnoreCase("true"));
                if (Main.tren.estaciones[Integer.parseInt(seccion.get("estacionOrigenId"))] != null) {
                    if (persona.isDiscapacitado()) {
                        Main.tren.estaciones[Integer.parseInt(seccion.get("estacionOrigenId"))].getColaDiscapacitados().encolar(persona);
                    } else {
                        Main.tren.estaciones[Integer.parseInt(seccion.get("estacionOrigenId"))].getColaRegular().encolar(persona);
                    }
                }
            }
        } catch (IOException e) {
            Interfaz.mostrarError("Error al leer " + ruta);
        }
    }

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getEstacionOrigenId() {
        return estacionOrigenId;
    }

    public void setEstacionOrigenId(int estacionOrigenId) {
        this.estacionOrigenId = estacionOrigenId;
    }

    public int getEstacionDestinoId() {
        return estacionDestinoId;
    }

    public void setEstacionDestinoId(int estacionDestinoId) {
        this.estacionDestinoId = estacionDestinoId;
    }

    public boolean isDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(boolean discapacitado) {
        this.discapacitado = discapacitado;
    }

    @Override
    public String toString() {
        if (discapacitado) {
            return this.nombreApellidos + " (" + this.estacionOrigenId + " a " + this.estacionDestinoId + " | D)";
        } else {
            return this.nombreApellidos + " (" + this.estacionOrigenId + " a " + this.estacionDestinoId + " | R)";
        }
    }
}
