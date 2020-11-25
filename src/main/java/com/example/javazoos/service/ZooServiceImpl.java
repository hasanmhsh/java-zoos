package com.example.javazoos.service;

import com.example.javazoos.model.Animal;
import com.example.javazoos.model.Telephone;
import com.example.javazoos.model.Zoo;
import com.example.javazoos.model.ZooAnimal;
import com.example.javazoos.repositories.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "zooService")
public class ZooServiceImpl implements ZooService {
    @Autowired
    private ZooRepository zooRepository;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private ZooAnimalService zooAnimalService;

    @Override
    public List<Zoo> getAllZoos() {
        List<Zoo> zoos = zooRepository.findAll();
        return zoos;
    }

    @Override
    public List<Zoo> getAllZoosSortedAndPaginated(int page, int pageSize) {
        if (page <= 0)
            page = 0;
        else
            page--;

        // In case of multiple element with the same Zooname , Zooid will be used to sort desc
        Pageable sortedByZoonameAscAndZooidDesc =
                PageRequest.of(page, pageSize, Sort.by("zooname")
                        .ascending()
                        .and(Sort.by("zooid")
                                .descending()));
        List<Zoo> zoos = zooRepository.findAllByZoonameContainingIgnoringCase("", sortedByZoonameAscAndZooidDesc);
        return zoos;
    }

    @Override
    public Zoo getZoo(long zooid) throws EntityNotFoundException {
        return zooRepository.findById(zooid)
                .orElseThrow(() -> new EntityNotFoundException("Zoo " + zooid + " not found!"));
    }

    @Transactional
    @Override
    public Zoo save(Zoo zoo) throws EntityNotFoundException {
        Zoo newZoo = null;
        if (zoo.getZooid() == 0) {
            // Create new zoo
            newZoo = new Zoo();
            newZoo.getOutcommingZooAnimals()
                    .clear();

            List<ZooAnimal> newZooAnimals = zoo.getZooAnimals();
            newZoo.getZooAnimals()
                    .clear();
            if (newZooAnimals.size() > 0) {
                for (ZooAnimal za : newZooAnimals) {
                    Animal animal = animalService.getAnimal(za.getAnimal()
                            .getAnimalid());
                    Zoo incomingZoo = null;
                    if (za.getIncomingzoo() != null && za.getIncomingzoo()
                            .getZooid() != 0) {
                        incomingZoo = getZoo(za.getIncomingzoo()
                                .getZooid());
                    }

                    // Difference (in case of creating new zoo)
                    newZoo.addZooAnimal(animal, incomingZoo);
                }
            }
        } else {
            // Replace existing zoo
            newZoo = zooRepository.findById(zoo.getZooid())
                    .orElseThrow(() -> new EntityNotFoundException("Zoo " + zoo.getZooid() + " not found!"));
            if (newZoo.getZooAnimals()
                    .size() > 0) {
                zooAnimalService.deleteZooAnimals(newZoo.getZooid());
            }

            List<ZooAnimal> newZooAnimals = zoo.getZooAnimals();
            newZoo.getZooAnimals()
                    .clear();
            if (newZooAnimals.size() > 0) {
                for (ZooAnimal za : newZooAnimals) {
                    Animal animal = animalService.getAnimal(za.getAnimal()
                            .getAnimalid());
                    Zoo incomingZoo = null;
                    if (za.getIncomingzoo() != null && za.getIncomingzoo()
                            .getZooid() != 0) {
                        incomingZoo = getZoo(za.getIncomingzoo()
                                .getZooid());
                    }


                    // Difference (in case of replacing existing zoo)
                    zooAnimalService.save(new ZooAnimal(newZoo, animal, incomingZoo));
                }
            }
        }
        newZoo.setZooname(zoo.getZooname());


        List<Telephone> newTelephones = zoo.getTelephones();
        newZoo.getTelephones()
                .clear();
        if (newTelephones.size() > 0) {
            for (Telephone t : newTelephones) {
                Telephone telephone = new Telephone();
                telephone.setPhonenumber(t.getPhonenumber());
                telephone.setPhonetype(t.getPhonetype());
                telephone.setZoo(newZoo);
                newZoo.getTelephones()
                        .add(telephone);
            }
        }

        return zooRepository.save(newZoo);
    }

    @Transactional
    @Override
    public Zoo update(long zooid, Zoo zoo) throws EntityNotFoundException {
        Zoo existingZoo = getZoo(zooid);

        if (zoo.getZooname() != null) {
            existingZoo.setZooname(zoo.getZooname());
        }

        if (zoo.getTelephones() != null && zoo.getTelephones()
                .size() > 0) {
            List<Telephone> newTelephones = zoo.getTelephones();
            existingZoo.getTelephones()
                    .clear();
            for (Telephone t : newTelephones) {
                Telephone telephone = new Telephone();
                telephone.setPhonenumber(t.getPhonenumber());
                telephone.setPhonetype(t.getPhonetype());
                telephone.setZoo(existingZoo);
                existingZoo.getTelephones()
                        .add(telephone);
            }
        }

        if (zoo.getZooAnimals() != null && zoo.getZooAnimals()
                .size() > 0) {
            zooAnimalService.deleteZooAnimals(existingZoo.getZooid());
            List<ZooAnimal> newZooAnimals = zoo.getZooAnimals();
            existingZoo.getZooAnimals()
                    .clear();
            for (ZooAnimal za : newZooAnimals) {
                Animal animal = animalService.getAnimal(za.getAnimal()
                        .getAnimalid());
                Zoo incomingZoo = null;
                if (za.getIncomingzoo() != null && za.getIncomingzoo()
                        .getZooid() != 0) {
                    incomingZoo = getZoo(za.getIncomingzoo()
                            .getZooid());
                }

                // Difference (in case of replacing existing zoo)
                zooAnimalService.save(new ZooAnimal(existingZoo, animal, incomingZoo));
            }
        }

        return zooRepository.save(existingZoo);

    }

    @Transactional
    @Override
    public void delete(long zooid) throws EntityNotFoundException {
        zooRepository.findById(zooid)
                .orElseThrow(() -> new EntityNotFoundException("Zoo id " + zooid + " not found!"));
        zooRepository.deleteById(zooid);
    }
}
