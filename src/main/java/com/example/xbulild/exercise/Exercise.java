package com.example.xbulild.exercise;

import com.example.xbulild.equipment.Equipment;
import com.example.xbulild.exercise.tag.ExerciseTag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    //Will make an eager call because we always want to know the equipments used.
    //Unidirectional relation. Exercises points to equipment but not the other way around
    //Ignore the set of exercises in equipment to prevet infinityloop
    //Cascadetyp persist. When exercise is saved. Its equipment is also saved
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "exercise_equipment",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id"))
    @JsonIgnoreProperties("exerciseSet")
    Set<Equipment> equipmentSet = new HashSet<>();

    @OneToOne(mappedBy = "exercise", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnoreProperties("exercise")
    ExerciseTag exerciseTag;

    public Exercise(String name) {
        this.name = name;
    }

    public void insertSingleIntoEquipmentSet(Equipment equipment){
        equipmentSet.add(equipment);
    }
}
