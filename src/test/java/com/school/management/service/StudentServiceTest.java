package com.school.management.service;

import com.school.management.model.dto.StudentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
	@Mock
	List<Long> courseIds;

	@Mock
	List<StudentDto> studentsDto;

	@InjectMocks
	StudentService studentService;

//	@Test
//	public void moreThanFiveCourses() {
//		when(courseIds.size()).thenReturn(6);
//
//		Assertions.assertThrows(ResponseStatusException.class, () ->
//			studentService.updateStudentCourses(1L, courseIds)
//		);
//	}

	@Test
	public void moreThanFiftyStudentsPerRequest() {
		when(studentsDto.size()).thenReturn(51);

		Assertions.assertThrows(ResponseStatusException.class, () ->
			studentService.createStudents(studentsDto)
		);
	}
}
