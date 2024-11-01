package com.example.Cookio.services.type;

import com.example.Cookio.dao.type.TypeDAO;
import com.example.Cookio.exceptions.type.TypeAlreadyExistsException;
import com.example.Cookio.exceptions.type.TypeNotFoundException;
import com.example.Cookio.models.Type;
import com.example.Cookio.models.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImpl implements TypeService {
    
    private final TypeDAO dao;

    @Autowired
    public TypeServiceImpl(TypeDAO dao) {
        this.dao = dao;
    }

    @Override
    public Type createType(Type type) {
        String cuisineName = type.getName();
        if (dao.existsByName(cuisineName)) {
            throw new TypeAlreadyExistsException("Type with name " + cuisineName + " already exists");
        }
        return dao.save(type);
    }

    @Override
    public List<Type> getAllTypes() {
        return dao.findAll();
    }

    @Override
    public List<Type> getTypeByName(String typeName) {
        List<Type> foundCuisines = dao.findByNameIgnoreCase(typeName);
        if (foundCuisines.isEmpty()) {
            throw new TypeNotFoundException("No type found with name: " + typeName);
        }
        return foundCuisines;
    }

    @Override
    public Optional<Type> getTypeById(int id) {
        return Optional.ofNullable(dao.findById(id)
                .orElseThrow(() -> new TypeNotFoundException("Type with id " + id + " is not found")));
    }

    @Override
    public Optional<Type> updateType(int typeId, Type updatedType) {
        return Optional.ofNullable(dao.findById(typeId).map(existingCuisine -> {
            existingCuisine.setName(updatedType.getName());
            existingCuisine.setDescription(updatedType.getDescription());
            return dao.save(existingCuisine);
        }).orElseThrow(() ->
                new TypeNotFoundException("Type with id " + typeId + " is not found")));
    }

    @Override
    public boolean deleteType(int typeId) {
        if (dao.existsById(typeId)) {
            dao.deleteById(typeId);
            return true;
        }
        throw new TypeNotFoundException("Type with id " + typeId + " is not found");
    }
}
