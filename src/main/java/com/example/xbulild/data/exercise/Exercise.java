package com.example.xbulild.data.exercise;

import com.example.xbulild.data.AbstractEntity;
import com.example.xbulild.data.property.Property;
import com.example.xbulild.data.equipment.Equipment;
import com.example.xbulild.data.exercise.tag.ExerciseTag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

@Entity
public class Exercise extends AbstractEntity {

    @NotBlank
    String name;

    String description;
    String url;

    //Will make an eager call because we always want to know the equipments used.
    //Ignore the set of exercises in equipment to prevent infinityloop
    //Cascadetyp persist. When exercise is saved. Its equipment is also saved
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "exercise_equipment",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    @JsonIgnoreProperties("exerciseSet")
    Set<Equipment> equipmentSet = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "exercise_property",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "property_id"))
    @JsonIgnoreProperties("exerciseSet")
    Set<Property> propertySet = new HashSet<>();

    @OneToOne(mappedBy = "exercise", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnoreProperties("exercise")
    ExerciseTag exerciseTag;

    public void addEquipment(Equipment equipment){
        this.equipmentSet.add(equipment);
        equipment.getExerciseSet().add(this);
    }

    public void removeEquipment(Equipment equipment){
        this.equipmentSet.remove(equipment);
        equipment.getExerciseSet().remove(this);
    }

    public void addProperty(Property property){
        this.propertySet.add(property);
        property.getExerciseSet().add(this);
    }

    public void removeProperty(Property property){
        this.propertySet.remove(property);
        property.getExerciseSet().remove(this);
    }
}
