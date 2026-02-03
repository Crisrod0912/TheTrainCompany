package the_train_company;

public class NodoCola {

    private Persona persona;
    private NodoCola siguiente;

    public NodoCola(Persona dato) {
        this.persona = dato;
        this.siguiente = null;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public NodoCola getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola siguiente) {
        this.siguiente = siguiente;
    }
}
