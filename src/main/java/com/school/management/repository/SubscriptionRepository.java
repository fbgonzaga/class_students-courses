package com.school.management.repository;

import com.school.management.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    @Query("select s from Subscription s where s.course.id = ?1 order by s.course.id, s.student.id")
    public List<Subscription> findByCourseId(Long id);

    @Query("select s from Subscription s where s.student.id = ?1 order by s.student.id, s.course.id) ")
    public List<Subscription> findByStudentId(Long id);
}
