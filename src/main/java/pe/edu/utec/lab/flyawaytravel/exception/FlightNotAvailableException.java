package pe.edu.utec.lab.flyawaytravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FlightNotAvailableException extends RuntimeException {
    public FlightNotAvailableException(String flightNumber) {
        super("El vuelo '" + flightNumber + "' no está disponible para reservas (ya pasó o está en tránsito)");
    }
}
