package com.example.javazoos.repositories;

import com.example.javazoos.model.Telephone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource(exported=false)
public interface TelephoneRepository extends JpaRepository<Telephone, Long> {

}
