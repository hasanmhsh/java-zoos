package com.example.javazoos.repositories;

import com.example.javazoos.model.Zoo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource(exported=false)
public interface ZooRepository extends JpaRepository<Zoo, Long> {
    public List<Zoo> findAllByZoonameContainingIgnoringCase(String zooname, Pageable pageable);
}
