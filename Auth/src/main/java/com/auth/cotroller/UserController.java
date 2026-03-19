package com.auth.cotroller;

import com.auth.dto.JwtTokenResponse;
import com.auth.dto.LoginRequest;
import com.auth.dto.UserDto;
import com.auth.entity.User;
import com.auth.exception.ResourceNotFoundException;
import com.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody User user) {
        UserDto userDto = userService.saveUSer(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/generate-token")
    public JwtTokenResponse generateToken(@RequestBody LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        if (authenticate.isAuthenticated()){
            return userService.generateToken(loginRequest.getUsername());
        }
        else
            throw new ResourceNotFoundException("Invalid access");
    }

}
