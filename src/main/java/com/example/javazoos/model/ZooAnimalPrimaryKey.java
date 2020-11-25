package com.example.javazoos.model;

import java.io.Serializable;
import java.util.Objects;

public class ZooAnimalPrimaryKey implements Serializable {
    private long zoo;
    private long animal;


    public ZooAnimalPrimaryKey(){

    }

    public ZooAnimalPrimaryKey(long zooid, long animalid) {
        this.zoo = zooid;
        this.animal = animalid;
    }

    public long getZoo() {
        return zoo;
    }

    public void setZooid(long zooid) {
        this.zoo = zooid;
    }

    public long getAnimal() {
        return animal;
    }

    public void setAnimal(long animalid) {
        this.animal = animalid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZooAnimalPrimaryKey that = (ZooAnimalPrimaryKey) o;
        return zoo == that.zoo &&
                animal == that.animal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zoo, animal);
    }
}
