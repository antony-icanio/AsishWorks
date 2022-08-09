package com.example.AsishWorks.model.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HandlerMethod {

    private String className;
    private String name;
    private String descriptor;
}
