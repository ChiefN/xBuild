package com.example.xbulild.data.exercise;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService{
    ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    //Get data
    public List<Exercise> findAllByCustomFilter(String filterText, List<Integer> equipmentIdList, List<Integer> propertyIdList){

        if(filterText == null && equipmentIdList == null && propertyIdList == null){
            return exerciseRepository.findAll();
        }

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
            exerciseInDB.setPropertySet(exercise.getPropertySet());
        }
        exerciseRepository.save(exerciseInDB);
    }

    public void deleteById(Integer id) {
        exerciseRepository.deleteById(id);
    }
}
