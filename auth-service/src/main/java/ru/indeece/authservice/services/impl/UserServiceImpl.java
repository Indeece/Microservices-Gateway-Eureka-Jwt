package ru.indeece.authservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.indeece.authservice.dto.JwtAuthenticationDto;
import ru.indeece.authservice.dto.RefreshTokenDto;
import ru.indeece.authservice.dto.UserCredentialsDto;
import ru.indeece.authservice.dto.UserDto;
import ru.indeece.authservice.entities.Role;
import ru.indeece.authservice.entities.User;
import ru.indeece.authservice.enums.ERole;
import ru.indeece.authservice.repositories.RoleRepository;
import ru.indeece.authservice.repositories.UserRepository;
import ru.indeece.authservice.security.jwt.JwtService;
import ru.indeece.authservice.services.UserService;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = findByCredentials(userCredentialsDto);
        return jwtService.generateAuthToken(user.getEmail(),
                user.getRoles().stream().map(r -> r.getName().name())
                        .collect(java.util.stream.Collectors.toSet()));
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.generateBaseToken(user.getEmail(), refreshToken,
                    user.getRoles().stream().map(r -> r.getName().name()).collect(java.util.stream.Collectors.toSet()));
        }
        throw new  AuthenticationException("Invalid refresh token");
    }

    @Override
    @Transactional
    public UserDto getUserById(String id) throws ChangeSetPersister.NotFoundException {
        Long userId = Long.valueOf(id);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return convertToDto(user);
    }

    @Override
    @Transactional
    public UserDto getUserByEmail(String email) throws ChangeSetPersister.NotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return convertToDto(user);
    }

    @Override
    public String addUser(UserDto userDto){
        User user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Default user role not found"));
        user.setRoles(Set.of(userRole));
        userRepository.save(user);
        return "User added";
    }

    private User findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        Optional<User> optionalUser = userRepository.findByEmail(userCredentialsDto.getEmail());
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())){
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }

    private User findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email).orElseThrow(()->
                new Exception(String.format("User with email %s not found", email)));
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        return dto;
    }

    private User convertToEntity(UserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        return user;
    }

}