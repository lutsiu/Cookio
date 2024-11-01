package com.example.Cookio.services.type;

import com.example.Cookio.models.Type;

import java.util.List;
import java.util.Optional;

public interface TypeService {
    Type createType(Type type);
    List<Type> getAllTypes();
    List<Type> getTypeByName(String type);
    Optional<Type> getTypeById(int id);
    Optional<Type> updateType(int typeId, Type updatedType);
    boolean deleteType(int typeId);
}
