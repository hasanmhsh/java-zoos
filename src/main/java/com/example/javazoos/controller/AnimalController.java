package com.example.javazoos.controller;

import com.example.javazoos.model.Animal;
import com.example.javazoos.model.ZooAnimal;
import com.example.javazoos.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @GetMapping(value = "/animals", produces = {"application/json"})
    public ResponseEntity<?> getAllAnimals() {
        return new ResponseEntity<>(animalService.getAllAnimals(), HttpStatus.OK);
    }

    // http://localhost:2019/animals/count
    @GetMapping(value = "/count", produces = {"application/json"})
    public ResponseEntity<?> getAnimalsWithCountedZoos() {
        return new ResponseEntity<>(animalService.getCountedZoosAnimals(), HttpStatus.OK);
    }

    // http://localhost:2019/animals/paginated?page=1&pageSize=2
    @GetMapping(value = "/paginated", produces = {"application/json"})
    public ResponseEntity<?> getAllAnimalsPaginated(@RequestParam int page,
                                                 @RequestParam int pageSize) {
        return new ResponseEntity<>(animalService.getAllAnimalsSortedAndPaginated(page, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/animal/{animalid}", produces = {"application/json"})
    public ResponseEntity<?> getAnimal(@PathVariable long animalid) {
        return new ResponseEntity<>(animalService.getAnimal(animalid), HttpStatus.OK);
    }

    @PostMapping(value = "/animal", consumes = {"application/json"})
    public ResponseEntity<?> createNewAnimal(@RequestBody Animal animal){
        animal.setAnimalid(0);
        Animal newAnimal = animalService.save(animal);
        return new ResponseEntity<>(newAnimal, HttpStatus.OK);
    }

    @PutMapping(value = "/animal/{id}", consumes = {"application/json"})
    public ResponseEntity<?> replaceExistingZoo(@RequestBody Animal animal, @PathVariable long id){
        animal.setAnimalid(id);
        Animal newAnimal = animalService.save(animal);
        return new ResponseEntity<>(newAnimal, HttpStatus.OK);
    }

    @PatchMapping(value = "/animal/{id}", consumes = {"application/json"})
    public ResponseEntity<?> updateExistingZoo(@RequestBody Animal animal, @PathVariable long id){
        Animal newAnimal = animalService.update(id,animal);
        return new ResponseEntity<>(newAnimal, HttpStatus.OK);
    }

    @DeleteMapping(value = "/animal/{animalid}", produces = {"application/json"})
    public ResponseEntity<?> deleteZoo(@PathVariable long animalid)  {
        animalService.delete(animalid);

        return new ResponseEntity<>(new Object(){
            public final boolean successful = true;
            public final List<Animal> animals = animalService.getAllAnimals();
        }, HttpStatus.OK);
    }
}
