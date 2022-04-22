package com.example.xbulild.data.property;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PropertyService {
    PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> findAll(){return propertyRepository.findAll();}

    public List<String> findDistinctCategory() { return propertyRepository.findDistinctCategory(); }

    public Set<Property> findAllByCategory(String category) { return propertyRepository.findAllByCategory(category); }
}
