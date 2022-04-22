package com.example.xbulild.data.property;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {

    @Query(value =
            "SELECT DISTINCT p.category FROM Property p", nativeQuery = true
    )
    List<String> findDistinctCategory();

    Set<Property> findAllByCategory(String category);


}
