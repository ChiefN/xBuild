package com.example.xbulild.data.property;

import com.example.xbulild.data.exercise.Exercise;
import com.example.xbulild.data.exercise.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PropertyService {
    PropertyRepository propertyRepository;
    ExerciseRepository exerciseRepository;

    public PropertyService(PropertyRepository propertyRepository, ExerciseRepository exerciseRepository) {
        this.propertyRepository = propertyRepository;
        this.exerciseRepository = exerciseRepository;
    }

    //Get data
    public List<Property> findAll(){return propertyRepository.findAll();}
    public List<String> findDistinctCategory() { return propertyRepository.findDistinctCategory(); }
    public Set<Property> findAllByCategory(String category) { return propertyRepository.findAllByCategory(category); }
    public Property findById(String id) { return propertyRepository.findById(id).orElseThrow(); }

    //Set data
    public void save(Property property){propertyRepository.save(property);}

    //Delete data
    public void deleteById(String id) {
        propertyRepository.deleteById(id);
    }
}
