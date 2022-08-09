package com.example.AsishWorks.model.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Servlets {
    private List<String> mappings;
    private String name;
    private String className;
}
