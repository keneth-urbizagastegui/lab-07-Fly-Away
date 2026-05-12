package pe.edu.utec.lab.flyawaytravel.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utec.lab.flyawaytravel.dto.request.UserRegisterRequestDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.UserRegisterResponseDto;
import pe.edu.utec.lab.flyawaytravel.dto.response.UserResponseDto;
import pe.edu.utec.lab.flyawaytravel.exception.EmailAlreadyExistsException;
import pe.edu.utec.lab.flyawaytravel.exception.ResourceNotFoundException;
import pe.edu.utec.lab.flyawaytravel.mapper.UserMapper;
import pe.edu.utec.lab.flyawaytravel.model.User;
import pe.edu.utec.lab.flyawaytravel.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserRegisterResponseDto register(UserRegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        User saved = userRepository.save(user);
        return new UserRegisterResponseDto(saved.getId());
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con id " + id + " no encontrado"));
        return userMapper.toResponseDto(user);
    }
}
