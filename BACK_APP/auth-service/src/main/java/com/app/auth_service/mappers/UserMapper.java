package com.app.auth_service.mappers;

import com.app.auth_service.dtos.common.CountryDTO;
import com.app.auth_service.dtos.common.UserDTO;
import com.app.auth_service.dtos.requests.SignInRequest;
import com.app.auth_service.dtos.requests.SignUpRequest;
import com.app.auth_service.entities.Country;
import com.app.auth_service.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring", uses = Country.class)
public interface UserMapper {
    UserDTO toDto(User user);

    List<UserDTO> toDtoList(List<User> users);

    User toEntity(UserDTO dto);

    List<User> toEntityList(List<UserDTO> drinksDto);

    @Mapping(target = "id", ignore = true)
    void updateEntity(UserDTO dto, @MappingTarget User users);

    default User fromSignUpRequestToEntity(SignUpRequest request, Country country, PasswordEncoder passwordEncoder){
        User user = new User();
        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());
        user.setBirthDate(request.birthDate());
        user.setCountry(country);

        String encodedPassword = passwordEncoder.encode(request.password());
        user.setPassword(encodedPassword);
        return user;
    }

}
