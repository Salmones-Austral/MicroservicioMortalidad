package cl.SalmonesAustral.Mortalidad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.SalmonesAustral.Mortalidad.modelo.Mortalidad;

public interface MortalidadRepository extends JpaRepository<Mortalidad, Integer> {

    List<Mortalidad> findByJaulaId(int jaulaId);
}