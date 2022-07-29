package com.example.AsishWorks.repository;

import com.example.AsishWorks.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {

}