package com.app.account_service.mappers;

import com.app.account_service.dtos.requests.SignUpRequest;
import com.app.account_service.dtos.responses.AdminUserDetailsResponse;
import com.app.account_service.dtos.responses.AdminUserListItemResponse;
import com.app.account_service.dtos.responses.UserProfileResponse;
import com.app.account_service.entities.Country;
import com.app.account_service.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "countryName", source = "user.country.name")
    UserProfileResponse toProfileResponse(User user, boolean premium);

    AdminUserListItemResponse toAdminListItem(User user, boolean premium);

    @Mapping(target = "countryName", source = "user.country.name")
    AdminUserDetailsResponse toAdminDetails(User user, boolean premium,
                                            Instant premiumStartedAt, Instant premiumExpiresAt);

    default User toNewUser(SignUpRequest request, Country country, String encodedPassword) {
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
