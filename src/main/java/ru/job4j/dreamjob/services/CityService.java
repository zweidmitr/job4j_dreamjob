package ru.job4j.dreamjob.services;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.persistence.CityStore;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Service
public class CityService {
    private final CityStore store;

    public CityService(CityStore store) {
        this.store = store;
    }

    public List<City> getAllCities() {
        return new ArrayList<>(store.findAll());
    }

    public City findById(int id) {
        return store.findById(id);
    }
}
