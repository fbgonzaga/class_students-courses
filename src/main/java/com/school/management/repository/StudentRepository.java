package com.school.management.repository;

import com.school.management.model.Student;
import com.school.management.model.dto.CourseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {

    public Set<Student> findDistinctByIdIn(Set<Long> ids);
}
