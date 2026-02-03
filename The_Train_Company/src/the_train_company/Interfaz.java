package the_train_company;

import java.awt.HeadlessException;
import javax.swing.*;
import java.time.format.DateTimeFormatter;

public class Interfaz {

    public static void informarTerminal(String msg) {
        System.out.println("[" + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(Main.fechaInicial.plusMinutes(Main.minutoTotal)) + "] " + msg);
    }

    public static void mostrarError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public static String preguntar(String msg, String placeholder) {
        String respuesta;
        while (true) {
            respuesta = (String) JOptionPane.showInputDialog(null, msg, "Pregunta", JOptionPane.QUESTION_MESSAGE, null, null, placeholder);
            if (respuesta == null) {
                break;
            }
            if (respuesta.contains("\\")) {
                mostrarError("El texto no puede contener el simbolo '\\'");
            } else {
                break;
            }
        }
        return respuesta;
    }

    public static String preguntarNumero(String msg, String placeholder) {
        while (true) {
            try {
                String respuesta = (String) JOptionPane.showInputDialog(null, msg, "Pregunta", JOptionPane.QUESTION_MESSAGE, null, null, placeholder);
                if (respuesta == null) {
                    return null;
                }
                Double.valueOf(respuesta);
                return respuesta;
            } catch (HeadlessException | NumberFormatException e) {
                mostrarError("Solo se pueden ingresar numeros.");
            }
        }
    }

    public static String preguntarEntero(String msg, String placeholder) {
        while (true) {
            try {
                String respuesta = (String) JOptionPane.showInputDialog(null, msg, "Pregunta", JOptionPane.QUESTION_MESSAGE, null, null, placeholder);
                if (respuesta == null) {
                    return null;
                }
                Integer.valueOf(respuesta);
                return respuesta;
            } catch (HeadlessException | NumberFormatException e) {
                mostrarError("Solo se pueden ingresar numeros enteros.");
            }
        }
    }

    public static boolean preguntarSiNo(String msg) {
        return JOptionPane.showConfirmDialog(null, msg, "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static int menu(Object msg, String[] opciones) {
        return JOptionPane.showOptionDialog(null, msg, "Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);
    }

    public static void informar(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}
