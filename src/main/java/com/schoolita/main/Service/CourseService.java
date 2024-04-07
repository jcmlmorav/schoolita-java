package com.schoolita.main.Service;

import com.schoolita.main.Dto.CourseDTO;

import java.util.List;

public interface CourseService {
    List<CourseDTO> getAllCourses();
    CourseDTO getCourse(Long id);
    CourseDTO createCourse(CourseDTO course);
    CourseDTO updateCourse(Long id, CourseDTO course);
    CourseDTO deleteCourse(Long id);
}
