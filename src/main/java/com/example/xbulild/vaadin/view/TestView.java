package com.example.xbulild.vaadin.view;

import com.example.xbulild.data.equipment.Equipment;
import com.example.xbulild.data.exercise.Exercise;
import com.example.xbulild.pojo.Workout;
import com.example.xbulild.data.equipment.EquipmentRepository;
import com.example.xbulild.data.exercise.ExerciseService;
import com.example.xbulild.pojo.WorkoutService;
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
        exerciseGrid.setItems(exerciseService.findAllByCustomFilter(null, null, null)); //Added
        equipmentGrid.setItems(equipmentRepository.findAll());

        add(new H1("Hello World!"));
        add(workoutGrid, exerciseGrid, equipmentGrid);
    }
}
