package cl.SalmonesAustral.Mortalidad.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * DTO para actualizar un registro de mortalidad existente (PUT) No incluye ID porque se obtiene del path parameter
 */
public record UpdateMortalidadRequest(
    @PositiveOrZero(message = "El ID de la jaula debe ser un número positivo o cero")
    int jaulaId,

    @PositiveOrZero(message = "El porcentaje de mortalidad debe ser un número positivo o cero")
    double porcentaje,

    @PositiveOrZero(message = "Los días deben ser un número positivo o cero")
    int dias
 
) {
}
