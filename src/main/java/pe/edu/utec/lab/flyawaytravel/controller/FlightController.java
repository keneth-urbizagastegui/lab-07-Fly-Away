package pe.edu.utec.lab.flyawaytravel.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utec.lab.flyawaytravel.dto.request.BookFlightRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.request.FlightCreateManyRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.request.FlightCreateRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.BookingResponseDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.FlightListResponseDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.FlightResponseDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import pe.edu.utec.lab.flyawaytravel.security.UserPrincipal;
import pe.edu.utec.lab.flyawaytravel.service.BookingService;
import pe.edu.utec.lab.flyawaytravel.service.FlightService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final BookingService bookingService;

    public FlightController(FlightService flightService, BookingService bookingService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<FlightResponseDto> createFlight(
            @Valid @RequestBody FlightCreateRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(flightService.createFlight(request));
    }

    @PostMapping("/create-many")
    public ResponseEntity<Void> createManyFlights(
            @Valid @RequestBody FlightCreateManyRequestDto request) {
        flightService.createManyFlights(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search")
    public ResponseEntity<FlightListResponseDto> searchFlights(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String airlineName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime estDepartureTimeFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime estDepartureTimeTo) {
        return ResponseEntity.ok(flightService.searchFlights(flightNumber, airlineName, estDepartureTimeFrom, estDepartureTimeTo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponseDto> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PostMapping("/book")
    public ResponseEntity<BookingResponseDto> bookFlight(
            @Valid @RequestBody BookFlightRequestDto request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.bookFlight(request, principal.getId()));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }
}
