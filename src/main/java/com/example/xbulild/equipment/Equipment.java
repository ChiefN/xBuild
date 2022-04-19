package com.example.xbulild.equipment;

import com.example.xbulild.exercise.Exercise;
import lombok.*;

import javax.persistence.*;
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
    public Integer id;

    public String name;

    @ManyToMany
    Set<Exercise> exerciseSet  = new HashSet<>();;
}
