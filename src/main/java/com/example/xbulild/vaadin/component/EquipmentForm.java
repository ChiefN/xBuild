package com.example.xbulild.vaadin.component;

import com.example.xbulild.data.equipment.Equipment;
import com.example.xbulild.data.equipment.EquipmentService;
import com.example.xbulild.vaadin.view.AdminView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class EquipmentForm extends FormLayout {
    H3 title = new H3("Add new equipment");
    TextField name = new TextField("Equipment name");
    TextField url = new TextField("URL");
    Button saveBtn = new Button("Save equipment");
    Button cancelBtn = new Button("Cancel");

    EquipmentService equipmentService;

    AdminView adminView;

    Binder<Equipment> binder = new BeanValidationBinder<>(Equipment.class);

    public EquipmentForm(EquipmentService equipmentService, AdminView adminView){
        binder.bindInstanceFields(this);
        this.equipmentService = equipmentService;
        this.adminView = adminView;

        saveBtn.addClickListener(e -> { handleSave(); });
        cancelBtn.addClickListener(e -> { closeDialog(); });

        HorizontalLayout hl = new HorizontalLayout(name, url);
        HorizontalLayout hl2 = new HorizontalLayout(saveBtn, cancelBtn);
        VerticalLayout vl = new VerticalLayout(title, hl, hl2);
        add(vl);

    }

    private void handleSave() {
        Equipment equipment = binder.validate().getBinder().getBean();
        if(!equipment.isPrevSaved()){
            equipment.setPrevSaved(true);
            equipmentService.save(equipment);
        }

        setEquipmentBean(null);
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

    public void setEquipmentBean(Equipment equipment) {
        if(equipment != null){
            binder.setBean(equipment);
        }
    }
}
