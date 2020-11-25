package com.example.javazoos.service;

import com.example.javazoos.model.Animal;
import com.example.javazoos.views.AnimalWithCountedZoos;

import java.util.List;

public interface AnimalService {

    public List<AnimalWithCountedZoos> getCountedZoosAnimals();
    public List<Animal> getAllAnimals();
    public List<Animal> getAllAnimalsSortedAndPaginated(int page, int pageSize);
    public Animal getAnimal(long id);

    public Animal save(Animal animal);
    public Animal update(long animalid, Animal animal);
    public void delete(long animalid);

}
