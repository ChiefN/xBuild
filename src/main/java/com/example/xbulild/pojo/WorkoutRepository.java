package com.example.xbulild.pojo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkoutRepository extends MongoRepository<Workout, String> {
}
