package com.schoolita.main.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseDTO {
    private Long id;
    private String name;
    private int year;
}
