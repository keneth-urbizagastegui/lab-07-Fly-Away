package pe.edu.utec.lab.flyawaytravel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utec.lab.flyawaytravel.model.FlightStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightResponseDto {
    private Long id;
    private String flightNumber;
    private String airlineName;
    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;
    private Integer availableSeats;
    private FlightStatus status;
}
