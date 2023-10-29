package com.example.xbulild.vaadin.component;

import com.example.xbulild.data.equipment.Equipment;
import com.example.xbulild.data.equipment.EquipmentService;
import com.example.xbulild.data.property.Property;
import com.example.xbulild.data.property.PropertyService;
import com.example.xbulild.vaadin.view.AdminView;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class PropertyForm extends FormLayout {
    H3 title = new H3("Add new property");
    TextField category = new TextField("Prop. Category");;
    TextField name = new TextField("Prop. name");;
    Button saveBtn = new Button("Save property");
    Button cancelBtn = new Button("Cancel");

    PropertyService propertyService;

    AdminView adminView;

    Binder<Property> binder = new BeanValidationBinder<>(Property.class);

    public PropertyForm(PropertyService propertyService, AdminView adminView){
        binder.bindInstanceFields(this);
        this.propertyService = propertyService;
        this.adminView = adminView;

        saveBtn.addClickListener(e -> { handleSave(); });
        cancelBtn.addClickListener(e -> { closeDialog(); });


        HorizontalLayout hl = new HorizontalLayout(category, name);
        HorizontalLayout hl2 = new HorizontalLayout(saveBtn, cancelBtn);
        VerticalLayout vl = new VerticalLayout(title, hl, hl2);
        add(vl);
    }

    private void handleSave() {
        Property property = binder.validate().getBinder().getBean();
        if(!property.isPrevSaved()){
            property.setPrevSaved(true);
            propertyService.save(property);
        }

        setPropertyBean(null);
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

    public void setPropertyBean(Property property) {
        if(property != null) {
            binder.setBean(property);
        }
    }
}
