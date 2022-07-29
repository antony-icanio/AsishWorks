package com.example.AsishWorks.model.rest;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@Data
@NoArgsConstructor
public class Video {
    private String title;
    private String fileName;
    private InputStream stream;
}
