package com.example.xbulild.object;

import com.example.xbulild.object.Workout;
import com.example.xbulild.object.WorkoutRepository;
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
