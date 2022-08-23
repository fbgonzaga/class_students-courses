package com.school.management.service;

import com.school.management.model.Course;
import com.school.management.model.dto.CourseDto;
import com.school.management.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public CourseDto getCourse(Long id) {
        return courseRepository.findById(id)
                .map(course -> new CourseDto(course.getId(), course.getName(), course.getCreatedAt(), course.getUpdatedAt()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found."));
    }

    @Transactional
    public CourseDto updateCourse(CourseDto courseDto) {
        Course course = courseRepository.findById(courseDto.getId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "course not found."));

        Boolean updated = false;
        if (courseDto.getName() != null && !courseDto.getName().isBlank() && !courseDto.getName().equals(course.getName())) {
            course.setName(courseDto.getName());
            updated = true;
        }

        if (updated) {
            course.setUpdatedAt(Timestamp.from(Instant.now()));
            course = courseRepository.save(course);
        }

        return new CourseDto(course.getId(), course.getName(), course.getCreatedAt(), course.getUpdatedAt());
    }
    public List<CourseDto> createCourses(List<CourseDto> coursesDto) {
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

    @Transactional
    public void deleteCourse(Long id, Boolean confirmDeletion) {
        if (confirmDeletion) {
            Course course = courseRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "course not found."));
            courseRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "To delete the course and student-courses relationships, inform confirm-deletion=true as a query param.");
        }


    }

    public void deleteAllCourses(Boolean confirmDeletion) {
        if (confirmDeletion) {
            courseRepository.deleteAll();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "To delete ALL courses and students-courses relationships, inform confirm-deletion=true as a query param.");
        }
    }
}
