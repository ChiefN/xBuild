package com.example.xbulild.exercise;

import com.example.xbulild.equipment.Equipment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ExerciseService{
    ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    //Get data
    public List<Exercise> findAllByCustomFilter(String filterText, List<Integer> equipmentIdList){
        List<Exercise> initialList;

        if(filterText == null || filterText.isEmpty()){
            initialList = exerciseRepository.findAll();
        } else {
            initialList = exerciseRepository.search(filterText);
        }

        if(equipmentIdList == null || equipmentIdList.isEmpty()){
            return initialList;
        } else {
            return initialList.stream().filter(exercise -> exercise.getEquipmentSet().stream().map(equipment -> equipment.getId()).toList().containsAll(equipmentIdList)).toList();
        }
    }

    public Exercise findById(int id){ return exerciseRepository.findById(id).orElseThrow(); }

    public void save(Exercise exercise) { exerciseRepository.save(exercise);}

    public void editOne(Exercise exercise) {
        Exercise exerciseInDB = exerciseRepository.findById(exercise.getId()).orElseThrow();
        if(exercise.getName() != null && !exercise.getName().isEmpty() && !exercise.getName().isBlank()){
            exerciseInDB.setName(exercise.getName());
        }
        exerciseRepository.save(exerciseInDB);
    }

    public void deleteById(Integer id) {
        exerciseRepository.deleteById(id);
    }
}
