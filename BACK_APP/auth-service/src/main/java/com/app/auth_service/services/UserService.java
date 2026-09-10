package com.app.auth_service.services;

import com.app.auth_service.dtos.common.UserDTO;
import com.app.auth_service.entities.User;
import com.app.auth_service.exceptions.UserNotFoundException;
import com.app.auth_service.mappers.UserMapper;
import com.app.auth_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }
}
