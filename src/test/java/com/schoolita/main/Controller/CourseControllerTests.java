package com.schoolita.main.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolita.main.Dto.CourseDTO;
import com.schoolita.main.Service.Impl.CourseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc
public class CourseControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseController courseController;

    @MockBean
    private CourseServiceImpl courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertNotNull(courseController);
    }

    @Test
    void getAllCourses_empty() throws Exception {
        when(courseService.getAllCourses()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/courses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getAllCourses() throws Exception {
        CourseDTO courseDto1 = CourseDTO.builder().id(1L).name("Mock 1").year(2024).build();
        CourseDTO courseDto2 = CourseDTO.builder().id(2L).name("Mock 2").year(2024).build();

        when(courseService.getAllCourses()).thenReturn(List.of(courseDto1, courseDto2));

        mockMvc.perform(get("/courses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(courseDto1, courseDto2))));
    }

    @Test
    void getCourse() throws Exception {
        CourseDTO courseDto1 = CourseDTO.builder().id(1L).name("Mock 1").year(2024).build();

        when(courseService.getCourse(1L)).thenReturn(courseDto1);

        mockMvc.perform(get("/courses/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(courseDto1)));
    }

    @Test
    void getCourse_notFound() throws Exception {
        when(courseService.getCourse(1L)).thenReturn(null);

        mockMvc.perform(get("/courses/1")).andExpect(status().isNotFound());
    }

    @Test
    void createCourse() throws Exception {
        CourseDTO courseDto1 = CourseDTO.builder().name("Mock 1").year(2024).build();

        when(courseService.createCourse(any())).thenAnswer((invocation) -> invocation.getArgument(0));

        mockMvc.perform(post("/courses").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(courseDto1)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(courseDto1)));
    }

    @Test
    void updateCourse() throws Exception {
        CourseDTO courseDto1 = CourseDTO.builder().name("Mock 1").year(2024).build();

        when(courseService.updateCourse(any(Long.class), any(CourseDTO.class))).thenAnswer((invocation) -> invocation.getArgument(1));

        mockMvc.perform(put("/courses/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(courseDto1)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(courseDto1)));
    }

    @Test
    void updateCourse_notFound() throws Exception {
        CourseDTO courseDto1 = CourseDTO.builder().name("Mock 1").year(2024).build();

        when(courseService.updateCourse(any(Long.class), any(CourseDTO.class))).thenAnswer((invocation) -> null);

        mockMvc.perform(put("/courses/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(courseDto1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCourse() throws Exception {
        CourseDTO courseDto1 = CourseDTO.builder().name("Mock 1").year(2024).build();

        when(courseService.deleteCourse(any(Long.class))).thenAnswer((invocation) -> courseDto1);

        mockMvc.perform(delete("/courses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteCourse_notFound() throws Exception {
        when(courseService.deleteCourse(any(Long.class))).thenAnswer((invocation) -> null);

        mockMvc.perform(delete("/courses/1"))
                .andExpect(status().isNotFound());
    }
}
