package pe.edu.utec.lab.flyawaytravel.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utec.lab.flyawaytravel.dto.request.LoginRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.LoginResponseDto;
import pe.edu.utec.lab.flyawaytravel.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
