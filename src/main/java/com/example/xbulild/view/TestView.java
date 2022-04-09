package com.example.xbulild.view;

import com.example.xbulild.entity.Block;
import com.example.xbulild.entity.Exercise;
import com.example.xbulild.entity.Workout;
import com.example.xbulild.service.BlockService;
import com.example.xbulild.service.ExerciseService;
import com.example.xbulild.service.WorkoutService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/")
public class TestView extends VerticalLayout {
    WorkoutService workoutService;
    BlockService blockService;
    ExerciseService exerciseService;

    Grid workoutGrid;
    Grid blockGrid;
    Grid exerciseGrid;

    public TestView(WorkoutService workoutService, BlockService blockService, ExerciseService exerciseService) {
        this.workoutService = workoutService;
        this.blockService = blockService;
        this.exerciseService = exerciseService;

        this.workoutGrid = new Grid<>(Workout.class, true);
        this.blockGrid = new Grid<>(Block.class, true);
        this.exerciseGrid = new Grid<>(Exercise.class, true);

        workoutGrid.setItems(workoutService.findAll());
        blockGrid.setItems(blockService.findAll());
        exerciseGrid.setItems(exerciseService.findAll());


        add(new H1("Hello World!"));
        add(workoutGrid, blockGrid, exerciseGrid);
    }
}
