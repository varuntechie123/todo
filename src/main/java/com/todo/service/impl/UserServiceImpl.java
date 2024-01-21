package com.todo.service.impl;

import com.todo.dto.CategoryDto;
import com.todo.dto.UserDto;
import com.todo.entity.Category;
import com.todo.entity.User;
import com.todo.exception.ResourceNotFoundException;
import com.todo.mapper.CategoryMapper;
import com.todo.mapper.TaskMapper;
import com.todo.mapper.UserMapper;
import com.todo.repository.UserRepository;
import com.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private CategoryMapper categoryMapper;
    private TaskMapper taskMapper;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CategoryMapper categoryMapper, TaskMapper taskMapper, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.categoryMapper = categoryMapper;
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        System.out.println("UserDto " + userDto);
        User user = userMapper.mapToUser(userDto);
        System.out.println(user);
        User createdUser = userRepository.save(userMapper.mapToUser(userDto));
        return userMapper.mapToUserDto(createdUser);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userList.stream().map(userMapper::mapToUserDto)
                .toList();
        return userDtoList;
    }

    @Override
    public UserDto getUser(long id) {

        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", String.valueOf(id)));

        return userMapper.mapToUserDto(user);
    }

    @Override
    public UserDto getUserByEmailId(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("User", "email", email));
        UserDto userDto = userMapper.mapToUserDto(user);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "Email", String.valueOf(id)));

        user = userMapper.mapToUser(userDto);
        User updatedUser = userRepository.save(user);
        return userMapper.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(long id) {

        userRepository.deleteById(id);

    }
}
