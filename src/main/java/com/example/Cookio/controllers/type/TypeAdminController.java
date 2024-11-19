package com.example.Cookio.controllers.type;

import com.example.Cookio.models.Type;
import com.example.Cookio.services.type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin/types")
@PreAuthorize("hasRole('ADMIN')")
public class TypeAdminController {
    private final TypeService typeService;

    @Autowired
    public TypeAdminController(TypeService typeService) {
        this.typeService = typeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Type> createType(@RequestBody Type type) {
        Type createdType = typeService.createType(type);
        if (createdType == null) {
            // Return conflict status if the type already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Type> updateType(@PathVariable int id, @RequestBody Type type) {
        Optional<Type> updatedType = typeService.updateType(id, type);
        return updatedType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable int id) {
        boolean deleted = typeService.deleteType(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

}
