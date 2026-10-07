package ru.ksenia.NauJava.bussinessLogic;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ksenia.NauJava.config.AppConfig;
import ru.ksenia.NauJava.crud.GuitarCrud;
import ru.ksenia.NauJava.entity.Guitar;

import java.util.List;
import java.util.function.Consumer;

@Service
public class GuitarServiceImpl implements GuitarService {
    private final GuitarCrud guitarCrud;
    private final AppConfig appConfig;

    @Autowired
    public GuitarServiceImpl(GuitarCrud guitarCrud, AppConfig appConfig) {
        this.guitarCrud = guitarCrud;
        this.appConfig = appConfig;
    }

    @PostConstruct
    public void init(){
        System.out.println("Приложение: " + appConfig.getAppName());
        System.out.println("Версия: " + appConfig.getAppVersion());
    }

    @Override
    public void addNewGuitar(String brand, String model, int stringsCount, String pickupType, String description) {
        Guitar guitar = new Guitar();
        guitar.setBrand(brand);
        guitar.setModel(model);
        guitar.setStringsCount(stringsCount);
        guitar.setPickupType(pickupType);
        guitar.setDescription(description);
        guitarCrud.create(guitar);
    }

    @Override
    public Guitar getGuitarById(Long id) {
        return guitarCrud.read(id);
    }

    private void updateGuitarField(Long id, Consumer<Guitar> setter) {
        Guitar guitar = getGuitarById(id);
        if (guitar != null) {
            setter.accept(guitar);
            guitarCrud.update(guitar);
        }
    }

    @Override
    public void updateBrand(String brand, Long id) {
        updateGuitarField(id, guitar -> guitar.setBrand(brand));
    }

    @Override
    public void updateModel(String model, Long id) {
        updateGuitarField(id, guitar -> guitar.setModel(model));
    }

    @Override
    public void updateStringsCount(int count, Long id) {
        updateGuitarField(id, guitar -> guitar.setStringsCount(count));
    }

    @Override
    public void updatePickupType(String pickupType, Long id) {
        updateGuitarField(id, guitar -> guitar.setPickupType(pickupType));
    }

    @Override
    public void updateDescription(String description, Long id) {
        updateGuitarField(id, guitar -> guitar.setDescription(description));
    }

    @Override
    public void deleteGuitar(Long id) {
        guitarCrud.delete(id);
    }

    @Override
    public List<Guitar> getAllGuitars() {
        return guitarCrud.readAll();
    }
}
