package com.school.management.service;

import com.school.management.model.Course;
import com.school.management.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){this.courseRepository = courseRepository;};


//    public List<Course> getCourses(){
//        return null;
//    }
}
