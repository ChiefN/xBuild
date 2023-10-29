package com.example.xbulild.data.property;

import com.example.xbulild.data.AbstractEntity;
import com.example.xbulild.data.exercise.Exercise;
import com.example.xbulild.util.Util;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter

@Entity
public class Property extends AbstractEntity {

    @NotBlank
    public String category;

    @NotBlank
    public String name;

    @ManyToMany(mappedBy = "propertySet", fetch = FetchType.EAGER)
    Set<Exercise> exerciseSet  = new HashSet<Exercise>();
}
