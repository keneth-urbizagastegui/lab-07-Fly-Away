package pe.edu.utec.lab.flyawaytravel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utec.lab.flyawaytravel.model.BookingStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private Long id;
    private LocalDateTime bookingDate;
    private Long customerId;
    private String customerFirstName;
    private String customerLastName;

    private Long flightId;
    private String flightNumber;
    private String airlineName;
    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;
}
