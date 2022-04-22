package com.example.xbulild.pojo;

import java.util.List;

public abstract class WorkoutContent{

    //Change to ENUM?
    String type;

    //Methods to override
    //Forced override


    //Optional override
    public List<WorkoutContent> getContentList(){return null;}

    public void addWorkoutContent(WorkoutContent workoutContent){} //Override in WorkoutBlock
    public String getNested(){
        return "This is in abstract class";
    }

    //Constructor
    public WorkoutContent(String type) {
        this.type = type;
    }

    //Based on type we'll know how to build the view
    public String getType(){
        return this.type;
    }
}
