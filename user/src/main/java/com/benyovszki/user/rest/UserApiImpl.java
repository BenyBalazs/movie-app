package com.benyovszki.user.rest;

import com.benyovszki.user.mapper.UserToUserRegisterResponseMapper;
import com.benyovszki.user.model.User;
import com.benyovszki.user.service.UserService;
import lombok.AllArgsConstructor;
import org.openapitools.api.UserApi;
import org.openapitools.model.UserLogin200Response;
import org.openapitools.model.UserLoginRequest;
import org.openapitools.model.UserRegistration200Response;
import org.openapitools.model.UserRegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserApiImpl implements UserApi {

    private final UserService userService;
    private final UserToUserRegisterResponseMapper userToUserRegisterResponseMapper;

    @Override
    public ResponseEntity<UserLogin200Response> userLogin(UserLoginRequest userLoginRequest) {
        String token = userService.loginUser(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new UserLogin200Response().token(token));
    }

    @Override
    public ResponseEntity<UserRegistration200Response> userRegistration(UserRegistrationRequest userRegistrationRequest) {
        User registeredUser = userService.registerUser(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userToUserRegisterResponseMapper.sourceToDestination(registeredUser));
    }
}
