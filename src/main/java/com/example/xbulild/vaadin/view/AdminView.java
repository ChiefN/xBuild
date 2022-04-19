package com.example.xbulild.vaadin.view;

import com.example.xbulild.vaadin.component.EquipmentForm;
import com.example.xbulild.vaadin.component.ExerciseForm;
import com.example.xbulild.equipment.Equipment;
import com.example.xbulild.exercise.Exercise;
import com.example.xbulild.equipment.EquipmentService;
import com.example.xbulild.exercise.ExerciseService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/admin", layout = AppView.class)
public class AdminView extends VerticalLayout {
    Grid<Equipment> equipmentGrid = new Grid<>(Equipment.class, false);
    Button addNewEquipmentBtn = new Button("New equipment");
    EquipmentService equipmentService;

    Grid<Exercise> exerciseGrid = new Grid<>(Exercise.class, false);
    Button addNewExerciseBtn = new Button("New exercise");
    ExerciseService exerciseService;

    public AdminView(EquipmentService equipmentService, ExerciseService exerciseService){
        this.equipmentService = equipmentService;
        configEquipmentGrid();
        addNewEquipmentBtn.addClickListener(e -> {
           openEquipmentForm(null);
        });

        this.exerciseService = exerciseService;
        configExerciseGrid();
        addNewExerciseBtn.addClickListener(e -> {
            openExerciseForm(null);
        });


        createLayout();
        updateGrid();
    }

    private void createLayout() {
        add(addNewEquipmentBtn, equipmentGrid, addNewExerciseBtn, exerciseGrid);
    }

    private void configEquipmentGrid() {
        equipmentGrid.addColumn(Equipment::getName).setHeader("Name");
        equipmentGrid.addComponentColumn(equipment -> {
            Button deleteBtn = new Button(new Icon(VaadinIcon.CLOSE), e -> {
                equipmentService.deleteById(equipment.getId());
                updateGrid();
            });

            deleteBtn.addThemeVariants(
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_SMALL
            );

            return deleteBtn;
        }).setHeader("Delete");
        equipmentGrid.asSingleSelect().addValueChangeListener(e -> {
            if(!e.getHasValue().isEmpty()){
                openEquipmentForm(e.getValue());
            }
        });
    }

    private void configExerciseGrid() {
        exerciseGrid.addColumn(Exercise::getName).setHeader("Name");
        exerciseGrid.addColumn(exercise -> getEquipmentSetAsString(exercise)).setHeader("Equipment");
        exerciseGrid.addColumn(exercise -> exercise.getExerciseTag().getTag()).setHeader("Tags");
        exerciseGrid.addComponentColumn(exercise -> {
            Button deleteBtn = new Button(new Icon(VaadinIcon.CLOSE), e -> {
                exerciseService.deleteById(exercise.getId());
                updateGrid();
            });

            deleteBtn.addThemeVariants(
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_SMALL
            );

            return deleteBtn;
        }).setHeader("Delete");
        exerciseGrid.asSingleSelect().addValueChangeListener(e -> {
           if(!e.getHasValue().isEmpty()){
               openExerciseForm(e.getValue());
           }
        });
    }

    public void openEquipmentForm(Equipment selectedEquipment){
        Dialog dialog = new Dialog();
        EquipmentForm equipmentForm = new EquipmentForm(equipmentService, this);

        if(selectedEquipment == null){
            Equipment equipment = new Equipment();
            equipmentForm.setEquipmentBean(equipment, "Add new equipment");
        } else{
            equipmentForm.setEquipmentBean(selectedEquipment, "Edit equipment");
        }
        dialog.add(equipmentForm);
        dialog.open();
    }

    public void openExerciseForm(Exercise selectedExercise){
        Dialog dialog = new Dialog();
        ExerciseForm exerciseForm = new ExerciseForm(exerciseService, this);

        if(selectedExercise == null){
            Exercise exercise = new Exercise();
            exerciseForm.setExerciseBean(exercise, "Add new exercise");
        } else{
            exerciseForm.setExerciseBean(selectedExercise, "Edit exercise");
        }
        dialog.add(exerciseForm);
        dialog.open();
    }

    public String getEquipmentSetAsString(Exercise exercise){
        StringBuffer equipmentAsString = new StringBuffer("");
        exercise.getEquipmentSet().forEach(equipment -> {
            equipmentAsString.append(equipment.getName());
            equipmentAsString.append(", ");
        });
        return equipmentAsString.toString();
    }

    public void updateGrid(){
        equipmentGrid.setItems(equipmentService.findAll());
        exerciseGrid.setItems(exerciseService.findAllByCustomFilter(null, null));
    }
}
