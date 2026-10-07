package ru.ksenia.NauJava.bussinessLogic;

import ru.ksenia.NauJava.entity.Guitar;

import java.util.List;

public interface GuitarService {
    void addNewGuitar(String brand, String model, int stringsCount, String pickupType, String Description);
    Guitar getGuitarById(Long id);
    void updateBrand(String brand, Long id);
    void updateModel(String model, Long id);
    void updateStringsCount(int count, Long id);
    void updatePickupType(String pickupType, Long id);
    void updateDescription(String description, Long id);
    void deleteGuitar(Long id);
    List<Guitar> getAllGuitars();
}
