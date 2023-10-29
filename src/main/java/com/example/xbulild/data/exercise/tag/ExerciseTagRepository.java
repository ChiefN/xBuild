package com.example.xbulild.data.exercise.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseTagRepository extends JpaRepository<ExerciseTag, String> {
}
