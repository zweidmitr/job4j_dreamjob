package ru.job4j.dreamjob.persistence;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.City;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
@Repository
public class CityStore {
    private final Map<Integer, City> cities = new ConcurrentHashMap<>();

    public CityStore() {
        cities.put(1, new City(1, "Мск"));
        cities.put(2, new City(2, "СПб"));
        cities.put(3, new City(3, "Екб"));
        cities.put(4, new City(4, "НН"));
        cities.put(5, new City(5, "Piter"));
    }

    public Collection<City> findAll() {
        return new ArrayList<>(cities.values());
    }

    public City findById(int id) {
        return cities.get(id);
    }
}
