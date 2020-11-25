package com.example.javazoos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animals")
public class Animal extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long animalid;

    @NonNull
    @Column(nullable = false)
    private String animaltype;

    @OneToMany(mappedBy = "animal",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(ignoreUnknown = true,
            value = {"animal"},
            allowSetters = true)
    private List<ZooAnimal> animalZoos = new ArrayList<>();



    public Animal(){

    }

    public Animal(long animalid, @NonNull String animaltype) {
        this.animalid = animalid;
        this.animaltype = animaltype;
    }

    public long getAnimalid() {
        return animalid;
    }

    public void setAnimalid(long animalid) {
        this.animalid = animalid;
    }

    @NonNull
    public String getAnimaltype() {
        return animaltype;
    }

    public void setAnimaltype(@NonNull String animaltype) {
        this.animaltype = animaltype;
    }

    public List<ZooAnimal> getAnimalZoos() {
        return animalZoos;
    }

    public void setAnimalZoos(List<ZooAnimal> animalZoos) {
        this.animalZoos = animalZoos;
    }

    public void addAnimalZoo(Zoo zoo, Zoo incomingzoo) {
        animalZoos.add(new ZooAnimal(zoo,
                this, incomingzoo));
    }
}
