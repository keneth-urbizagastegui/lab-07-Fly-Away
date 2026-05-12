package pe.edu.utec.lab.flyawaytravel.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterRequestDto {

    @NotBlank(message = "El nombre es requerido")
    @Pattern(
        regexp = ".*[A-Z].*",
        message = "El nombre debe contener al menos una letra mayúscula (A-Z)"
    )
    private String firstName;

    @NotBlank(message = "El apellido es requerido")
    @Pattern(
        regexp = ".*[A-Z].*",
        message = "El apellido debe contener al menos una letra mayúscula (A-Z)"
    )
    private String lastName;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe tener un formato válido")
    private String email;

    @NotBlank(message = "La contraseña es requerida")
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$",
        message = "La contraseña debe tener mínimo 8 caracteres, al menos 1 letra y 1 número"
    )
    private String password;
}
