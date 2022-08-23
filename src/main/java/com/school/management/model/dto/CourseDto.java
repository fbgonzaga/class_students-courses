package com.school.management.model.dto;
import java.sql.Timestamp;


public class CourseDto {

    private Long id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updated_at;

    public CourseDto(Long id, String name, Timestamp createdAt, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
