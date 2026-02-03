package the_train_company;

public class Cola {

    private NodoCola frente;
    private NodoCola fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public void encolar(Persona persona) {
        NodoCola nuevoNodo = new NodoCola(persona);
        if (estaVacia()) {
            frente = nuevoNodo;
        } else {
            fin.setPersona(persona);
        }

        fin = nuevoNodo;
    }

    public Persona desencolar() {
        if (estaVacia()) {
            return null;
        }
        Persona personaFrente = frente.getPersona();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null;
        }

        return personaFrente;
    }

    public NodoCola getFrente() {
        return frente;
    }

    public void setFrente(NodoCola frente) {
        this.frente = frente;
    }

    public NodoCola getFin() {
        return fin;
    }

    public void setFin(NodoCola fin) {
        this.fin = fin;
    }

    public void removerNodo(NodoCola nodo) {
        if (estaVacia()) {
            return;
        }

        NodoCola actual = frente;
        NodoCola anterior = null;

        if (actual == nodo) {
            frente = frente.getSiguiente();
            if (frente == null) {
                fin = null;
            }
            return;
        }

        while (actual != null && actual != nodo) {
            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (actual == null) {
            throw new IllegalArgumentException("El nodo no está en la cola.");
        }

        anterior.setSiguiente(actual.getSiguiente());

        if (actual == fin) {
            fin = anterior;
        }
    }

    @Override
    public String toString() {
        NodoCola actual = frente;
        StringBuilder texto = new StringBuilder();
        while (actual != null) {
            texto.append(actual.getPersona()).append("\n");
            actual = actual.getSiguiente();
        }
        return texto.toString();
    }
}
