package com.example.xbulild.vaadin.view;

import com.example.xbulild.equipment.Equipment;
import com.example.xbulild.exercise.Exercise;
import com.example.xbulild.object.Workout;
import com.example.xbulild.equipment.EquipmentRepository;
import com.example.xbulild.exercise.ExerciseService;
import com.example.xbulild.object.WorkoutService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/", layout = AppView.class)
public class TestView extends VerticalLayout {
    WorkoutService workoutService;
    ExerciseService exerciseService;
    EquipmentRepository equipmentRepository;

    Grid<Workout> workoutGrid; //Added
    Grid<Exercise> exerciseGrid; //Added
    Grid<Equipment> equipmentGrid;

    public TestView(WorkoutService workoutService, ExerciseService exerciseService, EquipmentRepository equipmentRepository) {
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.equipmentRepository = equipmentRepository;

        this.workoutGrid = new Grid<>(Workout.class, true);
        this.exerciseGrid = new Grid<>(Exercise.class, true); //Added
        this.equipmentGrid = new Grid<>(Equipment.class, false);
        equipmentGrid.addColumn(Equipment::getName).setHeader("Name");

        workoutGrid.setItems(workoutService.findAll());
        exerciseGrid.setItems(exerciseService.findAllByCustomFilter(null, null)); //Added
        equipmentGrid.setItems(equipmentRepository.findAll());

        add(new H1("Hello World!"));
        add(workoutGrid, exerciseGrid, equipmentGrid);
    }
}
