package com.todo.mapper;

import com.todo.dto.CategoryDto;
import com.todo.dto.UserDto;
import com.todo.entity.Category;
import com.todo.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private CategoryMapper categoryMapper;

    public UserMapper( CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList = user.getCategories().stream().map(categoryMapper::mapToCategoryDto).toList();
        userDto.setCategories(categoryDtoList);

        return userDto;
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        List<Category> categoryList = new ArrayList<>();
        categoryList = userDto.getCategories().stream().map(categoryMapper::mapToCategory).toList();
        user.setCategories(categoryList);

        return user;
    }
}
