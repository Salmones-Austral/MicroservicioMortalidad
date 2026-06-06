package cl.SalmonesAustral.Mortalidad.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "mortalidad")
public class Mortalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 
    
    @Column(name = "jaula_id", nullable = false)
    private int jaulaId;
    
    @Column(name = "porcentaje", nullable = false)
    private double porcentaje;
    
    @Column(name = "dias", nullable = false)
    private int dias;

    public Mortalidad() {}

    public Mortalidad(Integer id, int jaulaId, double porcentaje, int dias) {
        this.id = id;
        this.jaulaId = jaulaId;
        this.porcentaje = porcentaje;
        this.dias = dias;
    }

    // GETTERS Y SETTERS
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public int getJaulaId() { return jaulaId; }
    public void setJaulaId(int jaulaId) { this.jaulaId = jaulaId; }
    public double getPorcentaje() { return porcentaje; }
    public void setPorcentaje(double porcentaje) { this.porcentaje = porcentaje; }
    public int getDias() { return dias; }
    public void setDias(int dias) { this.dias = dias; }
}