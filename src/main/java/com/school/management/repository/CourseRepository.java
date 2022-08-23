package com.school.management.repository;

import com.school.management.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CourseRepository extends JpaRepository<Course,Long> {
    public Set<Course> findDistinctByIdIn(Set<Long> ids);
}
