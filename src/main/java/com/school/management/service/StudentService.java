package com.school.management.service;


import com.school.management.model.Student;
import com.school.management.model.dto.StudentDto;
import com.school.management.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
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
			studentRepository.deleteById(id);
		} else {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"To delete the student and student-courses relationships, inform confirm-deletion=true as a query param.");
		}


	}
}
