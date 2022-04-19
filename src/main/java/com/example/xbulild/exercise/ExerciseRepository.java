package com.example.xbulild.exercise;

import com.example.xbulild.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer>{

    @Query("SELECT e, et FROM Exercise e JOIN ExerciseTag et ON e.id=et.id " +
    "WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ")
    List<Exercise> search(@Param("searchTerm") String searchTerm);
}
