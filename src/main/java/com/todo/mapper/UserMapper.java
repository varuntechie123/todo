package com.todo.mapper;

import com.todo.dto.UserDto;
import com.todo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private ModelMapper mapper;

    public UserMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UserDto mapToUserDto(User user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }

    public User mapToUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        return user;
    }
}
