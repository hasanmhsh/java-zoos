package com.hms.javazoos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "zoos")
public class Zoo extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long zooid;

    @NotNull
    @Column(nullable = false,
            unique = true)
    private String zooname;

    @OneToMany(mappedBy = "zoo",
              cascade = CascadeType.ALL,
              orphanRemoval = true)
    @JsonIgnoreProperties(value = "zoo",
            allowSetters = true)
    private List<Telephone> telephones = new ArrayList<>();

    @OneToMany(mappedBy = "zoo",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "zoo",
            allowSetters = true)
    private List<ZooAnimal> animals = new ArrayList<>();

    public Zoo(){
    }

    public Zoo(long zooid, String zooname) {
        this.zooid = zooid;
        this.zooname = zooname;
    }

    public long getZooid() {
        return zooid;
    }

    public void setZooid(long zooid) {
        this.zooid = zooid;
    }

    public String getZooname() {
        return zooname;
    }

    public void setZooname(String zooname) {
        this.zooname = zooname;
    }

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<Telephone> zoophones) {
        this.telephones = zoophones;
    }

    public List<ZooAnimal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<ZooAnimal> zooanimals) {
        this.animals = zooanimals;
    }

    public void addAnimal(Animal animal) {
        animals.add(new ZooAnimal(this,
                animal));
    }
}
