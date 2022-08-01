package com.example.AsishWorks.repository;

import com.example.AsishWorks.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VideoRepository extends MongoRepository<Video,String> {
}
