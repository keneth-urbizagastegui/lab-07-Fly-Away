package pe.edu.utec.lab.flyawaytravel.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utec.lab.flyawaytravel.repository.BookingRepository;
import pe.edu.utec.lab.flyawaytravel.repository.FlightRepository;
import pe.edu.utec.lab.flyawaytravel.repository.UserRepository;

@Service
public class CleanupService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public CleanupService(BookingRepository bookingRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void cleanupAll() {
        bookingRepository.deleteAll();
        flightRepository.deleteAll();
        userRepository.deleteAll();
    }
}
