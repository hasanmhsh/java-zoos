package com.hms.javazoos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "telephones")
public class Telephone extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long phoneid;

    @NotNull
    @Column(nullable = false)
    private String phonenumber;

    private String phonetype;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "zooid",
            nullable = false)
    @JsonIgnoreProperties(value = "telephones",
            allowSetters = true)
    private Zoo zoo;

    public Telephone(){

    }

    public Telephone(long phoneid, @NotNull String phonenumber, String phonetype, @NotNull Zoo zoo) {
        this.phoneid = phoneid;
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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
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