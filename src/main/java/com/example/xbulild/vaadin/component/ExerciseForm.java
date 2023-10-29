package com.example.xbulild.vaadin.component;

import com.example.xbulild.data.equipment.Equipment;
import com.example.xbulild.data.equipment.EquipmentService;
import com.example.xbulild.data.exercise.Exercise;
import com.example.xbulild.data.exercise.ExerciseService;
import com.example.xbulild.data.exercise.tag.ExerciseTag;
import com.example.xbulild.data.exercise.tag.ExerciseTagService;
import com.example.xbulild.data.property.Property;
import com.example.xbulild.data.property.PropertyService;
import com.example.xbulild.vaadin.view.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExerciseForm extends FormLayout {
    H3 title = new H3("");
    TextField name = new TextField("Exercise name");
    TextArea description = new TextArea("Description");
    TextField url = new TextField("URL");

    CheckboxGroup<Equipment> equipmentSet = new CheckboxGroup<>();
    CheckboxGroup<Property> propertySet = new CheckboxGroup<>();

    Button saveBtn = new Button("Save exercise");

    Button print = new Button("Print");

    ExerciseService exerciseService;
    PropertyService propertyService;
    EquipmentService equipmentService;
    ExerciseTagService exerciseTagService;
    List<CheckboxGroup<Property>> checkboxGroupList = new ArrayList<>();
    Set<Property> newPropertySet = new HashSet<>();

    AdminView adminView;

    Binder<Exercise> binder = new BeanValidationBinder<>(Exercise.class);

    public ExerciseForm(ExerciseService exerciseService, AdminView adminView, PropertyService propertyService, EquipmentService equipmentService, ExerciseTagService exerciseTagService){
        binder.bindInstanceFields(this);
        this.exerciseService = exerciseService;
        this.propertyService = propertyService;
        this.equipmentService = equipmentService;
        this.exerciseTagService = exerciseTagService;
        this.adminView = adminView;

        equipmentSet.setLabel("Equipment");
        equipmentSet.setItems(equipmentService.findAll());
        equipmentSet.setItemLabelGenerator(Equipment::getName);
        equipmentSet.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        binder.forField(equipmentSet).bind(Exercise::getEquipmentSet, Exercise::setEquipmentSet);

        HorizontalLayout propsContainer = new HorizontalLayout();

        propertyService.findDistinctCategory().forEach(categoryStr -> {

            CheckboxGroup<Property> pcg = new CheckboxGroup<>();
            pcg.setLabel(categoryStr);
            pcg.setItems(propertyService.findAllByCategory(categoryStr));
            pcg.setItemLabelGenerator(Property::getName);
            pcg.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
            pcg.addSelectionListener(e -> {
                if(e.getAddedSelection().size() != 0){
                    e.getAddedSelection().forEach(property -> {
                        newPropertySet.add(property);
                    });
                }
                if(e.getRemovedSelection().size() != 0){
                    e.getRemovedSelection().forEach(property -> {
                        newPropertySet.remove(property);
                    });
                }
            });

            checkboxGroupList.add(pcg);
            propsContainer.add(pcg);
        });

        saveBtn.addClickListener(e -> { handleSave(); });

        print.addClickListener(e -> {
            Exercise exercise = binder.validate().getBinder().getBean();
            System.out.println(exercise.isPrevSaved());
        });

        HorizontalLayout checkContainer = new HorizontalLayout(equipmentSet);
        checkContainer.setSizeFull();
        VerticalLayout vlLeft = new VerticalLayout(name, url, checkContainer, propsContainer);
        VerticalLayout vlRight = new VerticalLayout(description);
        HorizontalLayout hlContainer = new HorizontalLayout(vlLeft, vlRight);
        hlContainer.setSizeFull();
        VerticalLayout vl = new VerticalLayout(title, hlContainer, saveBtn, print);
        add(vl);
    }

    private void handleSave() {
        Exercise exercise = binder.validate().getBinder().getBean();
        exercise.setPropertySet(newPropertySet);
        if(!exercise.isPrevSaved()){
            ExerciseTag exerciseTag = new ExerciseTag();
            exerciseTag.setExercise(exercise);
            exercise.setExerciseTag(exerciseTag);
            exercise.setPrevSaved(true);
            exerciseService.save(exercise);
        } else {
            exerciseService.save(exercise);
        }

        setExerciseBean(null, "ERROR");
        adminView.updateGrid();

        closeDialog();
    }

    public void closeDialog(){
        this.getParent().ifPresent(component -> {
            if(component instanceof Dialog){
                ((Dialog) component).close();
            }
        });
    }

    public void setExerciseBean(Exercise exercise, String formTitle) {
        if(exercise != null){
            title.setText(formTitle);
            binder.setBean(exercise);
            checkboxGroupList.forEach(checkboxGroup -> {
                if(exercise.getPropertySet() != null) {
                    exercise.getPropertySet().forEach(property -> {
                        checkboxGroup.select(property);
                    });
                }
            });
        }
    }

}
