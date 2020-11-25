package com.example.javazoos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "telephones")
public class Telephone extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long phoneid;

    @NonNull
    @Column(nullable = false)
    private String phonenumber;

    private String phonetype;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "zooid",
            nullable = false)
    @JsonIgnoreProperties(value = "zooAnimals",
            allowSetters = true)
    private Zoo zoo;

    public Telephone(){

    }

    public Telephone(@NonNull String phonenumber, String phonetype, @NotNull Zoo zoo) {
        this.phonenumber = phonenumber;
        this.phonetype = phonetype;
        this.zoo = zoo;
    }

    public long getPhoneid() {
        return phoneid;
    }

    public void setPhoneid(long phoneid) {
        this.phoneid = phoneid;
    }

    @NonNull
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(@NonNull String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonetype() {
        return phonetype;
    }

    public void setPhonetype(String phonetype) {
        this.phonetype = phonetype;
    }

    public Zoo getZoo() {
        return zoo;
    }

    public void setZoo(Zoo zoo) {
        this.zoo = zoo;
    }
}
