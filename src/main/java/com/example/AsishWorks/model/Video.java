package com.example.AsishWorks.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.InputStream;

@Data
@NoArgsConstructor
@Document(collection="Videos")
public class Video {
    @Id
    private String id;
    private String title;
    private String fileName;
    private InputStream stream;
    private Binary video;
}
