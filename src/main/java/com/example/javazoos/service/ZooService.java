package com.example.javazoos.service;

import com.example.javazoos.model.Zoo;

import java.util.List;

public interface ZooService {
    public List<Zoo> getAllZoos();
    public List<Zoo> getAllZoosSortedAndPaginated(int page, int pageSize);
    public Zoo getZoo(long zooid);

    public Zoo save(Zoo zoo);
    public Zoo update(long zooid, Zoo zoo);
    public void delete(long zooid);
}
