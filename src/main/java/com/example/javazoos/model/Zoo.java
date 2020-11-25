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
@Table(name = "zoos")
public class Zoo extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long zooid;

    @NonNull
    @Column(nullable = false)
    private String zooname;

    @OneToMany(mappedBy = "zoo",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "zoo",
            allowSetters = true)
    private List<ZooAnimal> zooAnimals = new ArrayList<>();



    @OneToMany(mappedBy = "zoo",
            cascade = CascadeType.REFRESH)
    @JsonIgnore
    private List<ZooAnimal> outcommingZooAnimals = new ArrayList<>();




    @OneToMany(mappedBy = "zoo",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "zoo",
            allowSetters = true)
    private List<Telephone> telephones = new ArrayList<>();

    public Zoo(){

    }

    public Zoo(@NonNull String zooname) {
        this.zooname = zooname;
    }

    public long getZooid() {
        return zooid;
    }

    public void setZooid(long zooid) {
        this.zooid = zooid;
    }

    @NonNull
    public String getZooname() {
        return zooname;
    }

    public void setZooname(@NonNull String zooname) {
        this.zooname = zooname;
    }

    public List<ZooAnimal> getZooAnimals() {
        return zooAnimals;
    }

    public void setZooAnimals(List<ZooAnimal> zooAnimals) {
        this.zooAnimals = zooAnimals;
    }

    public List<Telephone> getTelephones() {
        return telephones;
    }

    public void setTelephones(List<Telephone> telephones) {
        this.telephones = telephones;
    }

    public List<ZooAnimal> getOutcommingZooAnimals() {
        return outcommingZooAnimals;
    }

    public void setOutcommingZooAnimals(List<ZooAnimal> outcommingZooAnimals) {
        this.outcommingZooAnimals = outcommingZooAnimals;
    }

    public void addZooAnimal(Animal animal, Zoo incomingzoo) {
        zooAnimals.add(new ZooAnimal(this,
                animal, incomingzoo));
    }
}
