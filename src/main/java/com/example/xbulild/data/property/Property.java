package com.example.xbulild.data.property;

import com.example.xbulild.data.AbstractEntity;
import com.example.xbulild.data.exercise.Exercise;
import com.example.xbulild.util.Util;
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
public class Property extends AbstractEntity {
    public String category;

    public String name;

    @ManyToMany(mappedBy = "propertySet")
    Set<Exercise> exerciseSet  = new HashSet<Exercise>();
}
