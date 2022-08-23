package com.school.management.repository;

import com.school.management.model.Course;
import com.school.management.model.Student;
import com.school.management.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    @Query("select s from Subscription s where s.course.id = ?1 order by s.course.id, s.student.id")
    public List<Subscription> findByCourseId(Long id);

    @Query("select s from Subscription s where s.student.id = ?1 order by s.student.id, s.course.id ")
    public List<Subscription> findByStudentId(Long id);

    @Query("select s from Subscription s order by s.course.id ASC, s.student.id ASC")
    public List<Subscription> orderByCourses();

    @Query("select s from Subscription s order by s.student.id ASC, s.course.id ASC")
    public List<Subscription> orderByStudents();

    //@Query(value = "DELETE FROM subscription WHERE s.student_id = ?1", nativeQuery = true)
    //void deleteStudentSubscriptions(Long studentId);

    void deleteByStudent_Id(Long studentId);

    void deleteByCourse_id(Long courseId);

    @Query("DELETE FROM Subscription s WHERE s.course.id = ?1")
    void deleteCourseSubscriptions(Long courseId);

    @Query("SELECT sub.course AS course " +
            "FROM Subscription sub " +
            "WHERE (SELECT COUNT(s) from Subscription s WHERE s.course = sub.course ) >=50" +
            "AND (sub.course IN ?1) ")
    List<Course> findFullCourses(Set<Course> courses);

    @Query("SELECT c.id FROM Course c LEFT JOIN Subscription s WHERE c NOT in s")
    List<Long> findCoursesWithoutStudent();

}
