package pe.edu.utec.lab.flyawaytravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoSeatsAvailableException extends RuntimeException {
    public NoSeatsAvailableException(String flightNumber) {
        super("El vuelo '" + flightNumber + "' no tiene asientos disponibles");
    }
}
