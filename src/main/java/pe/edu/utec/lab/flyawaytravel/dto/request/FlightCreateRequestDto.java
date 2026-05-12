package pe.edu.utec.lab.flyawaytravel.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import pe.edu.utec.lab.flyawaytravel.dto.validation.ValidFlightDates;

import java.time.LocalDateTime;

@Data
@ValidFlightDates
public class FlightCreateRequestDto {

    @NotBlank(message = "El número de vuelo es requerido")
    @Pattern(
        regexp = "^[A-Z]{2,3}[0-9]{3}$",
        message = "El número de vuelo debe ser de 2-3 letras mayúsculas seguidas de 3 números (ej: AA984)"
    )
    private String flightNumber;

    @NotBlank(message = "El nombre de la aerolínea es requerido")
    private String airlineName;

    @NotNull(message = "La hora de salida es requerida")
    private LocalDateTime estDepartureTime;

    @NotNull(message = "La hora de llegada es requerida")
    private LocalDateTime estArrivalTime;

    @NotNull(message = "Los asientos disponibles son requeridos")
    @Min(value = 1, message = "Los asientos disponibles deben ser mayores a 0")
    private Integer availableSeats;
}
