package pe.edu.utec.lab.flyawaytravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.utec.lab.flyawaytravel.model.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);

    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findByFlightNumberContainingIgnoreCase(String flightNumber);

    List<Flight> findByAirlineNameContainingIgnoreCase(String airlineName);

    @Query("SELECT f FROM Flight f WHERE " +
           "LOWER(f.flightNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(f.airlineName) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Flight> searchByFlightNumberOrAirlineName(@Param("query") String query);

    List<Flight> findByDepartureTimeBetween(LocalDateTime from, LocalDateTime to);
    List<Flight> findByDepartureTimeGreaterThanEqual(LocalDateTime from);
    List<Flight> findByDepartureTimeLessThanEqual(LocalDateTime to);

    @Query("SELECT f FROM Flight f WHERE " +
           "(LOWER(f.flightNumber) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(f.airlineName) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "f.departureTime BETWEEN :from AND :to")
    List<Flight> searchByQueryAndDateRange(
            @Param("query") String query,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}
