package com.auth.service;

import com.auth.dto.JwtTokenResponse;
import com.auth.dto.UserDto;
import com.auth.entity.User;
import com.auth.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserDto saveUSer(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepo.save(user);

        return UserDto.builder()
                .id(save.getId())
                .email(save.getEmail())
                .username(save.getUsername())
                .roles(save.getRoles())
                .build();
    }


    public JwtTokenResponse generateToken(String username){

        String token = jwtUtil.generateToken(username);
        JwtTokenResponse jwtTokenResponse= new JwtTokenResponse();
        jwtTokenResponse.setToken(token);
        jwtTokenResponse.setType("Bearer");
        jwtTokenResponse.setValidUntil(jwtUtil.extractExpiration(token).toString());

        return  jwtTokenResponse;


    }


}
