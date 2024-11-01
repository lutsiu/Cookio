package com.example.Cookio.dao.type;

import com.example.Cookio.models.Cuisine;
import com.example.Cookio.models.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeDAO extends JpaRepository<Type, Integer> {
    boolean existsByName(String name);
    @Query("SELECT t FROM Type t WHERE LOWER(t.name) LIKE CONCAT('%', :keyword, '%')")
    List<Type> findByNameIgnoreCase(@Param("keyword") String keyword);
}
