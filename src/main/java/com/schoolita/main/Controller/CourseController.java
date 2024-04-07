package com.schoolita.main.Controller;

import com.schoolita.main.Dto.CourseDTO;
import com.schoolita.main.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> all() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<CourseDTO> one(@PathVariable long id) {
        CourseDTO course = courseService.getCourse(id);

        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(course);
    }

    @PostMapping("/courses")
    public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO courseDto) {
        return new ResponseEntity<>(courseService.createCourse(courseDto), HttpStatus.CREATED);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable long id, @RequestBody CourseDTO courseDto) {
        CourseDTO course = courseService.updateCourse(id, courseDto);

        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        CourseDTO course = courseService.deleteCourse(id);

        if (course == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}
