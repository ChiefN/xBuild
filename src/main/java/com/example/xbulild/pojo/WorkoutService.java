package com.example.xbulild.pojo;

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
