package com.example.xbulild.data.exercise;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {
    ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService){
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<Exercise> findAllByCustomFilter(
            @RequestParam(required = false, name = "filterText") String filterText,
            @RequestParam(required = false, name = "equipmentIdList") List<String> equipmentIdList,
            @RequestParam(required = false, name = "propertyIdList") List<String> propertyIdList
            ){
        return exerciseService.findAllByCustomFilter(filterText, equipmentIdList, propertyIdList);
    }

    @GetMapping("/{id}")
    public Exercise findById(@PathVariable("id") String id){
        return exerciseService.findById(id);
    }


}
