package ru.ksenia.NauJava.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.ksenia.NauJava.entity.Guitar;

import java.util.List;

@Component
public class GuitarCrud implements Crud<Guitar, Long> {
    private final List<Guitar> guitarContainer;

    @Autowired
    public GuitarCrud(List<Guitar> guitarContainer) {
        this.guitarContainer = guitarContainer;
    }

    private Long id = 1L;
    @Override
    public void create(Guitar guitar) {
        guitar.setId(id++);
        guitarContainer.add(guitar);
    }

    @Override
    public Guitar read(Long id) {
        for (Guitar guitar : guitarContainer) {
            if (guitar.getId().equals(id))
                return guitar;
        }
        return null;
    }

    @Override
    public void update(Guitar guitar) {
        if (guitarContainer.contains(guitar)) {
            Guitar guitarForUpd = read(guitar.getId());
            guitarForUpd.setBrand(guitar.getBrand());
            guitarForUpd.setModel(guitar.getModel());
            guitarForUpd.setStringsCount(guitar.getStringsCount());
            guitarForUpd.setPickupType(guitar.getPickupType());
            guitarForUpd.setDescription(guitar.getDescription());
        }
    }

    @Override
    public void delete(Long id) {
        guitarContainer.removeIf(guitar -> guitar.getId().equals(id));
    }

    public List<Guitar> readAll(){
        return guitarContainer;
    }
}
