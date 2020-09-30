package com.hms.javazoos.services;

import com.hms.javazoos.models.Animal;
import com.hms.javazoos.models.Telephone;
import com.hms.javazoos.models.Zoo;
import com.hms.javazoos.models.ZooAnimal;
import com.hms.javazoos.repositories.TelephoneRepository;
import com.hms.javazoos.repositories.ZooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "zooService")
public class ZooServiceImpl implements ZooService {
    @Autowired
    private ZooRepository zooRepository;

    @Autowired
    private TelephoneRepository telephoneRepository;

    @Autowired
    private AnimalService animalService;

    @Override
    public Zoo findZooById(long id) throws EntityNotFoundException {
        return zooRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zoo id " + id + "not found!"));
    }

    @Override
    public List<Zoo> findAll() {
        List<Zoo> zoos = new ArrayList<>();
        zooRepository.findAll()
                .iterator()
                .forEachRemaining(zoos::add);
        return zoos;
    }

    @Override
    public List<Zoo> findByPartOfName(String part) {
        return  zooRepository
                .findByZoonameContainingIgnoreCase(part.toLowerCase());
    }

    @Override
    public Zoo findByName(String name) {
        return zooRepository.findByZoonameEqualsIgnoreCase(name);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Zoo oldZoo = findZooById(id);
        //The following part of code lead delete function to throw exception
//        if(oldZoo.getAnimals().size()>0){
//            //delete old zoo animals
//            for(ZooAnimal zooAnimal : oldZoo.getAnimals()){
//                deleteZooAnimal(oldZoo.getZooid(),zooAnimal.getAnimal().getAnimalid());
//            }
//        }
        zooRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Zoo save(Zoo zoo) {
        Zoo newZoo = new Zoo();
        if(zoo.getZooid()!=0){
            Zoo oldZoo = findZooById(zoo.getZooid());
            if(oldZoo.getAnimals().size()>0){
                //delete old zoo animals
                for(ZooAnimal zooAnimal : oldZoo.getAnimals()){
                    deleteZooAnimal(oldZoo.getZooid(),zooAnimal.getAnimal().getAnimalid());
                }
            }
            newZoo.setZooid(oldZoo.getZooid());
        }
        newZoo.setZooname(zoo.getZooname());
        newZoo.getTelephones().clear();
        for(Telephone phone : zoo.getTelephones()){
            Telephone newPhone = new Telephone();
            newPhone.setPhonenumber(phone.getPhonenumber());
            newPhone.setPhonetype(phone.getPhonetype());
            newZoo.getTelephones().add(newPhone);
            newPhone.setZoo(newZoo);
        }

        newZoo.getAnimals().clear();
        if(zoo.getAnimals() != null && zoo.getAnimals().size() >0) {
            if(newZoo.getZooid()==0){
                for (ZooAnimal zooAnimal : zoo.getAnimals()) {
                    Animal animal = animalService.findAnimalById(zooAnimal.getAnimal().getAnimalid());
                    newZoo.addAnimal(animal);
                }
            }
            else {
                for (ZooAnimal zooAnimal : zoo.getAnimals()) {
                    addZooAnimal(zoo.getZooid(), zooAnimal.getAnimal()
                            .getAnimalid());
                }
            }
        }
        return zooRepository.save(newZoo);
    }

    @Transactional
    @Override
    public Zoo update(Zoo zoo,long id) {
        Zoo currentZoo = findZooById(id);
        if(zoo.getAnimals()!=null && zoo.getAnimals().size()>0){
            //delete old zoo animals
            for(ZooAnimal zooAnimal : currentZoo.getAnimals()){
                    deleteZooAnimal(currentZoo.getZooid(),zooAnimal.getAnimal().getAnimalid());
            }

            //add new zoo animals
            for (ZooAnimal zooAnimal : zoo.getAnimals()) {
                addZooAnimal(currentZoo.getZooid(), zooAnimal.getAnimal()
                                                      .getAnimalid());
            }
        }
        if(zoo.getZooname() != null)
            currentZoo.setZooname(zoo.getZooname());

        if(zoo.getTelephones()!=null && zoo.getTelephones().size()>0) {
            currentZoo.getTelephones()
                    .clear();
            for (Telephone phone : zoo.getTelephones()) {
                Telephone newPhone = new Telephone();
                newPhone.setPhonenumber(phone.getPhonenumber());
                newPhone.setPhonetype(phone.getPhonetype());
                newPhone.setZoo(currentZoo);
                currentZoo.getTelephones()
                        .add(newPhone);
            }
        }


        return zooRepository.save(currentZoo);
    }


    @Transactional
    @Override
    public void addZooAnimal(long zooid, long animalid) {
        animalService.findAnimalById(animalid);
        findZooById(zooid);
        if(zooRepository.getZooAnimalCombositeCount(zooid,animalid).getCount() <= 0){
            zooRepository.addZooAnimal("SYSTEM",zooid,animalid);
        }
        else{
            throw new EntityExistsException("Zoo animal combination already exists!");
        }
    }

    @Transactional
    @Override
    public void deleteZooAnimal(long zooid,long animalid){
        findZooById(zooid);
        animalService.findAnimalById(animalid);
        if(zooRepository.getZooAnimalCombositeCount(zooid,animalid).getCount() > 0)
            zooRepository.deleteZooAnimal(zooid,animalid);
        else
            throw new EntityNotFoundException("Zoo and Animal Combination Does Not Exists");
    }






}
