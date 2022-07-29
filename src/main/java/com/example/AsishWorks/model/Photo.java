package com.example.AsishWorks.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
@Data
@NoArgsConstructor
public class Photo {
    @Id
    private String id;

    private String title;

    private Binary image;

    public Photo(String title) {
        this.title = title;
    }
}