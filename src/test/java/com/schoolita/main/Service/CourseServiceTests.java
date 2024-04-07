package com.schoolita.main.Service;

import com.schoolita.main.Dto.CourseDTO;
import com.schoolita.main.Model.Course;
import com.schoolita.main.Repository.CourseRepository;
import com.schoolita.main.Service.Impl.CourseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTests {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private final Course course1 = new Course(1, "Mock 1", 2024);
    private final Course course2 = new Course(2, "Mock 2", 2024);


    @Test
    void getAllCourses() {
        given(courseRepository.findAll()).willReturn(List.of(course1, course2));

        List<CourseDTO> coursesList = courseService.getAllCourses();

        Assertions.assertNotNull(coursesList);
        Assertions.assertEquals(coursesList.size(), 2);
        Assertions.assertEquals(course1.getName(), coursesList.get(0).getName());
        Assertions.assertEquals(course1.getYear(), coursesList.get(0).getYear());
        Assertions.assertEquals(course2.getName(), coursesList.get(1).getName());
        Assertions.assertEquals(course2.getYear(), coursesList.get(1).getYear());
    }

    @Test
    void getCourse() {
        given(courseRepository.findById(1L)).willReturn(Optional.of(course1));

        CourseDTO courseDTO = courseService.getCourse(1L);

        Assertions.assertNotNull(courseDTO);
        Assertions.assertEquals(course1.getName(), courseDTO.getName());
        Assertions.assertEquals(course1.getYear(), courseDTO.getYear());
    }

    @Test
    void getCourse_notFound() {
        given(courseRepository.findById(5L)).willReturn(Optional.empty());

        CourseDTO courseDTO = courseService.getCourse(5L);

        Assertions.assertNull(courseDTO);
    }

    @Test
    void createCourse() {
        CourseDTO courseDTO = CourseDTO.builder()
                .name("Mock 1")
                .year(2024)
                .build();

        given(courseRepository.save(Mockito.any(Course.class))).willReturn(course1);

        CourseDTO savedCourse = courseService.createCourse(courseDTO);

        Assertions.assertNotNull(savedCourse);
    }

    @Test
    void updateCourse() {
        CourseDTO courseDTO = CourseDTO.builder()
                .name("Mock 1")
                .year(2024)
                .build();

        given(courseRepository.findById(1L)).willReturn(Optional.of(course1));
        given(courseRepository.save(Mockito.any(Course.class))).willReturn(course1);

        CourseDTO updatedCourse = courseService.updateCourse(1L, courseDTO);

        Assertions.assertNotNull(updatedCourse);
    }

    @Test
    void updateCourse_notFound() {
        CourseDTO courseDTO = CourseDTO.builder()
                .name("Mock 1")
                .year(2024)
                .build();

        given(courseRepository.findById(1L)).willReturn(Optional.empty());

        CourseDTO updatedCourse = courseService.updateCourse(1L, courseDTO);

        Assertions.assertNull(updatedCourse);
    }

    @Test
    void deleteCourse() {
        Course course = Course.builder()
                .name("Mock 1")
                .year(2024)
                .build();

        given(courseRepository.findById(1L)).willReturn(Optional.of(course));

        Assertions.assertAll(() -> courseService.deleteCourse(1L));
    }

    @Test
    void deleteCourse_notFound() {
        given(courseRepository.findById(1L)).willReturn(Optional.empty());
        Assertions.assertAll(() -> courseService.deleteCourse(1L));
    }
}
