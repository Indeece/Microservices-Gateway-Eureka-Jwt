package ru.indeece.authservice.services;

import org.springframework.data.crossstore.ChangeSetPersister;
import ru.indeece.authservice.dto.JwtAuthenticationDto;
import ru.indeece.authservice.dto.RefreshTokenDto;
import ru.indeece.authservice.dto.UserCredentialsDto;
import ru.indeece.authservice.dto.UserDto;

import javax.naming.AuthenticationException;

public interface UserService {
    JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;
    UserDto getUserById(String id) throws ChangeSetPersister.NotFoundException;
    UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException;
    String addUser(UserDto user);
}
