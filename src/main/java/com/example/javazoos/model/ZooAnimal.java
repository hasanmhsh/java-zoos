package com.example.javazoos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "zooanimals",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"zooid", "animalid"})})
@IdClass(ZooAnimalPrimaryKey.class)
public class ZooAnimal extends Auditable implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "zooid")
    @JsonIgnoreProperties(value = "zooAnimals",
            allowSetters = true)
    private Zoo zoo;

    @Id
    @ManyToOne
    @JoinColumn(name = "animalid")
    @JsonIgnoreProperties(value = "animalZoos",
            allowSetters = true)
    private Animal animal;


    @ManyToOne
    @JoinColumn(name = "incomingzooid",
            referencedColumnName = "ZOOID",
            nullable = true)
    @JsonIgnoreProperties(value = {"outcommingZooAnimals", "zooAnimals"},
            ignoreUnknown = true,
            allowSetters = true)
    @Nullable
    private Zoo incomingzoo;

    public ZooAnimal(){

    }

    public ZooAnimal(Zoo zoo, Animal animal, Zoo incomingzoo) {
        this.zoo = zoo;
        this.animal = animal;
        this.incomingzoo = incomingzoo;
    }

    public Zoo getZoo() {
        return zoo;
    }

    public void setZoo(Zoo zoo) {
        this.zoo = zoo;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Nullable
    public Zoo getIncomingzoo() {
        return incomingzoo;
    }

    public void setIncomingzoo(@Nullable Zoo incomingzoo) {
        this.incomingzoo = incomingzoo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZooAnimal zooAnimal = (ZooAnimal) o;
        return Objects.equals(zoo, zooAnimal.zoo) &&
                Objects.equals(animal, zooAnimal.animal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoo, animal);
    }
}
