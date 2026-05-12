package pe.edu.utec.lab.flyawaytravel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ScheduleConflictException extends RuntimeException {
    public ScheduleConflictException() {
        super("Ya tienes una reserva con un vuelo que se superpone en horario");
    }
}
