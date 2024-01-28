package com.todo.entity;


import jakarta.persistence.*;

import java.util.List;

//@Setter
//@Getter
@Entity
@Table(name = "categories")
public class Category {

    public Category() {
    }

    public Category(long id, String name, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks;

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

    public List<Task> getTask() {
        return tasks;
    }

    public void setTask(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
