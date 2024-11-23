package com.example.Cookio.services.ouath2;

import com.example.Cookio.dto.user.UserDTO;
import com.example.Cookio.dto.user.UserWithRoleDTO;

import java.util.Map;

public interface Oauth2Service {
    public UserWithRoleDTO createOrUpdateUserFromOAuth(Map<String, Object> attributes);
}
