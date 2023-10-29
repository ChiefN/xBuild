package com.example.xbulild.data.equipment;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentService {
    EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    //Get data
    public List<Equipment> findAll(){
        return equipmentRepository.findAll();
    }
    public Equipment findById(String id) { return equipmentRepository.findById(id).orElseThrow(); }

    //Set data
    public void save(Equipment equipment) { equipmentRepository.save(equipment);}

    //Delete data
    public void deleteById(String id) { equipmentRepository.deleteById(id); }
}
