package com.school.management.service;

import com.school.management.model.Course;
import com.school.management.model.Student;
import com.school.management.model.dto.CourseDto;
import com.school.management.model.dto.StudentDto;
import com.school.management.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){this.courseRepository = courseRepository;};


    public List<CourseDto> getCourses(){
        return courseRepository.findAll().stream()
                .map(course -> new CourseDto(course.getId(), course.getName(), course.getCreatedAt(), course.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    public List<CourseDto> createcourses(List<CourseDto> coursesDto) {
        if (coursesDto.size() > 50) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "A request can not contain more than 50 courses.");
        }

        Timestamp ts = Timestamp.from(Instant.now());
        List<Course> l = courseRepository.saveAll(coursesDto.stream()
                .filter(s -> s.getName() != null && !s.getName().isBlank())
                .map(courseDto -> new Course(courseDto.getId(),
                        courseDto.getName(),
                        ts,
                        ts))
                .collect(Collectors.toList()));
        return l.stream()
                .map(course -> new CourseDto(course.getId(),
                        course.getName(),
                        course.getCreatedAt(),
                        course.getUpdatedAt()))
                .collect(Collectors.toList());
    }
}
