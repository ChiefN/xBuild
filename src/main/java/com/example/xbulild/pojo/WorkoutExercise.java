package com.example.xbulild.pojo;

public class WorkoutExercise extends WorkoutContent{

    String nested;

    public WorkoutExercise(String nested) {
        super("WorkoutExercise");
        this.nested = nested;
    }

    //When exercise: getContent() returns only this object. A single exercise and its properties

    @Override
    public String getNested() {
        return nested;
    }
}
