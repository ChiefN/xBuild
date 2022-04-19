package com.example.xbulild.equipment;

import com.example.xbulild.equipment.Equipment;
import com.example.xbulild.equipment.EquipmentRepository;
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

    public void save(Equipment equipment) { equipmentRepository.save(equipment);}

    public void editOne(Equipment equipment) {
        Equipment equipmentInDB = equipmentRepository.findById(equipment.getId()).orElseThrow();
        if(equipment.getName() != null && !equipment.getName().isEmpty() && !equipment.getName().isBlank()){
            equipmentInDB.setName(equipment.getName());
        }
        equipmentRepository.save(equipmentInDB);
    }

    public void deleteById(Integer id) {
        equipmentRepository.deleteById(id);
    }
}
