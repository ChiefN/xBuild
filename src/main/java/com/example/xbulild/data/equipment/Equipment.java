package com.example.xbulild.data.equipment;

import com.example.xbulild.data.exercise.Exercise;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotBlank
    String name;

    String url;

    @ManyToMany(mappedBy = "equipmentSet")
    Set<Exercise> exerciseSet = new HashSet<>();
}
