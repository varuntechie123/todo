package com.todo.repository;

import com.todo.entity.Category;
import com.todo.entity.Task;
import com.todo.entity.User;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
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
    public void givenUser_whenSaveUser_thenReturnSavedUser() {

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);

        //Assertions

    }

    @Test
    public void givenUserList_whenFindAll_thenReturnUserList() {

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
        user2.setEmail("arun@gmail.com");
        user2.setFirstName("varun");
        user2.setLastName("gudisena");
        user2.setPassword("password");
        user2.setCategories(categoryList2);

        userRepository.save(user);
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    public void givenUser_whenFindById_thenReturnUser() {
        User savedUser = userRepository.save(user);
        User returnedUser = userRepository.findById(savedUser.getId()).get();
        assertThat(returnedUser).isNotNull();

    }

    @Test
    public void givenUser_whenUpdateUser_thenReturnUpdatedUser() {
        User savedUser = userRepository.save(user);
        savedUser.setLastName("gudi");
        User updatedUser = userRepository.save(savedUser);

        assertThat(updatedUser.getLastName()).isEqualTo("gudi");

    }

    @Test
    public void givenUser_whenDelete_thenRemoveUser() {
        User savedUser = userRepository.save(user);
        userRepository.deleteById(savedUser.getId());
        Optional<User> userOptional = userRepository.findById(savedUser.getId());

        assertThat(userOptional).isEmpty();
    }

}
