package cl.SalmonesAustral.Mortalidad.mapper;

import cl.SalmonesAustral.Mortalidad.modelo.Mortalidad;
import cl.SalmonesAustral.Mortalidad.dto.CreateMortalidadRequest;
import cl.SalmonesAustral.Mortalidad.dto.UpdateMortalidadRequest;

public class MortalidadMapper {
    
    // Para POST
    public static Mortalidad toModel(CreateMortalidadRequest request) {
        return new Mortalidad(
                null, //null para el insert correcto y que se genere el ID automáticamente
                request.jaulaId(), 
                request.porcentaje(), 
                request.dias()
        );
    }

    // Para PUT
    public static Mortalidad toModel(Integer id, UpdateMortalidadRequest request) {
        return new Mortalidad(
                id, 
                request.jaulaId(), 
                request.porcentaje(), 
                request.dias()
        );
    }
}