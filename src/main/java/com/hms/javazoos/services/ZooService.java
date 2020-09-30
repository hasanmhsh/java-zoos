package com.hms.javazoos.services;

import com.hms.javazoos.models.Zoo;
import com.hms.javazoos.models.ZooAnimal;

import java.util.List;

public interface ZooService {
    public Zoo findZooById(long id);
    public List<Zoo> findAll();
    public List<Zoo> findByPartOfName(String part);
    public Zoo findByName(String name);
    public void delete(long id);
    public Zoo save(Zoo zoo);
    public void deleteZooAnimal(long zooid,long animalid);
    public void addZooAnimal(long zooid, long animalid);
    public Zoo update(Zoo zoo,long id);
}
