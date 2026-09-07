package com.app.auth_service.dto.mappers;

import com.app.auth_service.dto.requests.SignUpRequest;
import com.app.auth_service.dto.responses.AuthResponse;
import com.app.auth_service.entities.Country;
import com.app.auth_service.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public class UserMapper {
    public static User mapRequestToEntity(Country country, SignUpRequest request, PasswordEncoder passwordEncoder){
        User user = new User();
        request = standardizeRequestData(request, country.getPhoneCode());

        user.setFirstname(request.firstname());
        user.setLastname(request.lastname());
        user.setEmail(request.email());
        user.setPhoneNumber(request.phoneNumber());
        user.setBirthDate(request.birthDate());
        String encodedPassword = passwordEncoder.encode(request.password());
        user.setPassword(encodedPassword);
        user.setCountry(country);
        return user;
    }

    public static AuthResponse mapUserToResponse(User user){
        return new AuthResponse("Success, " + user.getFirstname() + ", you have signed up");
    }

    private static SignUpRequest standardizeRequestData(SignUpRequest request, String code){
        String fname = capitalize(request.firstname());
        String lname = capitalize(request.lastname());
        String email = request.email().toLowerCase();
        String phoneNumber = request.phoneNumber();
        String password = request.password();
        LocalDate date = request.birthDate();
        return new SignUpRequest(fname, lname, email, phoneNumber, password, date, request.countryId());
    }

    private static String capitalize(String value) {
        value = value.toLowerCase();
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }
}
