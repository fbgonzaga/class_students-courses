package com.school.management.service;


import com.school.management.model.Course;
import com.school.management.model.Student;
import com.school.management.model.Subscription;
import com.school.management.model.dto.CourseDto;
import com.school.management.model.dto.StudentDto;
import com.school.management.model.dto.SubscriptionDto;
import com.school.management.repository.CourseRepository;
import com.school.management.repository.StudentRepository;
import com.school.management.repository.SubscriptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final SubscriptionRepository subscriptionRepository;

	public StudentService(StudentRepository studentRepository, CourseRepository courseRepository, SubscriptionRepository subscriptionRepository) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.subscriptionRepository = subscriptionRepository;
	}

	public List<StudentDto> getStudents() {
		return studentRepository.findAll().stream()
			.map(student -> new StudentDto(student.getId(), student.getName(), student.getAddress(), student.getCreatedAt(), student.getUpdatedAt()))
				.collect(Collectors.toList());
	}

	public StudentDto getStudent(Long id) {
		return studentRepository.findById(id)
			.map(student -> new StudentDto(student.getId(), student.getName(), student.getAddress(), student.getCreatedAt(), student.getUpdatedAt()))
			.orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Student not found."));
	}

	@Transactional
	public StudentDto updateStudent(StudentDto studentDto) {
		Student student = studentRepository.findById(studentDto.getId()).orElseThrow(() -> new ResponseStatusException(
			HttpStatus.NOT_FOUND, "Student not found."));

		Boolean updated = false;
		if (studentDto.getName() != null && !studentDto.getName().isBlank() && !studentDto.getName().equals(student.getName())) {
			student.setName(studentDto.getName());
			updated = true;
		}
		if (studentDto.getAddress() != null && !studentDto.getAddress().isBlank() && !studentDto.getAddress().equals(student.getAddress())) {
			student.setAddress(studentDto.getAddress());
			updated = true;
		}

		if (updated) {
			student.setUpdatedAt(Timestamp.from(Instant.now()));
			student = studentRepository.save(student);
		}

		return new StudentDto(student.getId(), student.getName(), student.getAddress(), student.getCreatedAt(), student.getUpdatedAt());
	}
	@Transactional
	public List<CourseDto> updateStudentCourses(Long studentId, Set<Long> courseIds) {
		Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Student not found."));

		Set<Course> courses = courseRepository.findDistinctByIdIn(courseIds);
		if(courses.isEmpty() || courses.size() < courseIds.size()){
			List<Long> ids = courses.stream()
					.map(course -> course.getId())
					.collect(Collectors.toList());
			List<Long> notFound = courseIds.stream()
					.filter(id -> !ids.contains(id))
					.collect(Collectors.toList());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"The following course IDs entered do not exist: {" + notFound.toString() + "}");
		}
		boolean tooManyCourses = (courses.size() > 5);
		List<Course> fullCourses = subscriptionRepository.findFullCourses(courses);
		boolean aFullCourse = !fullCourses.isEmpty();
		//boolean aFullCourse = true;
		//fullCourses = courses.stream().collect(Collectors.toList());
		if(tooManyCourses)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "student cannot be enrolled in more than 5 courses");
		if(aFullCourse)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "courses are already at full capacity:"
					+ fullCourses.stream().map(course -> course.getId()).collect(Collectors.toList()));

		subscriptionRepository.deleteByStudent_Id(studentId);
		List<Subscription> subscriptions = courses.stream()
				.map(course -> new Subscription(course, student))
				.collect(Collectors.toList());
		List<Subscription> saved = subscriptionRepository.saveAll(subscriptions);
		return saved.stream()
				.map(subscription -> subscription.getCourse())
				.map(course -> new CourseDto(course.getId(), course.getName(), course.getCreatedAt(), course.getCreatedAt()))
				.collect(Collectors.toList());
	}
	public List<StudentDto> createStudents(List<StudentDto> studentsDto) {
		if (studentsDto.size() > 50) {
			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN, "A request can not contain more than 50 students.");
		}

		Timestamp ts = Timestamp.from(Instant.now());
		List<Student> l = studentRepository.saveAll(studentsDto.stream()
			.filter(s -> s.getName() != null && !s.getName().isBlank() && s.getAddress() != null && !s.getAddress().isBlank())
			.map(studentDto -> new Student(studentDto.getName(),
				studentDto.getAddress(),
				ts,
				ts))
			.collect(Collectors.toList()));

		return l.stream()
			.map(student -> new StudentDto(student.getId(),
				student.getName(),
				student.getAddress(),
				student.getCreatedAt(),
				student.getUpdatedAt()))
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteAllStudents(Boolean confirmDeletion) {
		if (confirmDeletion) {
			subscriptionRepository.deleteAll();
			studentRepository.deleteAll();
		} else {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"To delete ALL students and students-courses relationships, inform confirm-deletion=true as a query param.");
		}
	}

	@Transactional
	public void deleteStudent(Long id, Boolean confirmDeletion) {
		if (confirmDeletion) {
			Student student = studentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Student not found."));
			subscriptionRepository.deleteByStudent_Id(id);
			studentRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"To delete the student and student-courses relationships, inform confirm-deletion=true as a query param.");
		}


	}

	public List<Long> getStudentCourses(Long studentId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student with ID" + studentId + " was not found."));
		List<Long> courses = subscriptionRepository.findByStudentId(studentId).stream()
				.map(subscription -> subscription.getCourse().getId())
				.collect(Collectors.toList());
		return courses;
	}

	public List<SubscriptionDto> getCourses(){
		return subscriptionRepository.orderByStudents().stream()
				.map(subscription -> new SubscriptionDto(
						subscription.getStudent(),
						subscription.getCourse()))
				.collect(Collectors.toList());

	}

}
