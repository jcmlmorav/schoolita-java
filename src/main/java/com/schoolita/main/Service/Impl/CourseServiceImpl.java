package com.schoolita.main.Service.Impl;

import com.schoolita.main.Dto.CourseDTO;
import com.schoolita.main.Model.Course;
import com.schoolita.main.Repository.CourseRepository;
import com.schoolita.main.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map((course) -> mapToCourseDto(course)).collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            return null;
        }

        return mapToCourseDto(course.get());
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());
        course.setYear(courseDto.getYear());

        Course newCourse = courseRepository.save(course);

        return CourseDTO.builder().
                id(newCourse.getId())
                .name(newCourse.getName())
                .year(newCourse.getYear())
                .build();
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDto) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            return null;
        }

        course.get().setName(courseDto.getName());
        course.get().setYear(courseDto.getYear());
        Course updatedCourse = courseRepository.save(course.get());

        return mapToCourseDto(updatedCourse);
    }

    @Override
    public CourseDTO deleteCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            return null;
        }

        courseRepository.delete(course.get());

        return mapToCourseDto(course.get());
    }

    private CourseDTO mapToCourseDto(Course course) {
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .year(course.getYear())
                .build();
    }
}
