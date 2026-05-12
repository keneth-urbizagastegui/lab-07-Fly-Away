package pe.edu.utec.lab.flyawaytravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.utec.lab.flyawaytravel.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByFlightId(Long flightId);

    boolean existsByUserIdAndFlightId(Long userId, Long flightId);

    @Query("SELECT b FROM Booking b " +
           "JOIN b.flight f " +
           "WHERE b.user.id = :userId " +
           "AND b.status = pe.edu.utec.lab.flyawaytravel.model.BookingStatus.CONFIRMED " +
           "AND f.departureTime < :arrivalTime " +
           "AND f.arrivalTime > :departureTime")
    List<Booking> findConflictingBookings(
            @Param("userId") Long userId,
            @Param("departureTime") LocalDateTime departureTime,
            @Param("arrivalTime") LocalDateTime arrivalTime
    );
}
