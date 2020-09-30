package com.hms.javazoos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animals")
public class Animal extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long animalid;

    @NotNull
    @Column(nullable = false,
            unique = false)
    private String animaltype;

    @OneToMany(mappedBy = "animal",
            cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "animal",
            allowSetters = true)
    List<ZooAnimal> zooanimals = new ArrayList<>();

    public Animal() {
    }

    public Animal(long animalid, @NotNull String animaltype) {
        this.animalid = animalid;
        this.animaltype = animaltype;
    }

    public long getAnimalid() {
        return animalid;
    }

    public void setAnimalid(long animalid) {
        this.animalid = animalid;
    }

    public String getAnimaltype() {
        return animaltype;
    }

    public void setAnimaltype(String animaltype) {
        this.animaltype = animaltype;
    }

    public List<ZooAnimal> getZooanimals() {
        return zooanimals;
    }

    public void setZooanimals(List<ZooAnimal> zooanimals) {
        this.zooanimals = zooanimals;
    }
}
