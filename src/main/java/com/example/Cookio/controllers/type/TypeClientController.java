package com.example.Cookio.controllers.type;

import com.example.Cookio.models.Type;
import com.example.Cookio.services.type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public class TypeClientController {

    private final TypeService typeService;

    @Autowired
    public TypeClientController(TypeService typeService) {
        this.typeService = typeService;
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
}