package com.example.xbulild;

import com.example.xbulild.data.property.Property;
import com.example.xbulild.data.equipment.Equipment;
import com.example.xbulild.data.exercise.Exercise;
import com.example.xbulild.data.exercise.tag.ExerciseTag;
import com.example.xbulild.data.property.PropertyRepository;
import com.example.xbulild.pojo.WorkoutBlock;
import com.example.xbulild.pojo.WorkoutContent;
import com.example.xbulild.pojo.WorkoutExercise;
import com.example.xbulild.data.equipment.EquipmentRepository;
import com.example.xbulild.data.exercise.ExerciseRepository;
import com.example.xbulild.pojo.WorkoutRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class XbulildApplication {

    public static void main(String[] args) {
        SpringApplication.run(XbulildApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ExerciseRepository exerciseRepository, PropertyRepository propertyRepository, EquipmentRepository equipmentRepository ){
        return args -> {

            /* List<Equipment> equipmentList = List.of(
                    Equipment.builder().name("Dumbbell").exerciseSet(new HashSet<>()).build(),
                    Equipment.builder().name("Body-weight").exerciseSet(new HashSet<>()).build(),
                    Equipment.builder().name("Barbell").exerciseSet(new HashSet<>()).build(),
                    Equipment.builder().name("Bench").exerciseSet(new HashSet<>()).build()
            );

            List<Property> propertyList = new ArrayList<>();
            Property p1 = new Property();
            p1.setName("Sumo");
            p1.setCategory("Stance");

            Property p2 = new Property();
            p2.setName("Hold");
            p2.setCategory("Load");

            System.out.println(p2.getVersion());


            List.of(
                    /*Property.builder().name("Parallel").category("Stance").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Sumo").category("Stance").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Split").category("Stance").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Staggered").category("Stance").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Seated").category("Stance").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Lying").category("Stance").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Rack").category("Load").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Back").category("Load").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Overhead").category("Load").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Hold").category("Load").exerciseSet(new HashSet<>()).build(),
                    Property.builder().name("Split").category("Load").exerciseSet(new HashSet<>()).build()
            );

            //propertyRepository.saveAll(propertyList);


            List<Exercise> exerciseList = List.of(
                    Exercise.builder().name("Back squat").equipmentSet(new HashSet<>()).propertySet(new HashSet<>()).build(),
                    Exercise.builder().name("Goblet squat").equipmentSet(new HashSet<>()).propertySet(new HashSet<>()).build(),
                    Exercise.builder().name("Bench press").equipmentSet(new HashSet<>()).propertySet(new HashSet<>()).build()
            );


            for(int i = 0; i < exerciseList.size(); i++){
                ExerciseTag tag = new ExerciseTag();
                exerciseList.get(i).setExerciseTag(tag);
                tag.setExercise(exerciseList.get(i));
            }

            exerciseList.get(0).addEquipment(equipmentList.get(2));
            exerciseList.get(1).addEquipment(equipmentList.get(3));
            exerciseList.get(2).addEquipment(equipmentList.get(2));
            exerciseList.get(2).addEquipment(equipmentList.get(3));
            exerciseList.get(0).addProperty(p1);
            exerciseList.get(2).addProperty(p2);


            exerciseRepository.saveAll(exerciseList);

            System.out.println(propertyRepository.findAll().get(1).getVersion());

            List<String> categories = propertyRepository.findDistinctCategory();

            categories.forEach(str -> {
                System.out.println(str);
            }); */

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
