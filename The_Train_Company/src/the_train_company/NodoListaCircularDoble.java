package the_train_company;

public class NodoListaCircularDoble {

    private Pila pilaAsientos;
    private NodoListaCircularDoble siguiente;
    private NodoListaCircularDoble anterior;
    private boolean discapacitado;

    public NodoListaCircularDoble(Pila pilaAsientos, boolean discapacitado) {
        this.pilaAsientos = pilaAsientos;
        this.siguiente = null;
        this.anterior = null;
        this.discapacitado = discapacitado;
    }

    public NodoListaCircularDoble(int maximo, boolean discapacitado) {
        this.pilaAsientos = new Pila(maximo);
        this.siguiente = null;
        this.anterior = null;
        this.discapacitado = discapacitado;
    }

    public boolean isDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(boolean discapacitado) {
        this.discapacitado = discapacitado;
    }

    public Pila getPilaAsientos() {
        return pilaAsientos;
    }

    public NodoListaCircularDoble getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoListaCircularDoble siguiente) {
        this.siguiente = siguiente;
    }

    public NodoListaCircularDoble getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoListaCircularDoble anterior) {
        this.anterior = anterior;
    }
}
