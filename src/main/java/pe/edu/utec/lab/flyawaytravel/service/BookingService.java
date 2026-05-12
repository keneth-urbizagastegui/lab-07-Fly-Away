package pe.edu.utec.lab.flyawaytravel.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import pe.edu.utec.lab.flyawaytravel.dto.request.BookFlightRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.BookingResponseDto;
import pe.edu.utec.lab.flyawaytravel.event.BookingConfirmedEvent;
import pe.edu.utec.lab.flyawaytravel.exception.FlightNotAvailableException;
import pe.edu.utec.lab.flyawaytravel.exception.NoSeatsAvailableException;
import pe.edu.utec.lab.flyawaytravel.exception.ResourceNotFoundException;
import pe.edu.utec.lab.flyawaytravel.exception.ScheduleConflictException;
import pe.edu.utec.lab.flyawaytravel.mapper.BookingMapper;
import pe.edu.utec.lab.flyawaytravel.model.Booking;
import pe.edu.utec.lab.flyawaytravel.model.BookingStatus;
import pe.edu.utec.lab.flyawaytravel.model.Flight;
import pe.edu.utec.lab.flyawaytravel.model.User;
import pe.edu.utec.lab.flyawaytravel.repository.BookingRepository;
import pe.edu.utec.lab.flyawaytravel.repository.FlightRepository;
import pe.edu.utec.lab.flyawaytravel.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final BookingMapper bookingMapper;

    public BookingService(BookingRepository bookingRepository, 
                          FlightRepository flightRepository, 
                          UserRepository userRepository,
                          ApplicationEventPublisher eventPublisher,
                          BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
        this.bookingMapper = bookingMapper;
    }

    @Transactional
    public BookingResponseDto bookFlight(BookFlightRequestDto request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + userId + " no encontrado"));

        Flight flight = flightRepository.findById(request.getFlightId())
                .orElseThrow(() -> new ResourceNotFoundException("Vuelo con id " + request.getFlightId() + " no encontrado"));

        if (flight.getAvailableSeats() <= 0) {
            throw new NoSeatsAvailableException(flight.getFlightNumber());
        }

        LocalDateTime now = LocalDateTime.now();
        if (!flight.getDepartureTime().isAfter(now)) {
            throw new FlightNotAvailableException(flight.getFlightNumber());
        }

        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                userId,
                flight.getDepartureTime(),
                flight.getArrivalTime()
        );
        if (!conflicts.isEmpty()) {
            throw new ScheduleConflictException();
        }

        Booking booking = Booking.builder()
                .user(user)
                .flight(flight)
                .bookingDate(LocalDateTime.now())
                .customerNames(user.getFirstName() + " " + user.getLastName())
                .status(BookingStatus.CONFIRMED)
                .build();

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Booking saved = bookingRepository.save(booking);
        
        // Desacoplamiento total mediante eventos
        eventPublisher.publishEvent(new BookingConfirmedEvent(this, saved));

        return bookingMapper.toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public BookingResponseDto getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva con id " + id + " no encontrada"));
        return bookingMapper.toResponseDto(booking);
    }
}
