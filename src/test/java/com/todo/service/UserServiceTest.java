package com.todo.service;

import com.todo.dto.CategoryDto;
import com.todo.dto.TaskDto;
import com.todo.dto.UserDto;
import com.todo.entity.Category;
import com.todo.entity.Task;
import com.todo.entity.User;
import com.todo.mapper.UserMapper;
import com.todo.repository.UserRepository;
import com.todo.service.impl.UserServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("spring tutorial");
        taskDto.setDescription("kava");
        taskDto.setDueDate(LocalDate.of(2024,02,28));
        taskDto.setStatus(false);
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(taskDto);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("java");
        categoryDto.setTasks(taskDtoList);
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);

        userDto = new UserDto();
        userDto.setEmail("varun@gmail.com");
        userDto.setFirstName("varun");
        userDto.setLastName("gudisena");
        userDto.setPassword("password");
        userDto.setCategories(categoryDtoList);

        Task task = new Task();
        task.setTitle("spring tutorial");
        task.setDescription("kava");
        task.setDueDate(LocalDate.of(2024,02,28));
        task.setStatus(false);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);

        Category category = new Category();
        category.setName("java");
        category.setTask(taskList);
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        user = new User();
        user.setEmail("varun@gmail.com");
        user.setFirstName("varun");
        user.setLastName("gudisena");
        user.setPassword("password");
        user.setCategories(categoryList);
    }

    @Test
    public void givenUserData_whenCreateUser_thenReturnCreatedUser() {
        given(userMapper.mapToUser(userDto)).willReturn(user);
        given(userRepository.save(user)).willReturn(user);
        given(userMapper.mapToUserDto(user)).willReturn(userDto);

        UserDto savedUserDto = userService.createUser(userDto);

        assertThat(savedUserDto).isNotNull();
    }

    @Test
    public void givenUsersList_whenGetAllUsers_thenReturnAllUsers() {
        TaskDto taskDto2 = new TaskDto();
        taskDto2.setTitle("spring tutorial");
        taskDto2.setDescription("kava");
        taskDto2.setDueDate(LocalDate.of(2024,02,28));
        taskDto2.setStatus(false);
        List<TaskDto> taskDtoList2 = new ArrayList<>();
        taskDtoList2.add(taskDto2);

        CategoryDto categoryDto2 = new CategoryDto();
        categoryDto2.setName("java");
        categoryDto2.setTasks(taskDtoList2);
        List<CategoryDto> categoryDtoList2 = new ArrayList<>();
        categoryDtoList2.add(categoryDto2);

        UserDto userDto2 = new UserDto();
        userDto2.setEmail("varun@gmail.com");
        userDto2.setFirstName("varun");
        userDto2.setLastName("gudisena");
        userDto2.setPassword("password");
        userDto2.setCategories(categoryDtoList2);

        Task task2 = new Task();
        task2.setTitle("spring tutorial");
        task2.setDescription("kava");
        task2.setDueDate(LocalDate.of(2024,02,28));
        task2.setStatus(false);
        List<Task> taskList2 = new ArrayList<>();
        taskList2.add(task2);

        Category category2 = new Category();
        category2.setName("java");
        category2.setTask(taskList2);
        List<Category> categoryList2 = new ArrayList<>();
        categoryList2.add(category2);

        User user2 = new User();
        user2.setEmail("varun@gmail.com");
        user2.setFirstName("varun");
        user2.setLastName("gudisena");
        user2.setPassword("password");
        user2.setCategories(categoryList2);

        given(userRepository.findAll()).willReturn(List.of(user,user2));

        UserDto savedUserDto1 = userService.createUser(userDto);
        UserDto savedUserDto2 = userService.createUser(userDto2);

        List<User> userList = userRepository.findAll();

        assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void givenUser_whenGetUserById_thenReturnUser() {
        given(userRepository.findById(1L)).willReturn(Optional.ofNullable(user));
        given(userMapper.mapToUserDto(user)).willReturn(userDto);

        UserDto returnedUserDto = userService.getUser(1L);

        assertThat(returnedUserDto).isNotNull();
    }

    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() {
        given(userRepository.findById(1L)).willReturn(Optional.ofNullable(user));
        given(userRepository.save(user)).willReturn(user);
        given(userMapper.mapToUser(userDto)).willReturn(user);
        given(userMapper.mapToUserDto(user)).willReturn(userDto);

        userDto.setFirstName("sachin");
        user.setFirstName("sachin");

        UserDto updatedUserDto = userService.updateUser(userDto, 1L);

        assertThat(updatedUserDto.getFirstName()).isEqualTo("sachin");

    }

    @Test
    public void givenUserObject_whenDeleteById_thenNothing() {
        long employeeId = 1L;
        willDoNothing().given(userRepository).deleteById(employeeId);
        userService.deleteUser(employeeId);

        verify(userRepository, times(1)).deleteById(employeeId);

    }
}
