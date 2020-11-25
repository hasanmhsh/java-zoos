package com.example.javazoos.controller;

import com.example.javazoos.model.ZooAnimal;
import com.example.javazoos.service.ZooAnimalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zooanimal")
public class ZooAnimalController {
    @Autowired
    private ZooAnimalService zooAnimalService;

    @GetMapping(value = "/{zooid}/{animalid}", produces = {"application/json"})
    public ResponseEntity<?> getZooAnimalExistance(@PathVariable long zooid,
                                                   @PathVariable long animalid)  {
        boolean isExist = zooAnimalService.isZooAnimalComboExist(zooid,animalid);
        ZooAnimal zooAnimal = zooAnimalService.getZooAnimal(zooid,animalid);
        return new ResponseEntity<>(new Object(){
            public final boolean iszooanimalcomboexist = isExist;
            public final ZooAnimal zooanimal = zooAnimal;
        }, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{zooid}/{animalid}", produces = {"application/json"})
    public ResponseEntity<?> deleteZooAnimal(@PathVariable long zooid,
                                             @PathVariable long animalid)  {
        zooAnimalService.delete(zooid, animalid);

        return new ResponseEntity<>(new Object(){
            public final boolean successful = true;
            public final List<ZooAnimal> zooanimals = zooAnimalService.getAllZooAnimals();
        }, HttpStatus.OK);
    }

    @DeleteMapping(value = "/byzoo/{zooid}", produces = {"application/json"})
    public ResponseEntity<?> deleteZooAnimals(@PathVariable long zooid)  {
        zooAnimalService.deleteZooAnimals(zooid);

        return new ResponseEntity<>(new Object(){
            public final boolean successful = true;
            public final List<ZooAnimal> zooanimals = zooAnimalService.getAllZooAnimals();
        }, HttpStatus.OK);
    }

    @DeleteMapping(value = "/byanimal/{animalid}", produces = {"application/json"})
    public ResponseEntity<?> deleteAnimalZoos(@PathVariable long animalid)  {
        zooAnimalService.deleteAnimalZoos(animalid);

        return new ResponseEntity<>(new Object(){
            public final boolean successful = true;
            public final List<ZooAnimal> animalzoos = zooAnimalService.getAllAnimalZoos();
        }, HttpStatus.OK);
    }

    @PostMapping(value = "/zooanimal", consumes = {"application/json"})
    public ResponseEntity<?> createNewZooAnimal(@RequestBody ZooAnimal zooAnimal){
        /*
        this json works in this endpoint to create new zooanimal

        {
            "zoo": {
                "zooid": 1
            },
            "animal": {
                "animalid": 2
            },
            "incomingzoo": {
                "zooid": 5
            }
        }

         */
        zooAnimalService.save(zooAnimal);

        return new ResponseEntity<>(new Object(){
            public final boolean successful = true;
            public final ZooAnimal createdZooanimal = zooAnimalService.getZooAnimal(zooAnimal.getZoo().getZooid(),zooAnimal.getAnimal().getAnimalid());
        }, HttpStatus.OK);
    }
}
