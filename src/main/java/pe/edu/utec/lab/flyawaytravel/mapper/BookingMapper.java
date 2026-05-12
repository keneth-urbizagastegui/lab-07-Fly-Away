package pe.edu.utec.lab.flyawaytravel.mapper;

import org.springframework.stereotype.Component;
import pe.edu.utec.lab.flyawaytravel.dto.response.BookingResponseDto;
import pe.edu.utec.lab.flyawaytravel.model.Booking;
import pe.edu.utec.lab.flyawaytravel.model.Flight;
import pe.edu.utec.lab.flyawaytravel.model.User;

@Component
public class BookingMapper {

    public BookingResponseDto toResponseDto(Booking booking) {
        if (booking == null) return null;
        
        Flight flight = booking.getFlight();
        User user = booking.getUser();
        
        return new BookingResponseDto(
                booking.getId(),
                booking.getBookingDate(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                flight.getId(),
                flight.getFlightNumber(),
                flight.getAirlineName(),
                flight.getDepartureTime(),
                flight.getArrivalTime()
        );
    }
}
