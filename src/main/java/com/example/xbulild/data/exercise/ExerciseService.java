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
    public List<Exercise> findAllByCustomFilter(String filterText, List<String> equipmentIdList, List<String> propertyIdList){
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

    public Exercise findById(String id){ return exerciseRepository.findById(id).orElseThrow(); }

    //Save data
    public void save(Exercise exercise) { exerciseRepository.save(exercise);}

    //Delete data
    public void deleteById(String id) {

        System.out.println("Delete");
        exerciseRepository.deleteById(id);
    }
}
