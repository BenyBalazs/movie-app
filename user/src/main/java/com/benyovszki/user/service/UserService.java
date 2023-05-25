package com.benyovszki.user.service;


import com.benyovszki.token.repository.UserToken;
import com.benyovszki.token.repository.UserTokenRepository;
import com.benyovszki.token.token.JWTUtils;
import com.benyovszki.user.exception.InvalidUsernameException;
import com.benyovszki.user.exception.UsernameTakenException;
import com.benyovszki.user.model.User;
import com.benyovszki.user.repository.UserRepository;
import com.benyovszki.user.security.CustomAuthProvider;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.openapitools.model.UserLoginRequest;
import org.openapitools.model.UserRegistrationRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final JWTUtils jwtUtils;
    private final UserTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private CustomAuthProvider authenticationManager;

    public User registerUser(@NonNull UserRegistrationRequest registrationRequest) {

        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new UsernameTakenException();
        }

        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setUsername(registrationRequest.getUsername());
        return userRepository.save(user);
    }

    public String loginUser(@NonNull UserLoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(InvalidUsernameException::new);
        UserToken token = createUserToken(user);
        String jwt = jwtUtils.generateToken(token);
        token.setToken(jwt);
        tokenRepository.save(token);
        return jwt;
    }

    private UserToken createUserToken(User user) {
        return UserToken.builder()
                .userId(user.getId())
                .username(user.getUsername()).build();
    }
}
