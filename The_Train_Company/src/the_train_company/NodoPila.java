package the_train_company;

public class NodoPila {

    private Persona persona;
    private NodoPila siguiente;

    public NodoPila(Persona dato) {
        this.persona = dato;
        this.siguiente = null;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public NodoPila getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPila siguiente) {
        this.siguiente = siguiente;
    }
}
