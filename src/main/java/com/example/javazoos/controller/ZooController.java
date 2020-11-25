package com.example.javazoos.controller;

import com.example.javazoos.model.Zoo;
import com.example.javazoos.model.ZooAnimal;
import com.example.javazoos.service.ZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zoos")
public class ZooController {
    @Autowired
    private ZooService zooService;

    @GetMapping(value = "/zoos", produces = {"application/json"})
    public ResponseEntity<?> getAllZoos() {
        return new ResponseEntity<>(zooService.getAllZoos(), HttpStatus.OK);
    }


    // http://localhost:2019/zoos/paginated?page=1&pageSize=2
    @GetMapping(value = "/paginated", produces = {"application/json"})
    public ResponseEntity<?> getAllZoosPaginated(@RequestParam int page,
                                                 @RequestParam int pageSize) {
        return new ResponseEntity<>(zooService.getAllZoosSortedAndPaginated(page, pageSize), HttpStatus.OK);
    }

    @GetMapping(value = "/zoo/{zooid}", produces = {"application/json"})
    public ResponseEntity<?> getZoo(@PathVariable long zooid) {
        return new ResponseEntity<>(zooService.getZoo(zooid), HttpStatus.OK);
    }

    @PostMapping(value = "/zoo", consumes = {"application/json"})
    public ResponseEntity<?> createNewZoo(@RequestBody Zoo zoo){
        zoo.setZooid(0);
        Zoo newZoo = zooService.save(zoo);
        return new ResponseEntity<>(newZoo, HttpStatus.OK);
    }

    @PutMapping(value = "/zoo/{id}", consumes = {"application/json"})
    public ResponseEntity<?> replaceExistingZoo(@RequestBody Zoo zoo, @PathVariable long id){
        zoo.setZooid(id);
        Zoo newZoo = zooService.save(zoo);
        return new ResponseEntity<>(newZoo, HttpStatus.OK);
    }

    @PatchMapping(value = "/zoo/{id}", consumes = {"application/json"})
    public ResponseEntity<?> updateExistingZoo(@RequestBody Zoo zoo, @PathVariable long id){
        Zoo newZoo = zooService.update(id,zoo);
        return new ResponseEntity<>(newZoo, HttpStatus.OK);
    }

    @DeleteMapping(value = "/zoo/{zooid}", produces = {"application/json"})
    public ResponseEntity<?> deleteZoo(@PathVariable long zooid)  {
        zooService.delete(zooid);

        return new ResponseEntity<>(new Object(){
            public final boolean successful = true;
            public final List< Zoo> zoos = zooService.getAllZoos();
        }, HttpStatus.OK);
    }
}
