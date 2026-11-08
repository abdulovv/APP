package com.app.account_service.mappers;

import com.app.account_service.dtos.requests.SignUpRequest;
import com.app.account_service.dtos.responses.AdminUserDetailsResponse;
import com.app.account_service.dtos.responses.AdminUserListItemResponse;
import com.app.account_service.dtos.responses.UserProfileResponse;
import com.app.account_service.entities.Country;
import com.app.account_service.entities.PremiumSubscription;
import com.app.account_service.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "countryName", source = "user.country.name")
    @Mapping(target = "startedAt", source = "subscription.startedAt")
    @Mapping(target = "expiresAt", source = "subscription.expiresAt")
    @Mapping(target = "cancelledAt", source = "subscription.cancelledAt")
    UserProfileResponse toProfileResponse(User user, PremiumSubscription subscription, boolean premium);


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
