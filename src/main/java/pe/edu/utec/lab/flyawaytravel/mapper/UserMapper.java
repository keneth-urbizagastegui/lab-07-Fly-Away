package pe.edu.utec.lab.flyawaytravel.mapper;

import org.springframework.stereotype.Component;
import pe.edu.utec.lab.flyawaytravel.dto.response.UserResponseDto;
import pe.edu.utec.lab.flyawaytravel.model.User;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user) {
        if (user == null) return null;
        
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }
}
