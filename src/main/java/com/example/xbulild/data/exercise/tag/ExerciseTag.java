package com.example.xbulild.data.exercise.tag;

import com.example.xbulild.data.exercise.Exercise;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter

@Entity
public class ExerciseTag {
    @Id
    @Column(name = "exercise_id")
    private Integer id;

    public String tag;

    @OneToOne
    @MapsId
    @JoinColumn(name = "exercise_id")
    Exercise exercise;
}
