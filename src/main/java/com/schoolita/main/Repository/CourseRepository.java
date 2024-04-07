package com.schoolita.main.Repository;

import com.schoolita.main.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository <Course, Long> { }
