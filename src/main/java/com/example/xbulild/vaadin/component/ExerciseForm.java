package com.example.xbulild.vaadin.component;

import com.example.xbulild.exercise.Exercise;
import com.example.xbulild.exercise.ExerciseService;
import com.example.xbulild.vaadin.view.AdminView;
import com.vaadin.flow.component.button.Button;
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
    AdminView adminView;
    Binder<Exercise> binder = new BeanValidationBinder<>(Exercise.class);

    public ExerciseForm(ExerciseService exerciseService, AdminView adminView){
        binder.bindInstanceFields(this);
        this.exerciseService = exerciseService;
        this.adminView = adminView;

        saveBtn.addClickListener(e -> {
            handleSave();
        });

        add(title, name, saveBtn);

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
