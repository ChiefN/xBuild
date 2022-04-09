package com.example.xbulild.service;

import com.example.xbulild.entity.Workout;
import com.example.xbulild.repository.WorkoutRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    WorkoutRepository workoutRepository;

    public WorkoutService(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public List<Workout> findAll(){
        return workoutRepository.findAll();
    }
}
