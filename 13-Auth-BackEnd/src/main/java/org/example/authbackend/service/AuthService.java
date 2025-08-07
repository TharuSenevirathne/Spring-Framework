package org.example.authbackend.service;

import lombok.RequiredArgsConstructor;
import org.example.authbackend.dto.AuthDTO;
import org.example.authbackend.dto.AuthResponseDTO;
import org.example.authbackend.dto.RegisterDTO;
import org.example.authbackend.entity.Role;
import org.example.authbackend.entity.User;
import org.example.authbackend.repository.UserRepository;
import org.example.authbackend.util.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO authenticate(AuthDTO authDTO){
        // validate credentials
        User user = userRepository.findByUsername(authDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        // check password
        if (!passwordEncoder.matches(
                authDTO.getPassword(),
                user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        // generate token
        String token = jwtUtil.generateToken(authDTO.username);
        return new AuthResponseDTO(token, user.getRole().name());
    }
    // register user
    public String register(RegisterDTO registerDTO){
        if (userRepository.findByUsername(registerDTO.getUsername())
                .isPresent()){
            throw new RuntimeException("Username already exists");
        }
        User user=User.builder()
                .username(registerDTO.getUsername())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.valueOf(registerDTO.getRole()))
                .build();
        userRepository.save(user);
        return "User registered successfully";
    }

}
