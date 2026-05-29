package cl.SalmonesAustral.Mortalidad.modelo;

public class Mortalidad {

    private int id;
    private int jaulaId;
    private double porcentaje;
    private int dias;

    public Mortalidad() {}

    public Mortalidad(int id, int jaulaId, double porcentaje, int dias) {
        this.id = id;
        this.jaulaId = jaulaId;
        this.porcentaje = porcentaje;
        this.dias = dias;
    }

    // getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJaulaId() {
        return jaulaId;
    }

    public void setJaulaId(int jaulaId) {
        this.jaulaId = jaulaId;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
}