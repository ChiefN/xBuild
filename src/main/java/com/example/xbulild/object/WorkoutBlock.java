package com.example.xbulild.object;

import java.util.ArrayList;
import java.util.List;

public class WorkoutBlock extends WorkoutContent{

    List<WorkoutContent> listOfWorkoutContent = new ArrayList<>();;
    String nested;

    public WorkoutBlock(String nested) {
        super("WorkoutBlock");
        this.nested = nested;
    }

    public List<WorkoutContent> getContentList(){
        return listOfWorkoutContent;
    }


    //Method to add WorkoutContent inside a WorkoutBlock
    @Override
    public void addWorkoutContent(WorkoutContent workoutContent){
        listOfWorkoutContent.add(workoutContent);
    }

    @Override
    public String getNested() {
        return nested;
    }
}
