package pe.edu.utec.lab.flyawaytravel.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utec.lab.flyawaytravel.dto.request.FlightCreateManyRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.request.FlightCreateRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.FlightListResponseDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.FlightResponseDto;
import pe.edu.utec.lab.flyawaytravel.exception.FlightNumberAlreadyExistsException;
import pe.edu.utec.lab.flyawaytravel.exception.ResourceNotFoundException;
import pe.edu.utec.lab.flyawaytravel.mapper.FlightMapper;
import pe.edu.utec.lab.flyawaytravel.model.Flight;
import pe.edu.utec.lab.flyawaytravel.model.FlightStatus;
import pe.edu.utec.lab.flyawaytravel.repository.FlightRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    @Transactional
    public FlightResponseDto createFlight(FlightCreateRequestDto request) {
        if (flightRepository.existsByFlightNumber(request.getFlightNumber())) {
            throw new FlightNumberAlreadyExistsException(request.getFlightNumber());
        }

        Flight flight = Flight.builder()
                .flightNumber(request.getFlightNumber())
                .airlineName(request.getAirlineName())
                .departureTime(request.getEstDepartureTime())
                .arrivalTime(request.getEstArrivalTime())
                .availableSeats(request.getAvailableSeats())
                .status(FlightStatus.SCHEDULED)
                .build();

        return flightMapper.toResponseDto(flightRepository.save(flight));
    }

    @Transactional(readOnly = true)
    public FlightListResponseDto searchFlights(
            String flightNumber,
            String airlineName,
            LocalDateTime estDepartureTimeFrom,
            LocalDateTime estDepartureTimeTo) {

        List<Flight> flights;
        boolean hasQuery = flightNumber != null || airlineName != null;
        String query = flightNumber != null ? flightNumber : airlineName;

        if (hasQuery && estDepartureTimeFrom != null && estDepartureTimeTo != null) {
            flights = flightRepository.searchByQueryAndDateRange(query, estDepartureTimeFrom, estDepartureTimeTo);
        } else if (estDepartureTimeFrom != null && estDepartureTimeTo != null) {
            flights = flightRepository.findByDepartureTimeBetween(estDepartureTimeFrom, estDepartureTimeTo);
        } else if (estDepartureTimeFrom != null) {
            flights = flightRepository.findByDepartureTimeGreaterThanEqual(estDepartureTimeFrom);
        } else if (estDepartureTimeTo != null) {
            flights = flightRepository.findByDepartureTimeLessThanEqual(estDepartureTimeTo);
        } else if (flightNumber != null) {
            flights = flightRepository.findByFlightNumberContainingIgnoreCase(flightNumber);
        } else if (airlineName != null) {
            flights = flightRepository.findByAirlineNameContainingIgnoreCase(airlineName);
        } else {
            flights = flightRepository.findAll();
        }

        List<FlightResponseDto> mapped = flights.stream()
                .map(flightMapper::toResponseDto)
                .collect(Collectors.toList());
        return new FlightListResponseDto(mapped);
    }

    @Transactional(readOnly = true)
    public FlightResponseDto getFlightById(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vuelo con id " + id + " no encontrado"));
        return flightMapper.toResponseDto(flight);
    }

    @Transactional(readOnly = true)
    public Flight findEntityById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vuelo con id " + id + " no encontrado"));
    }

    @Transactional
    public void createManyFlights(FlightCreateManyRequestDto request) {
        for (FlightCreateRequestDto input : request.inputs()) {
            createFlight(input);
        }
    }
}
