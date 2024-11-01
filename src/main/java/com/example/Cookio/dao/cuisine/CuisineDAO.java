package com.example.Cookio.dao.cuisine;

import com.example.Cookio.models.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuisineDAO extends JpaRepository<Cuisine, Integer> {

    boolean existsByName(String name);
    @Query("SELECT c FROM Cuisine c WHERE LOWER(c.name) LIKE CONCAT('%', :keyword, '%')")
    List<Cuisine> findByNameIgnoreCase(@Param("keyword") String keyword);
}
