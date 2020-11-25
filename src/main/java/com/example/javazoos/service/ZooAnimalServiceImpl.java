package com.example.javazoos.service;

import com.example.javazoos.model.Zoo;
import com.example.javazoos.model.ZooAnimal;
import com.example.javazoos.model.ZooAnimalPrimaryKey;
import com.example.javazoos.repositories.ZooAnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "zooAnimalService")
public class ZooAnimalServiceImpl implements ZooAnimalService {
    @Autowired
    private ZooAnimalRepository zooAnimalRepository;

    @Override
    public List<ZooAnimal> getAllZooAnimals() {
        return zooAnimalRepository.findAll();
    }

    @Override
    public List<ZooAnimal> getAllAnimalZoos() {
        return zooAnimalRepository.findAll();
    }

    @Override
    public boolean isZooAnimalComboExist(long zooid, long animalid) {
        ZooAnimal zooAnimal = zooAnimalRepository.findById(new ZooAnimalPrimaryKey(zooid, animalid))
                .orElse(null);
        if (zooAnimal == null)
            return false;
        return true;
    }


    @Override
    public ZooAnimal getZooAnimal(long zooid, long animalid) {
        ZooAnimal zooAnimal = zooAnimalRepository.findById(new ZooAnimalPrimaryKey(zooid, animalid))
                .orElse(null);
        return zooAnimal;
    }

    @Override
    public ZooAnimal save(ZooAnimal zooAnimal) throws EntityNotFoundException, EntityExistsException {
//        ZooAnimal newZooAnimal = new ZooAnimal(zooAnimal.getZoo(), zooAnimal.getAnimal(), zooAnimal.getIncomingzoo());
        return zooAnimalRepository.save(zooAnimal);
    }

    @Override
    public void delete(long zooid, long animalid) throws EntityNotFoundException {
        if(!isZooAnimalComboExist(zooid, animalid))
            throw new EntityNotFoundException("Zoo animal compo("+ zooid + " , " + animalid +") not found!");
        zooAnimalRepository.deleteById(new ZooAnimalPrimaryKey(zooid, animalid));
    }

    @Override
    public void deleteZooAnimals(long zooid) {
        zooAnimalRepository.deleteAllByZoo_Zooid(zooid);
    }

    @Override
    public void deleteAnimalZoos(long animalid) {
        zooAnimalRepository.deleteAllByAnimal_Animalid(animalid);
    }
}
