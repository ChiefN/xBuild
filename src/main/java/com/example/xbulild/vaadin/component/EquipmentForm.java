package com.example.xbulild.vaadin.component;

import com.example.xbulild.data.equipment.Equipment;
import com.example.xbulild.data.equipment.EquipmentService;
import com.example.xbulild.vaadin.view.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class EquipmentForm extends FormLayout {
    H3 title = new H3("");
    TextField name = new TextField("Equipment name");;
    Button saveBtn = new Button("Save equipment");

    EquipmentService equipmentService;
    AdminView adminView;
    Binder<Equipment> binder = new BeanValidationBinder<>(Equipment.class);

    public EquipmentForm(EquipmentService equipmentService, AdminView adminView){
        binder.bindInstanceFields(this);
        this.equipmentService = equipmentService;
        this.adminView = adminView;

        saveBtn.addClickListener(e -> {
           handleSave();
        });

        add(title, name, saveBtn);

    }

    private void handleSave() {
        Equipment equipment = binder.validate().getBinder().getBean();
        if(equipment.getId() == null){
            equipmentService.save(equipment);
        } else {
            equipmentService.editOne(equipment);
        }

        setEquipmentBean(null, "ERROR");
        adminView.updateGrid();

        this.getParent().ifPresent(component -> {
            if(component instanceof Dialog){
                ((Dialog) component).close();
            }
        });
    }

    public void setEquipmentBean(Equipment equipment, String formTitle) {
        if(equipment != null){
            title.setText(formTitle);
            binder.setBean(equipment);
        }
    }
}
