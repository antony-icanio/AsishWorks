package com.example.AsishWorks.model.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ServletFilters {
    private List<String> urlPatternMappings;
    private List<String> servletNameMappings;
    private String name;
    private String className;

}
