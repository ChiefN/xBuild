package com.example.xbulild;

import com.example.xbulild.entity.Block;
import com.example.xbulild.entity.Exercise;
import com.example.xbulild.entity.Workout;
import com.example.xbulild.repository.BlockRepository;
import com.example.xbulild.repository.ExerciseRepository;
import com.example.xbulild.repository.WorkoutRepository;
import com.vaadin.flow.server.Command;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class XbulildApplication {

    public static void main(String[] args) {
        SpringApplication.run(XbulildApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ExerciseRepository exerciseRepository, BlockRepository blockRepository, WorkoutRepository workoutRepository){
        return args -> {
            List<Exercise> exerciseList = List.of(
                    new Exercise("Knäböj"),
                    new Exercise("Marklyft"),
                    new Exercise("Bänkpress"),
                    new Exercise("Chins"),
                    new Exercise("Rodd"),
                    new Exercise("Militärpress"),
                    new Exercise("Höftlyft")
            );
            exerciseRepository.saveAll(exerciseList);

            List<Block> blockList = List.of(
                    new Block("Block1"),
                    new Block("Block2"),
                    new Block("Block3"),
                    new Block("Block4")
            );
            blockRepository.saveAll(blockList);

            List<Workout> workoutList = List.of(
                    new Workout("Workout1"),
                    new Workout("Workout2")
            );
            workoutRepository.saveAll(workoutList);

        };
    }

}
