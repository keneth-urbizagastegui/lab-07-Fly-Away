package pe.edu.utec.lab.flyawaytravel.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record FlightCreateManyRequestDto(
        @NotNull @Valid List<FlightCreateRequestDto> inputs
) {}
