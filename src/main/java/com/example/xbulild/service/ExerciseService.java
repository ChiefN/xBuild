package com.example.xbulild.service;

import com.example.xbulild.entity.Exercise;
import com.example.xbulild.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService{
    ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> findAll(){
        return exerciseRepository.findAll();
    }

}
