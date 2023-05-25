package com.benyovszki.user.mapper;

import com.benyovszki.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.UserRegistration200Response;

@Mapper
public interface UserToUserRegisterResponseMapper {

    @Mapping(target="userId", source="user.id")
    UserRegistration200Response sourceToDestination(User user);
}
