package the_train_company;

public class Estacion {

    String nombre;
    Cola colaRegular;
    Cola colaDiscapacitados;

    public Estacion(String nombre) {
        this.nombre = nombre;
        this.colaRegular = new Cola();
        this.colaDiscapacitados = new Cola();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cola getColaRegular() {
        return colaRegular;
    }

    public Cola getColaDiscapacitados() {
        return colaDiscapacitados;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
