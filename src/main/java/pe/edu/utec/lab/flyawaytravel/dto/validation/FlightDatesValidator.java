package pe.edu.utec.lab.flyawaytravel.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.edu.utec.lab.flyawaytravel.dto.request.FlightCreateRequestDto;

public class FlightDatesValidator implements ConstraintValidator<ValidFlightDates, FlightCreateRequestDto> {

    @Override
    public boolean isValid(FlightCreateRequestDto request, ConstraintValidatorContext context) {
        if (request.getEstDepartureTime() == null || request.getEstArrivalTime() == null) {
            return true;
        }
        return request.getEstDepartureTime().isBefore(request.getEstArrivalTime());
    }
}
