package com.example.Cookio.controllers.type;

import com.example.Cookio.models.Type;
import com.example.Cookio.services.type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/types")
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
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

    @GetMapping
    public ResponseEntity<List<Type>> getAllTypes() {
        List<Type> types = typeService.getAllTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Type>> getTypeByName(@RequestParam String name) {
        List<Type> types = typeService.getTypeByName(name);
        return ResponseEntity.ok(types);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Type> getTypeById(@PathVariable int id) {
        Optional<Type> type = typeService.getTypeById(id);
        return type.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
