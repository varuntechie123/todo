package com.todo.dto;


import jakarta.persistence.*;

import java.util.List;

//@Setter
//@Getter
public class CategoryDto {

    public CategoryDto() {
    }

    public CategoryDto(long id, String name, List<TaskDto> tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TaskDto> tasks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TaskDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDto> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
