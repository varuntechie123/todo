package com.todo.service;

import com.todo.dto.UserDto;
import com.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUsers();

    UserDto getUser(long id);

    UserDto getUserByEmailId(String email);

    UserDto updateUser(UserDto userDto, long id);

    void deleteUser(long id);


}
