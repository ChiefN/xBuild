package com.example.xbulild;

import com.example.xbulild.equipment.Equipment;
import com.example.xbulild.exercise.Exercise;
import com.example.xbulild.exercise.tag.ExerciseTag;
import com.example.xbulild.object.WorkoutBlock;
import com.example.xbulild.object.WorkoutContent;
import com.example.xbulild.object.WorkoutExercise;
import com.example.xbulild.equipment.EquipmentRepository;
import com.example.xbulild.exercise.ExerciseRepository;
import com.example.xbulild.object.WorkoutRepository;
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
    CommandLineRunner init(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository, EquipmentRepository equipmentRepository){
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
            List<ExerciseTag> exerciseTagList = List.of(
                    new ExerciseTag(),
                    new ExerciseTag(),
                    new ExerciseTag(),
                    new ExerciseTag(),
                    new ExerciseTag(),
                    new ExerciseTag(),
                    new ExerciseTag()
            );

            List<Equipment> equipmentList = List.of(
                    Equipment.builder().name("Dumbbell").build(),
                    Equipment.builder().name("Body-weight").build(),
                    Equipment.builder().name("Barbell").build(),
                    Equipment.builder().name("Bench").build(),
                    Equipment.builder().name("Pull-up bar").build()
            );

            for(int i = 0; i < 7; i++){
                exerciseList.get(i).setExerciseTag(exerciseTagList.get(i));
                exerciseTagList.get(i).setExercise(exerciseList.get(i));
            }

            exerciseList.get(1).insertSingleIntoEquipmentSet(equipmentList.get(1));
            exerciseList.get(1).insertSingleIntoEquipmentSet(equipmentList.get(2));


            exerciseRepository.saveAll(exerciseList);

//            List<Workout> workoutList = List.of(
//                    new Workout("Workout1"),
//                    new Workout("Workout2")
//            );
//            workoutRepository.saveAll(workoutList);

            WorkoutContent wc = new WorkoutBlock("1");

            WorkoutContent we = new WorkoutExercise("1");

            wc.addWorkoutContent(new WorkoutExercise("2"));

            WorkoutContent wc2 = new WorkoutBlock("2");

            wc2.addWorkoutContent(new WorkoutExercise("3"));
            wc2.addWorkoutContent(new WorkoutExercise("3"));

            wc.addWorkoutContent(wc2);

            List<WorkoutContent> testWorkout = List.of(
                    wc,
                    we
            );

            loop(testWorkout);
        };

    }
    public void loop(List<WorkoutContent> workoutContent){
        workoutContent.forEach(wContent -> {
            switch(wContent.getType()){
                case "WorkoutExercise":
                    System.out.println(wContent.getType() + " " + wContent.getNested());
                    break;
                case "WorkoutBlock":
                    System.out.println(wContent.getType() + " " + wContent.getNested());
                    loop(wContent.getContentList());
                    break;
                default:
                    System.out.println("Couldn't define content");
                    break;
            }
        });
    }

}
