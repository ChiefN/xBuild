package com.example.xbulild.vaadin.component;

import com.example.xbulild.data.exercise.Exercise;
import com.example.xbulild.data.exercise.ExerciseService;
import com.example.xbulild.data.property.Property;
import com.example.xbulild.data.property.PropertyService;
import com.example.xbulild.vaadin.view.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class ExerciseForm extends FormLayout {
    H3 title = new H3("");
    TextField name = new TextField("Exercise name");;
    Button saveBtn = new Button("Save exercise");

    ExerciseService exerciseService;
    PropertyService propertyService;
    AdminView adminView;
    Binder<Exercise> binder = new BeanValidationBinder<>(Exercise.class);

    CheckboxGroup<Property> propertySet = new CheckboxGroup<>();

    public ExerciseForm(ExerciseService exerciseService, AdminView adminView, PropertyService propertyService){
        binder.bindInstanceFields(this);
        this.exerciseService = exerciseService;
        this.propertyService = propertyService;
        this.adminView = adminView;

        propertySet.setLabel("Props");
        propertySet.setItems(propertyService.findAll());
        propertySet.setItemLabelGenerator(Property::getName);
        binder.forField(propertySet).bind(Exercise::getPropertySet, Exercise::setPropertySet);

        /*propertyService.findDistinctCategory().forEach(categoryStr -> {

            CheckboxGroup<Property> pcg = new CheckboxGroup<>();
            pcg.setLabel(categoryStr);
            pcg.setItems(propertyService.findAllByCategory(categoryStr));

            pcg.setItemLabelGenerator(Property::getName);

            binder.forField(pcg).bind(Exercise::getPropertySet, Exercise::setPropertySet);

            pcg.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

            add(pcg);
        });*/

        saveBtn.addClickListener(e -> {
            handleSave();
        });

        add(title, name, propertySet, saveBtn);

    }

    private void handleSave() {
        Exercise exercise = binder.validate().getBinder().getBean();
        if(exercise.getId() == null){
            exerciseService.save(exercise);
        } else {
            exerciseService.editOne(exercise);
        }

        setExerciseBean(null, "ERROR");
        adminView.updateGrid();

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
        }
    }

}
