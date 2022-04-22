package com.example.xbulild.data.exercise.tag;

import org.springframework.stereotype.Service;

@Service
public class ExerciseTagService {
    ExerciseTagRepository exerciseTagRepository;

    public ExerciseTagService(ExerciseTagRepository exerciseTagRepository) {
        this.exerciseTagRepository = exerciseTagRepository;
    }
}
