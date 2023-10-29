package com.example.xbulild.vaadin.view;

import com.example.xbulild.data.exercise.tag.ExerciseTagService;
import com.example.xbulild.data.property.Property;
import com.example.xbulild.data.property.PropertyService;
import com.example.xbulild.vaadin.component.EquipmentForm;
import com.example.xbulild.vaadin.component.ExerciseForm;
import com.example.xbulild.data.equipment.Equipment;
import com.example.xbulild.data.exercise.Exercise;
import com.example.xbulild.data.equipment.EquipmentService;
import com.example.xbulild.data.exercise.ExerciseService;
import com.example.xbulild.vaadin.component.PropertyForm;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;

import java.util.Set;

@Route(value = "/admin", layout = AppView.class)
public class AdminView extends VerticalLayout {
    Grid<Exercise> exerciseGrid = new Grid<>(Exercise.class, false);
    Grid<Equipment> equipmentGrid = new Grid<>(Equipment.class, false);
    Grid<Property> propertyGrid = new Grid<>(Property.class, false);

    ExerciseService exerciseService;
    EquipmentService equipmentService;
    PropertyService propertyService;
    ExerciseTagService exerciseTagService;

    Button addNewExerciseBtn = new Button("New exercise");
    Button addNewEquipmentBtn = new Button("New equipment");
    Button addNewPropertyBtn = new Button("New property");

    public AdminView(EquipmentService equipmentService, ExerciseService exerciseService, PropertyService propertyService, ExerciseTagService exerciseTagService){
        this.exerciseService = exerciseService;
        this.equipmentService = equipmentService;
        this.propertyService = propertyService;
        this.exerciseTagService = exerciseTagService;

        addNewExerciseBtn.addClickListener(e -> { addNew("exercise"); });
        addNewEquipmentBtn.addClickListener(e -> addNew("equipment"));
        addNewPropertyBtn.addClickListener(e -> addNew("property"));

        configExerciseGrid();
        configEquipmentGrid();
        configPropertyGrid();

        createLayout();
        updateGrid();
    }

    private void createLayout() {
        add(addNewExerciseBtn, exerciseGrid, new Hr(),
                addNewEquipmentBtn, equipmentGrid, new Hr(),
                addNewPropertyBtn, propertyGrid, new Hr());
    }

    public void updateGrid(){
        propertyGrid.setItems(propertyService.findAll());
        equipmentGrid.setItems(equipmentService.findAll());
        exerciseGrid.setItems(exerciseService.findAllByCustomFilter(null, null, null));
    }

    private void addNew(String addNewWhat) {
        System.out.println("add new " + addNewWhat);
        Dialog dialog = new Dialog();
        Boolean open = true;
        switch(addNewWhat){
            case "exercise":
                ExerciseForm exerciseForm = new ExerciseForm(exerciseService, this, propertyService, equipmentService, exerciseTagService);
                Exercise exercise = new Exercise();
                exerciseForm.setExerciseBean(exercise, "Add new exercise");
                dialog.add(exerciseForm);
                break;
            case "equipment":
                EquipmentForm equipmentForm = new EquipmentForm(equipmentService, this);
                Equipment equipment = new Equipment();
                equipmentForm.setEquipmentBean(equipment);
                dialog.add(equipmentForm);
                break;
            case "property":
                PropertyForm propertyForm = new PropertyForm(propertyService, this);
                Property property = new Property();
                propertyForm.setPropertyBean(property);
                dialog.add(propertyForm);
                break;
            default:
                open = false;
                break;
        }

        if(open){
            dialog.open();
        }
    }

    private void configEquipmentGrid() {
        equipmentGrid.addColumn(Equipment::getName).setHeader("Name");
        equipmentGrid.addComponentColumn(equipment -> {
            Button deleteBtn = new Button(new Icon(VaadinIcon.CLOSE), e -> {
                if(equipment.getExerciseSet() != null || !equipment.getExerciseSet().isEmpty()) {
                    equipment.getExerciseSet().forEach(exercise -> {
                        exercise.removeEquipment(equipment);
                        exerciseService.save(exercise);
                    });
                }
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
    }

    private void configPropertyGrid() {
        propertyGrid.addColumn(Property::getCategory).setHeader("Category").setSortable(true);
        propertyGrid.addColumn(Property::getName).setHeader("Name");
        propertyGrid.addComponentColumn(property -> {
            Button deleteBtn = new Button(new Icon(VaadinIcon.CLOSE), e -> {
                if(property.getExerciseSet() != null || !property.getExerciseSet().isEmpty()) {
                    property.getExerciseSet().forEach(exercise -> {
                        exercise.removeProperty(property);
                        exerciseService.save(exercise);
                    });
                }
                propertyService.deleteById(property.getId());
                updateGrid();
            });
            deleteBtn.addThemeVariants(
                    ButtonVariant.LUMO_ERROR,
                    ButtonVariant.LUMO_PRIMARY,
                    ButtonVariant.LUMO_SMALL
            );
            return deleteBtn;
        }).setHeader("Delete");
    }

    private void configExerciseGrid() {
        exerciseGrid.addColumn(Exercise::getName).setHeader("Name");
        exerciseGrid.addColumn(exercise -> getEquipmentSetAsString(exercise)).setHeader("Equipment");
        propertyService.findDistinctCategory().forEach(categoryStr -> {
            exerciseGrid.addColumn(exercise -> getPropertyAsString(exercise, categoryStr)).setHeader(categoryStr);
        });
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

        exerciseGrid.setDetailsVisibleOnClick(true);
        exerciseGrid.setItemDetailsRenderer(setExerciseDetails());
    }

    private Renderer<Exercise> setExerciseDetails() {
        ComponentRenderer<FormLayout, Exercise> CR = new ComponentRenderer<>(exercise ->{

            ExerciseForm exerciseForm = new ExerciseForm(exerciseService, this, propertyService, equipmentService, exerciseTagService);
            exerciseForm.setExerciseBean(exercise,"Edit exercise");

            return exerciseForm;
        });
        return CR;
    }

    public String getPropertyAsString(Exercise exercise, String categoryStr) {
        StringBuffer propertyAsString = new StringBuffer("");
        exercise.getPropertySet().stream().filter(property -> property.getCategory().equals(categoryStr)).forEach(property -> {
            propertyAsString.append(property.getName());
            propertyAsString.append(", ");
        });
        return propertyAsString.toString();
    }

    public String getEquipmentSetAsString(Exercise exercise){
        StringBuffer equipmentAsString = new StringBuffer("");
        exercise.getEquipmentSet().forEach(equipment -> {
            equipmentAsString.append(equipment.getName());
            equipmentAsString.append(", ");
        });
        return equipmentAsString.toString();
    }
}
