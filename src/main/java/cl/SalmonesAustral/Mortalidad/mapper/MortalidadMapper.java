package cl.SalmonesAustral.Mortalidad.mapper;

import cl.SalmonesAustral.Mortalidad.modelo.Mortalidad;
import cl.SalmonesAustral.Mortalidad.dto.CreateMortalidadRequest;
import cl.SalmonesAustral.Mortalidad.dto.UpdateMortalidadRequest;

public class MortalidadMapper {
    // Convierte CreateMortalidadRequest a Mortalidad (para POST) El ID se genera automáticamente, se pasa 0
    // temporalmente
    public static Mortalidad toModel(CreateMortalidadRequest request) {
        return new Mortalidad(0, // ID temporal, será asignado por el service/repository
                request.jaulaId(), request.porcentaje(), request.dias());
    }

    // Convierte UpdateMortalidadRequest a Mortalidad (para PUT) El ID se obtiene del path parameter
    public static Mortalidad toModel(int id, UpdateMortalidadRequest request) {
        return new Mortalidad(id, // ID del path parameter
                request.jaulaId(), request.porcentaje(), request.dias());
    }

}
