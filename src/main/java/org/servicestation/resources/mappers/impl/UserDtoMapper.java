package org.servicestation.resources.mappers.impl;

import org.servicestation.model.User;
import org.servicestation.resources.dto.UserDto;
import org.servicestation.resources.mappers.IDtoMapper;

public class UserDtoMapper implements IDtoMapper<UserDto, User> {

    @Override
    public User mapDtoToServerObject(UserDto dto) {
        User user = new User();
        user.username = dto.username;
        user.password = dto.password;
        user.firstname = dto.firstname;
        user.lastname = dto.lastname;
        user.phone_number = dto.phone_number;

        return user;
    }

    @Override
    public UserDto mapServerObjectToDto(User serverObj) {
        UserDto dto = new UserDto();
        dto.username = serverObj.username;
        dto.firstname = serverObj.firstname;
        dto.lastname = serverObj.lastname;
        dto.phone_number = serverObj.phone_number;

        return dto;
    }

    @Override
    public Class getDtoType() {
        return UserDto.class;
    }

    @Override
    public Class getServerObjectType() {
        return User.class;
    }
}
