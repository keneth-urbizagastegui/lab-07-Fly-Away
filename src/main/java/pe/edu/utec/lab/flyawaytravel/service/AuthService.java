package pe.edu.utec.lab.flyawaytravel.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utec.lab.flyawaytravel.dto.request.LoginRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.LoginResponseDto;
import pe.edu.utec.lab.flyawaytravel.exception.InvalidCredentialsException;
import pe.edu.utec.lab.flyawaytravel.model.User;
import pe.edu.utec.lab.flyawaytravel.repository.UserRepository;
import pe.edu.utec.lab.flyawaytravel.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Email no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponseDto(token);
    }
}
