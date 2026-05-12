package pe.edu.utec.lab.flyawaytravel.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utec.lab.flyawaytravel.dto.request.UserRegisterRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.UserRegisterResponseDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.UserResponseDto;
import pe.edu.utec.lab.flyawaytravel.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(
            @Valid @RequestBody UserRegisterRequestDto request) {
        UserRegisterResponseDto response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
