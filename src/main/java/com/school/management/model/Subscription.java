package com.school.management.model;

import javax.persistence.Entity;

@Entity
public class Subscription {

    private Course course;

    private Student student;

    public Subscription() {
    }

    public Subscription(Course course, Student student) {
        this.course = course;
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
