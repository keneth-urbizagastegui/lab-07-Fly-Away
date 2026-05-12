package pe.edu.utec.lab.flyawaytravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FlightNumberAlreadyExistsException extends RuntimeException {
    public FlightNumberAlreadyExistsException(String flightNumber) {
        super("El número de vuelo '" + flightNumber + "' ya existe");
    }
}
