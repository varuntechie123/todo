package com.todo.controller;

import com.todo.dto.UserDto;
import com.todo.entity.User;
import com.todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<UserDto> getUser(@PathVariable long id) {
        UserDto userDto = userService.getUser(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

   /* @GetMapping("/users/{emailId}")
    ResponseEntity<UserDto> getUserByEmailId(String emailId) {
        UserDto userDto = userService.getUserByEmailId(emailId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }*/

    @PutMapping("/users/{id}")
    ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable long id) {
        UserDto updatedUser = userService.updateUser(userDto, id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<String> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
}
