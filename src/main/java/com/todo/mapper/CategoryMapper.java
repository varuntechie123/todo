package com.todo.mapper;

import com.todo.dto.CategoryDto;
import com.todo.dto.TaskDto;
import com.todo.entity.Category;
import com.todo.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    private TaskMapper taskMapper;

    public CategoryMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public CategoryDto mapToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList = category.getTask().stream().map(taskMapper::mapToTaskDto).toList();
        categoryDto.setTasks(taskDtoList);

        return categoryDto;
    }

    public Category mapToCategory(CategoryDto categoryDto) {
        Category category= new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        List<Task> taskList = new ArrayList<>();
        taskList = categoryDto.getTasks().stream().map(taskMapper::mapToTask).toList();

        category.setTask(taskList);

        return category;
    }
}
