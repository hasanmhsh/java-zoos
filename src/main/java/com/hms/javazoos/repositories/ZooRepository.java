package com.hms.javazoos.repositories;

import com.hms.javazoos.models.Zoo;
import com.hms.javazoos.views.Countable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ZooRepository extends CrudRepository<Zoo, Long> {
    public List<Zoo> findByZoonameContainingIgnoreCase(String part);
    public Zoo findByZoonameEqualsIgnoreCase(String name);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM zooanimals WHERE zooid=:zooid AND animalid=:animalid",nativeQuery=true)
    public void deleteZooAnimal(long zooid,long animalid);

    @Transactional
    @Modifying
    @Query(value="DELETE FROM zooanimals WHERE zooid=:zooid",nativeQuery=true)
    public void deleteAllZooAnimals(long zooid);

    @Transactional
    @Modifying
    @Query(value="INSERT INTO zooanimals(zooid, animalid, created_by, created_date, last_modified_by, last_modified_date) VALUES (:zooid, :animalid, :auditorusername, CURRENT_TIMESTAMP, :auditorusername, CURRENT_TIMESTAMP)",nativeQuery=true)
    public void addZooAnimal(String auditorusername,long zooid,long animalid);

    @Query(value="SELECT COUNT(*) AS count FROM zooanimals WHERE zooid = :zooid",nativeQuery=true)
    public Countable getZooAnimalsCount(long zooid);

    @Query(value="SELECT COUNT(*) AS count FROM zooanimals WHERE zooid = :zooid AND animalid=:animalid",nativeQuery=true)
    public Countable getZooAnimalCombositeCount(long zooid,long animalid);
}
