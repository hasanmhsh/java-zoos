package com.example.javazoos.repositories;

import com.example.javazoos.model.ZooAnimal;
import com.example.javazoos.model.ZooAnimalPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

@RestResource(exported=false)
public interface ZooAnimalRepository extends JpaRepository<ZooAnimal, ZooAnimalPrimaryKey>  {
    @Transactional
    @Modifying
    public void deleteAllByZoo_Zooid(long zooid);

    @Transactional
    @Modifying
    public void deleteAllByAnimal_Animalid(long animalid);

}
