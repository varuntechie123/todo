package com.todo.mapper;

import com.todo.dto.TaskDto;
import com.todo.entity.Task;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private ModelMapper mapper;

    public TaskMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public TaskDto mapToTaskDto(Task task) {
        TaskDto taskDto = mapper.map(task, TaskDto.class);
        return taskDto;
    }

    public Task mapToTask(TaskDto taskDto) {
        Task task = mapper.map(taskDto, Task.class);
        return task;
    }
}
