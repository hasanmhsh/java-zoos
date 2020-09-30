package com.hms.javazoos.services;

import com.hms.javazoos.models.Animal;
import com.hms.javazoos.repositories.AnimalRepository;
import com.hms.javazoos.views.AnimalsCountable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service(value = "animalService")
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    @Override
    public Animal findAnimalById(long id) throws EntityNotFoundException {
        return animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal id " + id + " not found!"));
    }

    @Override
    public List<AnimalsCountable> getAnimalsZoosCount() {
        return animalRepository.getCountedAnimalZoos();
    }
}
