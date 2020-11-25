package com.example.javazoos.service;

import com.example.javazoos.model.Zoo;
import com.example.javazoos.model.Animal;
import com.example.javazoos.model.ZooAnimal;

import java.util.List;

public interface ZooAnimalService {

    public List<ZooAnimal> getAllZooAnimals();
    public List<ZooAnimal> getAllAnimalZoos();
    public boolean isZooAnimalComboExist(long zooid, long animalid);
    public ZooAnimal getZooAnimal(long zooid, long animalid);

    public ZooAnimal save(ZooAnimal zooAnimal);
    public void delete(long zooid, long animalid);
    public void deleteZooAnimals(long zooid);
    public void deleteAnimalZoos(long animalid);
}
