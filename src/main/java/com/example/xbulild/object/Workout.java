package com.example.xbulild.object;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
