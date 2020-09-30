package com.hms.javazoos.services;

import com.hms.javazoos.models.Animal;
import com.hms.javazoos.views.AnimalsCountable;

import java.util.List;

public interface AnimalService {
    public Animal findAnimalById(long id);
    public List<AnimalsCountable> getAnimalsZoosCount();
}
