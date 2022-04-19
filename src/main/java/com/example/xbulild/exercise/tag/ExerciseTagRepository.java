package com.example.xbulild.exercise.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseTagRepository extends JpaRepository<ExerciseTag, Integer> {
}
