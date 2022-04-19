package com.example.xbulild.exercise;

import com.example.xbulild.equipment.Equipment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
            @RequestParam(required = false, name = "equipmentList") List<Integer> equipmentIdList
            ){
        return exerciseService.findAllByCustomFilter(filterText, equipmentIdList);
    }

    @GetMapping("/{id}")
    public Exercise findById(@PathVariable("id") int id){
        return exerciseService.findById(id);
    }


}
