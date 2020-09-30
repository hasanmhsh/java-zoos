package com.hms.javazoos.repositories;

import com.hms.javazoos.models.Animal;
import com.hms.javazoos.views.AnimalsCountable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    @Query(value = "SELECT a.animaltype as animaltype , a.animalid as animalid, count(za.animalid) as countzoos FROM animals a JOIN zooanimals za ON a.animalid = za.animalid GROUP BY a.animalid ORDER BY a.animalid" , nativeQuery = true)
    public List<AnimalsCountable> getCountedAnimalZoos();
}
