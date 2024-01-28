package com.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.dto.CategoryDto;
import com.todo.dto.TaskDto;
import com.todo.dto.UserDto;
import com.todo.entity.Category;
import com.todo.entity.Task;
import com.todo.entity.User;
import com.todo.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void givenUserObject_whenCreateUser_thenReturnSavedUser() throws Exception {
        given(userService.createUser(any(UserDto.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/v1/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(userDto.getFirstName())));
    }

    @Test
    public void givenUserList_whenGetAllUsers_thenReturnUserList() throws Exception {
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

        given(userService.getAllUsers()).willReturn(List.of(userDto, userDto2));

        ResultActions response = mockMvc.perform(get("/v1/api/users")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(jsonPath("$.size()", CoreMatchers.is(2)));

    }

    @Test
    public void givenUserId_whenGetUserById_thenReturnUser() throws Exception {
        long employeeId = 1L;

        given(userService.getUser(employeeId)).willReturn(userDto);

        ResultActions response = mockMvc.perform(get("/v1/api/users/{id}", employeeId));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("varun")));
    }

    @Test
    public void givenUpdatedUser_whenUpdateUser_returnUpdatedUser() throws Exception {
        long employeeId = 1L;

        userDto.setFirstName("Arun");

        given(userService.updateUser(userDto, employeeId))
                .willReturn(userDto);

        ResultActions response = mockMvc.perform(put("/v1/api/users/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto))
        );

        response.andExpect(jsonPath("$.firstName", CoreMatchers.is("Arun")));

    }

    @Test
    public void givenUserId_whenDeleteUserById_thenReturn200Status() throws Exception {
        long employeeId = 1L;

        willDoNothing().given(userService).deleteUser(employeeId);

        ResultActions response = mockMvc.perform(delete("/v1/api/users/{id}", employeeId));

        response.andExpect(status().isOk());

    }

}
