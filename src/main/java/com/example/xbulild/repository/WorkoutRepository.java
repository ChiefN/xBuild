package com.example.xbulild.repository;

import com.example.xbulild.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
}
