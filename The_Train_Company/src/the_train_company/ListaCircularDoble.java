package the_train_company;

public class ListaCircularDoble {

    private NodoListaCircularDoble inicio;
    private NodoListaCircularDoble ultimo;

    public NodoListaCircularDoble getUltimo() {
        return ultimo;
    }

    public NodoListaCircularDoble getInicio() {
        return inicio;
    }

    public ListaCircularDoble() {
        this.inicio = null;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    public void insertar(NodoListaCircularDoble nuevoNodo) {
        if (estaVacia()) {
            inicio = nuevoNodo;
            ultimo = nuevoNodo;
            inicio.setSiguiente(inicio);
            inicio.setAnterior(inicio);
        } else {
            nuevoNodo.setSiguiente(inicio);
            nuevoNodo.setAnterior(ultimo);
            inicio.setAnterior(nuevoNodo);
            ultimo.setSiguiente(nuevoNodo);
            ultimo = nuevoNodo;
        }
    }

    public void eliminar() {
        if (!estaVacia()) {
            if (inicio.getSiguiente() == inicio) {
                inicio = null;
                ultimo = null;
            } else {
                NodoListaCircularDoble temp = inicio;
                inicio = inicio.getSiguiente();
                inicio.setAnterior(ultimo);
                ultimo.setSiguiente(inicio);
                temp.setSiguiente(null);
                temp.setAnterior(null);
            }
        }
    }

    public int getCantidad() {
        NodoListaCircularDoble auxiliar = inicio;
        int cantidad = 0;
        if (auxiliar != null) {
            cantidad++;
            auxiliar = auxiliar.getSiguiente();
        }
        while (auxiliar != inicio) {
            cantidad++;
            auxiliar = auxiliar.getSiguiente();
        }
        return cantidad;
    }

    public int getAsientosMaximos() {
        NodoListaCircularDoble auxiliar = inicio;
        int cantidad = 0;
        if (auxiliar != null) {
            cantidad = auxiliar.getPilaAsientos().getMaximo();
            auxiliar = auxiliar.getSiguiente();
        }
        while (auxiliar != inicio) {
            if (auxiliar.getPilaAsientos().getMaximo() > cantidad) {
                cantidad = auxiliar.getPilaAsientos().getMaximo();
            }
            auxiliar = auxiliar.getSiguiente();
        }
        return cantidad;
    }

    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        if (!estaVacia()) {
            NodoListaCircularDoble actual = inicio;
            do {
                texto.append(actual.getPilaAsientos()).append("\n");
                actual = actual.getSiguiente();
            } while (actual != inicio);
            System.out.println();
        }
        return texto.toString();
    }
}
