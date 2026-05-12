package pe.edu.utec.lab.flyawaytravel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookFlightRequestDto {

    @NotNull(message = "El ID del vuelo es requerido")
    private Long flightId;
}
