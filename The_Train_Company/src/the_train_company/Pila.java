package the_train_company;

public class Pila {

    private NodoPila cima;
    private int maximo;

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public Pila(int maximo) {
        this.cima = null;
        this.maximo = maximo;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public void apilar(Persona persona) {
        if (getLargo() < maximo) {
            NodoPila nuevoNodo = new NodoPila(persona);
            nuevoNodo.setSiguiente(cima);
            cima = nuevoNodo;
        } else {
            Interfaz.mostrarError("Máximo alcanzado en Pila.");
        }
    }

    public NodoPila extrae(NodoPila nodo) {
        if (cima == null) {
            return null;
        }
        NodoPila actual = this.getCima();
        NodoPila anterior = null;
        while (actual != null) {
            if (actual == nodo) {
                if (anterior != null) {
                    anterior.setSiguiente(actual.getSiguiente());
                    actual.setSiguiente(null);
                    actual = null;
                } else {
                    this.setCima(actual.getSiguiente());
                    actual.setSiguiente(null);
                    actual = null;
                }
                return actual;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return null;
    }

    public Persona desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía.");
        }
        Persona personaCima = cima.getPersona();
        cima = cima.getSiguiente();
        return personaCima;
    }

    public NodoPila getCima() {
        if (estaVacia()) {
            return null;
        }
        return cima;
    }

    public void setCima(NodoPila cima) {
        this.cima = cima;
    }

    public int getLargo() {
        int contador = 0;
        NodoPila auxiliar = cima;
        while (auxiliar != null) {
            contador += 1;
            auxiliar = auxiliar.getSiguiente();
        }
        return contador;
    }

    public void removerNodo(NodoPila nodo) {
        if (nodo == cima) {
            desapilar();
            return;
        }

        NodoPila actual = cima;
        while (actual != null && actual.getSiguiente() != nodo) {
            actual = actual.getSiguiente();
        }

        if (actual != null) {
            actual.setSiguiente(nodo.getSiguiente());
        } else {
            Interfaz.mostrarError("El nodo no se encuentra en la pila.");
        }
    }

    @Override
    public String toString() {
        NodoPila actual = cima;
        StringBuilder texto = new StringBuilder();
        while (actual != null) {
            texto.append(actual.getPersona()).append("\n");
            actual = actual.getSiguiente();
        }
        return texto.toString();
    }
}
