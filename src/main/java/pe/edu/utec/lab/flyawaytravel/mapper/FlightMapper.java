package pe.edu.utec.lab.flyawaytravel.mapper;

import org.springframework.stereotype.Component;
import pe.edu.utec.lab.flyawaytravel.dto.response.FlightResponseDto;
import pe.edu.utec.lab.flyawaytravel.model.Flight;

@Component
public class FlightMapper {

    public FlightResponseDto toResponseDto(Flight flight) {
        if (flight == null) return null;
        
        return new FlightResponseDto(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getAirlineName(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getAvailableSeats(),
                flight.getStatus()
        );
    }
}
