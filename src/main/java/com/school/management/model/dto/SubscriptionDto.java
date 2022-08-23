package com.school.management.model.dto;

import com.school.management.model.Course;
import com.school.management.model.Student;

public class SubscriptionDto {

    private Student student;
    private Course course;

    public SubscriptionDto() {
    }

    public SubscriptionDto(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public SubscriptionDto(Long studentId, Long courseId){
        this.student = new Student();
        this.course = new Course();
        this.student.setId(studentId);
        this.course.setId(courseId);
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
