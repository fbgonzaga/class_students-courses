package com.school.management.service;

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
class CourseServiceTest {

//	@Mock
//	List<Long> studentIds;
//
//	@Mock
//	List<CourseDto> coursesDto;
//
//	@InjectMocks
//	CourseService courseService;
//
//	@Test
//	public void moreThanFiftyCoursesPerRequest() {
//		when(coursesDto.size()).thenReturn(51);
//
//		Assertions.assertThrows(ResponseStatusException.class, () ->
//			courseService.createCourses(coursesDto)
//		);
//	}
//
//	@Test
//	public void moreThanFiftyStudentsInCourse() {
//		when(studentIds.size()).thenReturn(51);
//
//		Assertions.assertThrows(ResponseStatusException.class, () ->
//			courseService.updateCourseStudents(1L, studentIds)
//		);
//	}

}
