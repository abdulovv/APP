package com.app.auth_service.mappers;

import com.app.auth_service.dtos.requests.SignUpRequest;
import com.app.auth_service.dtos.responses.UserResponse;
import com.app.auth_service.entities.Country;
import com.app.auth_service.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "countryName", source = "country.name")
    UserResponse toResponse(User user);

    default User toEntity(SignUpRequest request, Country country, String encodedPassword) {
        return User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(encodedPassword)
                .birthDate(request.birthDate())
                .country(country)
                .build();
    }
}
