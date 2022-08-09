package com.example.AsishWorks.model.rest;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Produces {

    private String mediaType;
    private boolean negated;
}
