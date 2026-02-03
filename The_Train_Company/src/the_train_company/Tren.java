package the_train_company;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import org.ini4j.Ini;
import org.ini4j.Profile;
import java.io.IOException;

public class Tren extends Thread {

    int idEstacionActual, idEstacionInicial, minutoActual = 0, segundosEnParada;
    ListaCircularDoble vagon = new ListaCircularDoble();
    Estacion[] estaciones;
    Grafo grafo;
    JTable asientosTable;
    JTable filasTable;

    public Estacion[] getEstaciones() {
        return estaciones;
    }

    public Tren(Estacion[] estaciones, Grafo grafo, int idPrimeraEstacion) {
        try {
            Ini ini = new Ini(new File("configuracion.ini"));
            Profile.Section seccionConfiguracion = ini.get("Configuracion");
            int cFilasRegulares = Integer.parseInt(seccionConfiguracion.get("cantidadFilasRegulares"));
            int cFilasDiscapacitados = Integer.parseInt(seccionConfiguracion.get("cantidadFilasDiscapacitados"));
            int cAsientosRegulares = Integer.parseInt(seccionConfiguracion.get("cantidadAsientosRegulares"));
            int cAsientosDiscapacitados = Integer.parseInt(seccionConfiguracion.get("cantidadAsientosDiscapacitados"));

            Main.empresa = seccionConfiguracion.get("empresa");

            Main.precioKilometro = Double.parseDouble(seccionConfiguracion.get("costoKilometro"));

            for (int i = 0; i < cFilasRegulares; i++) {
                vagon.insertar(new NodoListaCircularDoble(cAsientosRegulares, false));
            }
            for (int i = 0; i < cFilasDiscapacitados; i++) {
                vagon.insertar(new NodoListaCircularDoble(cAsientosDiscapacitados, true));
            }
            this.estaciones = estaciones;
            this.grafo = grafo;
            this.segundosEnParada = Integer.parseInt(seccionConfiguracion.get("segundosEnParada"));
            this.idEstacionActual = idPrimeraEstacion;
            this.idEstacionInicial = idPrimeraEstacion;

        } catch (IOException e) {
            Interfaz.mostrarError("Error al leer configuracion.ini");
        }
    }

    private String crearLineasMapa(int cantidad, int posicionTren) {
        String lineas = "";
        for (int i = 0; i < cantidad; i++) {
            if (i == posicionTren) {
                lineas += "[-]";
            } else {
                lineas += " - ";
            }
        }
        return lineas;
    }

    public void actualizarTablaAsientos() {
        NodoListaCircularDoble auxiliar = vagon.getInicio();
        for (int i = 0; i < vagon.getCantidad(); i++) {
            if (auxiliar.isDiscapacitado()) {
                asientosTable.getModel().setValueAt("Discapacitado", 0, i);
            } else {
                asientosTable.getModel().setValueAt("Regular", 0, i);
            }
            NodoPila asientosAuxiliar = null;
            if (auxiliar.getPilaAsientos().estaVacia() == false) {
                asientosAuxiliar = auxiliar.getPilaAsientos().getCima();
            }
            for (int a = 1; a < vagon.getAsientosMaximos() + 1; a++) {
                if (asientosAuxiliar != null) {
                    asientosTable.getModel().setValueAt(asientosAuxiliar.getPersona(), a, i);
                    asientosAuxiliar = asientosAuxiliar.getSiguiente();
                } else {
                    if (a >= auxiliar.getPilaAsientos().getMaximo() + 1) {
                        asientosTable.getModel().setValueAt("-------", a, i);
                    } else {
                        asientosTable.getModel().setValueAt("", a, i);
                    }
                }
                if (asientosAuxiliar != null) {
                    asientosAuxiliar = asientosAuxiliar.getSiguiente();
                }
                if (a == asientosTable.getRowCount()) {
                    break;
                }
            }
            auxiliar = auxiliar.getSiguiente();
        }
    }

    public void actualizarTablaFilas() {
        JTable auxiliarTable = new JTable(new DefaultTableModel());
        ((DefaultTableModel) auxiliarTable.getModel()).addRow(new Object[]{});
        int indexAuxiliarEstacion = idEstacionInicial;
        for (int i = 0; i < estaciones.length * 2; i += 2) {
            ((DefaultTableModel) auxiliarTable.getModel()).addColumn("");
            ((DefaultTableModel) auxiliarTable.getModel()).setValueAt(estaciones[indexAuxiliarEstacion] + " (" + indexAuxiliarEstacion + ") Regular", 0, i);
            int personasEnFila = 0;
            NodoCola auxiliar = estaciones[indexAuxiliarEstacion].getColaRegular().getFrente();
            while (auxiliar != null) {
                personasEnFila++;
                if (auxiliarTable.getRowCount() - 1 <= personasEnFila) {
                    ((DefaultTableModel) auxiliarTable.getModel()).addRow(new Object[]{});
                }
                ((DefaultTableModel) auxiliarTable.getModel()).setValueAt(auxiliar.getPersona(), personasEnFila, i);
                auxiliar = auxiliar.getSiguiente();
            }

            ((DefaultTableModel) auxiliarTable.getModel()).addColumn("");
            ((DefaultTableModel) auxiliarTable.getModel()).setValueAt(estaciones[indexAuxiliarEstacion] + " (" + indexAuxiliarEstacion + ") Discapacitados", 0, i + 1);
            personasEnFila = 0;
            auxiliar = estaciones[indexAuxiliarEstacion].getColaDiscapacitados().getFrente();
            while (auxiliar != null) {
                personasEnFila++;
                if (auxiliarTable.getRowCount() - 1 <= personasEnFila) {
                    ((DefaultTableModel) auxiliarTable.getModel()).addRow(new Object[]{});
                }
                ((DefaultTableModel) auxiliarTable.getModel()).setValueAt(auxiliar.getPersona(), personasEnFila, i + 1);
                auxiliar = auxiliar.getSiguiente();
            }
            indexAuxiliarEstacion = grafo.getSiguiente(indexAuxiliarEstacion);
        }
        filasTable.setModel(auxiliarTable.getModel());
    }

    public void subirPasajeros(Cola colaDeEstacion, boolean discapacitado) {
        NodoCola auxiliarPersona = colaDeEstacion.getFrente();
        while (auxiliarPersona != null) {
            NodoListaCircularDoble auxiliarFilaAsientos = vagon.getInicio();
            boolean inicial = true;
            while (auxiliarFilaAsientos != vagon.getInicio() || inicial) {
                inicial = false;
                if (auxiliarFilaAsientos.isDiscapacitado() == discapacitado && auxiliarFilaAsientos.getPilaAsientos().getLargo() < auxiliarFilaAsientos.getPilaAsientos().getMaximo()) {
                    colaDeEstacion.removerNodo(auxiliarPersona);
                    actualizarTablaFilas();
                    Interfaz.informarTerminal(auxiliarPersona.getPersona().toString() + " se ha subido en la estación " + estaciones[idEstacionActual].toString() + " (" + idEstacionActual + ")");
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                    }
                    auxiliarPersona.getPersona().setEstado("EN_CAMINO");
                    auxiliarFilaAsientos.getPilaAsientos().apilar(auxiliarPersona.getPersona());
                    actualizarTablaAsientos();
                    break;
                }
                auxiliarFilaAsientos = auxiliarFilaAsientos.getSiguiente();
            }
            auxiliarPersona = auxiliarPersona.getSiguiente();
        }
    }

    public void bajarPasajeros() {
        NodoListaCircularDoble auxiliarFilaAsientos = vagon.getInicio();
        boolean inicial = true;
        while (auxiliarFilaAsientos != vagon.getInicio() || inicial) {
            inicial = false;
            if (!auxiliarFilaAsientos.getPilaAsientos().estaVacia()) {
                NodoPila persona = auxiliarFilaAsientos.getPilaAsientos().getCima();
                if (persona.getPersona().getEstacionDestinoId() == idEstacionActual) {
                    auxiliarFilaAsientos.getPilaAsientos().extrae(persona);
                    actualizarTablaAsientos();
                    double precioPagado = grafo.dijkstra(persona.getPersona().getEstacionOrigenId(), persona.getPersona().getEstacionDestinoId())[0] * Main.precioKilometro;
                    Interfaz.informarTerminal(persona.getPersona().toString() + " se ha bajado en la estación " + estaciones[idEstacionActual].toString() + " (" + idEstacionActual + ") y ha pagado " + precioPagado);
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                    }
                    persona.getPersona().setEstado("COMPLETADO");
                }
            }
            auxiliarFilaAsientos = auxiliarFilaAsientos.getSiguiente();
        }
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        JFrame frame = new JFrame("Tren");
        frame.setLayout(new GridLayout(9, 1));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(920, 1000);

        Font fuente = new Font("Arial", Font.PLAIN, 24);

        Image imagen = new ImageIcon("./logo.png").getImage().getScaledInstance(300, 180, Image.SCALE_SMOOTH);;
        JLabel logoLabel = new JLabel(new ImageIcon(imagen));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel tituloLabel = new JLabel(Main.empresa);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 36));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel mapaLabel = new JLabel();
        mapaLabel.setFont(fuente);
        mapaLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel estadisticasLabel = new JLabel();
        estadisticasLabel.setFont(fuente);
        estadisticasLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel estacionLabel = new JLabel();
        estacionLabel.setFont(fuente);
        estacionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        asientosTable = new JTable(vagon.getAsientosMaximos() + 1, vagon.getCantidad());
        actualizarTablaAsientos();
        asientosTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        asientosTable.setEnabled(false);

        filasTable = new JTable();
        actualizarTablaFilas();
        filasTable.setEnabled(false);

        for (int i = 0; i < vagon.getAsientosMaximos() + 1; i++) {
            asientosTable.setRowHeight(i, 20);
        }

        frame.add(logoLabel);
        frame.add(tituloLabel);
        frame.add(mapaLabel);
        frame.add(estadisticasLabel);
        frame.add(estacionLabel);
        frame.add(asientosTable);
        frame.add(filasTable);
        frame.setVisible(true);
        frame.pack();

        try {
            while (true) {
                Estacion estacionActual = estaciones[idEstacionActual];
                estacionLabel.setText("<html>Estación Siguiente: " + estacionActual.getNombre() + " (" + idEstacionActual + ")</html>");
                sleep(100);
                minutoActual++;

                if (grafo.getSiguiente(idEstacionActual) == -1) {
                    Interfaz.mostrarError("Las vías del tren se cortan.");
                    return;
                }
                int[] distanciaASiguienteEstacion = grafo.dijkstra(idEstacionActual, grafo.getSiguiente(idEstacionActual));
                double kilometroActual = ((double) distanciaASiguienteEstacion[0]) / ((double) distanciaASiguienteEstacion[1]) * minutoActual;

                int indexAuxiliarEstacion = idEstacionInicial;
                String mapa = "";
                for (int i = 0; i < estaciones.length; i++) {
                    if (idEstacionActual == indexAuxiliarEstacion) {
                        mapa += crearLineasMapa(grafo.dijkstra(indexAuxiliarEstacion, grafo.getSiguiente(indexAuxiliarEstacion))[0] / 5, (int) kilometroActual / 5) + indexAuxiliarEstacion;
                    } else {
                        mapa += crearLineasMapa(grafo.dijkstra(indexAuxiliarEstacion, grafo.getSiguiente(indexAuxiliarEstacion))[0] / 5, -1) + indexAuxiliarEstacion;
                    }
                    indexAuxiliarEstacion = grafo.getSiguiente(indexAuxiliarEstacion);
                }

                mapaLabel.setText("<html>" + mapa + "</html>");

                estadisticasLabel.setText("<html>Kilómetros: " + (int) kilometroActual + " / " + distanciaASiguienteEstacion[0] + " | Minutos: " + minutoActual + " / " + distanciaASiguienteEstacion[1] + "</html>");
                Main.minutoTotal += minutoActual;
                if (minutoActual >= distanciaASiguienteEstacion[1]) {
                    estacionLabel.setText("<html>Estación Actual: " + estacionActual + " (" + idEstacionActual + ")</html>");
                    Interfaz.informarTerminal("El tren ha llegado a la estación " + estacionActual + " (" + idEstacionActual + ")");
                    bajarPasajeros();

                    subirPasajeros(estacionActual.getColaDiscapacitados(), true);
                    subirPasajeros(estacionActual.getColaRegular(), false);

                    idEstacionActual = grafo.getSiguiente(idEstacionActual);
                    minutoActual = 0;
                    sleep(segundosEnParada * 1000);
                    Interfaz.informarTerminal("El tren esta en camino a la estación " + estaciones[idEstacionActual] + " (" + idEstacionActual + ")");
                }
            }
        } catch (InterruptedException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
