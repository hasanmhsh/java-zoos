package com.example.javazoos.repositories;

import com.example.javazoos.model.Animal;
import com.example.javazoos.model.Zoo;
import com.example.javazoos.views.AnimalWithCountedZoos;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource(exported=false)
public interface AnimalRepository extends JpaRepository<Animal, Long> {
//Tables contents
/*tables contents
    //--------------------------------------------------------------
    zoos table contents
 zooid | created_by |        created_date        | last_modified_by |     last_modified_date     |         zooname
-------+------------+----------------------------+------------------+----------------------------+--------------------------
     1 | SYSTEM     | 2020-11-23 21:08:40.227098 | SYSTEM           | 2020-11-23 21:08:40.227098 | Gladys Porter Zoo
     2 | SYSTEM     | 2020-11-23 21:08:40.227098 | SYSTEM           | 2020-11-23 21:08:40.227098 | Point Defiance Zoo
     3 | SYSTEM     | 2020-11-23 21:08:40.227098 | SYSTEM           | 2020-11-23 21:08:40.227098 | San Diego Zoo
     4 | SYSTEM     | 2020-11-23 21:08:40.227098 | SYSTEM           | 2020-11-23 21:08:40.227098 | San Antonio Zoo
     5 | SYSTEM     | 2020-11-23 21:08:40.227098 | SYSTEM           | 2020-11-23 21:08:40.227098 | Smithsonian National Zoo


    animals table contents
 animalid | created_by |        created_date        | last_modified_by |     last_modified_date     | animaltype
----------+------------+----------------------------+------------------+----------------------------+------------
        1 | SYSTEM     | 2020-11-23 21:08:40.235693 | SYSTEM           | 2020-11-23 21:08:40.235693 | lion
        2 | SYSTEM     | 2020-11-23 21:08:40.235693 | SYSTEM           | 2020-11-23 21:08:40.235693 | bear
        3 | SYSTEM     | 2020-11-23 21:08:40.235693 | SYSTEM           | 2020-11-23 21:08:40.235693 | monkey
        4 | SYSTEM     | 2020-11-23 21:08:40.235693 | SYSTEM           | 2020-11-23 21:08:40.235693 | penguin
        5 | SYSTEM     | 2020-11-23 21:08:40.235693 | SYSTEM           | 2020-11-23 21:08:40.235693 | tiger
        6 | SYSTEM     | 2020-11-23 21:08:40.235693 | SYSTEM           | 2020-11-23 21:08:40.235693 | bear
        7 | SYSTEM     | 2020-11-23 21:08:40.235693 | SYSTEM           | 2020-11-23 21:08:40.235693 | turtle


    zooanimals table contents
 created_by |        created_date        | last_modified_by |     last_modified_date     | zooid | animalid
------------+----------------------------+------------------+----------------------------+-------+----------
 SYSTEM     | 2020-11-23 21:08:40.239166 | SYSTEM           | 2020-11-23 21:08:40.239166 |     1 |        1
 SYSTEM     | 2020-11-23 21:08:40.239166 | SYSTEM           | 2020-11-23 21:08:40.239166 |     2 |        2
 SYSTEM     | 2020-11-23 21:08:40.239166 | SYSTEM           | 2020-11-23 21:08:40.239166 |     1 |        2
 SYSTEM     | 2020-11-23 21:08:40.239166 | SYSTEM           | 2020-11-23 21:08:40.239166 |     5 |        7
 SYSTEM     | 2020-11-23 21:08:40.239166 | SYSTEM           | 2020-11-23 21:08:40.239166 |     5 |        6
 SYSTEM     | 2020-11-23 21:08:40.239166 | SYSTEM           | 2020-11-23 21:08:40.239166 |     5 |        5
 SYSTEM     | 2020-11-23 21:08:40.239166 | SYSTEM           | 2020-11-23 21:08:40.239166 |     5 |        1
 SYSTEM     | 2020-11-23 21:08:40.239166 | SYSTEM           | 2020-11-23 21:08:40.239166 |     3 |        1
 SYSTEM     | 2020-11-23 21:08:40.239166 | SYSTEM           | 2020-11-23 21:08:40.239166 |     3 |        2


    telephones table content
 phoneid | created_by |        created_date        | last_modified_by |     last_modified_date     | phonenumber  | phonetype  | zooid
---------+------------+----------------------------+------------------+----------------------------+--------------+------------+-------
       1 | SYSTEM     | 2020-11-23 21:08:40.232147 | SYSTEM           | 2020-11-23 21:08:40.232147 | 555-555-5555 | main       |     1
       2 | SYSTEM     | 2020-11-23 21:08:40.232147 | SYSTEM           | 2020-11-23 21:08:40.232147 | 555-555-1234 | education  |     1
       3 | SYSTEM     | 2020-11-23 21:08:40.232147 | SYSTEM           | 2020-11-23 21:08:40.232147 | 555-555-4321 | membership |     1
       4 | SYSTEM     | 2020-11-23 21:08:40.232147 | SYSTEM           | 2020-11-23 21:08:40.232147 | 123-555-5555 | main       |     4
       5 | SYSTEM     | 2020-11-23 21:08:40.232147 | SYSTEM           | 2020-11-23 21:08:40.232147 | 555-123-5555 | main       |     3

*/












    //list listing the animals and a count of how many zoos where they can be found.

    @Query(value =
            "SELECT a.animaltype AS animaltype, " +
                    "a.animalid AS animalid, " +
                    "COUNT(za.animalid) AS countzoos " +
                    "FROM animals a INNER JOIN zooanimals za " +
                    "ON a.animalid = za.animalid " +
                    "GROUP BY a.animalid " +
                    "ORDER BY animaltype ASC"
            ,nativeQuery = true)
    public List<AnimalWithCountedZoos> getAnimalsWithCountedZoos_INNERJOIN_OmitZeroCountedZoosAnimals();


    /* INNER JOIN, RIGHT JOIN RESULTS
 animaltype | animalid | countzoos
------------+----------+-----------
 bear       |        6 |         1
 bear       |        2 |         3
 lion       |        1 |         3
 tiger      |        5 |         1
 turtle     |        7 |         1

     */
    //---------------------------------------------------

    @Query(value =
            "SELECT a.animaltype AS animaltype, " +
                    "a.animalid AS animalid, " +
                    "COUNT(za.animalid) AS countzoos " +
                    "FROM animals a LEFT JOIN zooanimals za " +
                    "ON a.animalid = za.animalid " +
                    "GROUP BY a.animalid " +
                    "ORDER BY animaltype ASC"
            ,nativeQuery = true)
    public List<AnimalWithCountedZoos> getAnimalsWithCountedZoos_LEFTJOIN_WithZeroCountedZoosAnimals();
    /*LEFT JOIN result
 animaltype | animalid | countzoos
------------+----------+-----------
 bear       |        2 |         3
 bear       |        6 |         1
 lion       |        1 |         3
 monkey     |        3 |         0
 penguin    |        4 |         0
 tiger      |        5 |         1
 turtle     |        7 |         1

     */



//    SELECT a.* FROM jz.animals a INNER JOIN jz.zooanimals za ON za.animalid = a.animalid AND za.zooid = 1 GROUP BY a.animalid;
//    public List<Animal> getZooAnimals(long zooid);







    public List<Animal> findAllByAnimaltypeContainingIgnoringCase(String animaltype, Pageable pageable);
}
