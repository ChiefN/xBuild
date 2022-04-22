package com.example.xbulild.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter

@Document("workouts")
public class Workout {

    @Id
    String id;

    String name;

    public Workout(String name) {
        this.name = name;
    }
}
