package com.example.javazoos.service;

import com.example.javazoos.model.Animal;
import com.example.javazoos.model.Telephone;
import com.example.javazoos.model.Zoo;
import com.example.javazoos.model.ZooAnimal;
import com.example.javazoos.repositories.AnimalRepository;
import com.example.javazoos.views.AnimalWithCountedZoos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service(value = "animalService")
public class AnimalServiceImpl implements AnimalService {
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private ZooService zooService;

    @Autowired
    private ZooAnimalService zooAnimalService;

    @Override
    public List<AnimalWithCountedZoos> getCountedZoosAnimals() {
        return animalRepository.getAnimalsWithCountedZoos_LEFTJOIN_WithZeroCountedZoosAnimals();
    }

    @Override
    public List<Animal> getAllAnimals() {
        List<Animal> animals = animalRepository.findAll();
        return animals;
    }

    @Override
    public List<Animal> getAllAnimalsSortedAndPaginated(int page, int pageSize) {
        if (page <= 0)
            page = 0;
        else
            page--;
        // In case of multiple element with the same Animaltype , Animalid will be used to sort desc
        Pageable sortedByAnimaltypeAscAndAnimalidDesc =
                PageRequest.of(page, pageSize, Sort.by("animaltype")
                        .ascending()
                        .and(Sort.by("animalid")
                                .descending()));
        List<Animal> animals = animalRepository.findAllByAnimaltypeContainingIgnoringCase("", sortedByAnimaltypeAscAndAnimalidDesc);
        return animals;
    }

    @Override
    public Animal getAnimal(long id) throws EntityNotFoundException {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal " + id + " not found!"));
        return animal;
    }

    @Transactional
    @Override
    public Animal save(Animal animal) throws EntityNotFoundException {
        Animal newAnimal = null;
        if (animal.getAnimalid() == 0) {
            // Create new zoo
            newAnimal = new Animal();

            List<ZooAnimal> newAnimalZoos = animal.getAnimalZoos();
            newAnimal.getAnimalZoos()
                    .clear();
            if (newAnimalZoos.size() > 0) {
                for (ZooAnimal za : newAnimalZoos) {
                    Zoo zoo = zooService.getZoo(za.getZoo()
                            .getZooid());
                    Zoo incomingZoo = null;
                    if (za.getIncomingzoo() != null && za.getIncomingzoo()
                            .getZooid() != 0) {
                        incomingZoo = zooService.getZoo(za.getIncomingzoo()
                                .getZooid());
                    }

                    // Difference (in case of creating new zoo)
                    newAnimal.addAnimalZoo(zoo, incomingZoo);
                }
            }
        } else {
            // Replace existing zoo
            newAnimal = animalRepository.findById(animal.getAnimalid())
                    .orElseThrow(() -> new EntityNotFoundException("Animal " + animal.getAnimalid() + " not found!"));
            if (newAnimal.getAnimalZoos()
                    .size() > 0) {
                zooAnimalService.deleteAnimalZoos(newAnimal.getAnimalid());
            }

            List<ZooAnimal> newAnimalZoos = animal.getAnimalZoos();
            newAnimal.getAnimalZoos()
                    .clear();
            if (newAnimalZoos.size() > 0) {
                for (ZooAnimal za : newAnimalZoos) {
                    Zoo zoo = zooService.getZoo(za.getZoo()
                            .getZooid());
                    Zoo incomingZoo = null;
                    if (za.getIncomingzoo() != null && za.getIncomingzoo()
                            .getZooid() != 0) {
                        incomingZoo = zooService.getZoo(za.getIncomingzoo()
                                .getZooid());
                    }


                    // Difference (in case of replacing existing zoo)
                    zooAnimalService.save(new ZooAnimal(zoo, newAnimal, incomingZoo));
                }
            }
        }
        newAnimal.setAnimaltype(animal.getAnimaltype());


        return animalRepository.save(newAnimal);
    }

    @Transactional
    @Override
    public Animal update(long animalid, Animal animal) {
        Animal existingAnimal = getAnimal(animalid);

        if (animal.getAnimaltype() != null) {
            existingAnimal.setAnimaltype(animal.getAnimaltype());
        }

        if (animal.getAnimalZoos() != null && animal.getAnimalZoos()
                .size() > 0) {
            zooAnimalService.deleteAnimalZoos(existingAnimal.getAnimalid());

            List<ZooAnimal> newAnimalZoos = animal.getAnimalZoos();
            existingAnimal.getAnimalZoos()
                    .clear();
            for (ZooAnimal za : newAnimalZoos) {
                Zoo zoo = zooService.getZoo(za.getZoo()
                        .getZooid());
                Zoo incomingZoo = null;
                if (za.getIncomingzoo() != null && za.getIncomingzoo()
                        .getZooid() != 0) {
                    incomingZoo = zooService.getZoo(za.getIncomingzoo()
                            .getZooid());
                }


                // Difference (in case of replacing existing zoo)
                zooAnimalService.save(new ZooAnimal(zoo, existingAnimal, incomingZoo));
            }

        }

        return animalRepository.save(existingAnimal);
    }

    @Transactional
    @Override
    public void delete(long animalid) throws EntityNotFoundException {
        animalRepository.findById(animalid)
                .orElseThrow(() -> new EntityNotFoundException(
                        ("Animal " + animalid + " not found!")
                ));
        animalRepository.deleteById(animalid);
    }
}
