package com.hms.javazoos.controllers;

import com.hms.javazoos.models.Zoo;
import com.hms.javazoos.services.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/zoos")
public class ZooController {
    @Autowired
    private ZooService zooService;

    @GetMapping(value = "/zoos", produces = {"application/json"})
    public ResponseEntity<?> getAllZoos(){
        List<Zoo> zoos = zooService.findAll();
        return new ResponseEntity<>(zoos, HttpStatus.OK);
    }

    @GetMapping(value = "/zoo/{id}", produces = {"application/json"})
    public ResponseEntity<?> getZooById(@PathVariable long id){
        Zoo zoo = zooService.findZooById(id);
        return new ResponseEntity<>(zoo, HttpStatus.OK);
    }

    @PostMapping(value = "/zoo",consumes = {"application/json"})
    public ResponseEntity<?> createNewZoo(@RequestBody Zoo zoo){
        zoo.setZooid(0);
        return new ResponseEntity<>(zooService.save(zoo),HttpStatus.OK);
    }

    @PutMapping(value = "/zoo/{id}",consumes = {"application/json"})
    public ResponseEntity<?> replaceExistingZoo(@RequestBody Zoo zoo,@PathVariable long id){
        zoo.setZooid(id);
        return new ResponseEntity<>(zooService.save(zoo),HttpStatus.OK);
    }

    @PatchMapping(value = "/zoo/{id}",consumes = {"application/json"})
    public ResponseEntity<?> updateExistingZoo(@RequestBody Zoo zoo,@PathVariable long id){
        return new ResponseEntity<>(zooService.update(zoo,id),HttpStatus.OK);
    }

    @DeleteMapping(value = "/zoo/{id}")
    public ResponseEntity<?> deleteZoo(@PathVariable long id){
        zooService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //stretch goals

    @GetMapping(value = "/zoo/namelike/{name}", produces = {"application/json"})
    public ResponseEntity<?> getZooByPartOfName(@PathVariable String name){
        List<Zoo> zoos = zooService.findByPartOfName(name);
        return new ResponseEntity<>(zoos, HttpStatus.OK);
    }


    @DeleteMapping(value = "/zoo/{zooid}/animal/{animalid}")
    public ResponseEntity<?> deleteZooAnimal(@PathVariable long zooid,
                                             @PathVariable long animalid){
        zooService.deleteZooAnimal(zooid,animalid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/zoo/{zooid}/animal/{animalid}")
    public ResponseEntity<?> addZooAnimal(@PathVariable long zooid,
                                          @PathVariable long animalid){
        zooService.addZooAnimal(zooid,animalid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
