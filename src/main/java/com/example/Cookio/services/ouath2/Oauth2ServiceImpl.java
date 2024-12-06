package com.example.Cookio.services.ouath2;


import com.example.Cookio.dao.user.UserDAO;
import com.example.Cookio.dto.user.UserDTO;
import com.example.Cookio.dto.user.UserWithRoleDTO;
import com.example.Cookio.models.User;
import com.example.Cookio.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class Oauth2ServiceImpl implements Oauth2Service {

    private final UserDAO userDAO;

    @Autowired

    public Oauth2ServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserWithRoleDTO createOrUpdateUserFromOAuth(Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");
        Boolean emailIsVerified = (Boolean) attributes.get("email_verified");
        String avatar = (String) attributes.get("picture");

        Optional<User> optionalUser = userDAO.findByEmail(email);

        User user;

        if (optionalUser.isEmpty()) {
            // If the user doesn't exist, create a new one
            user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setAvatar(avatar);
            user.setEmailVerified(emailIsVerified != null && emailIsVerified);
            user.setRole(Role.user);
            user.setPassword(UUID.randomUUID().toString());  // Set a temporary password
            userDAO.save(user);
        } else {
            // If the user exists, update their avatar and email verification status
            user = optionalUser.get();
            user.setAvatar(avatar);
            user.setEmailVerified(emailIsVerified != null && emailIsVerified);
            userDAO.save(user);
        }

        UserDTO userDTO = UserDTO.fromUser(user);
        String role = user.getRole().name();  // Get the role as a string

        return new UserWithRoleDTO(userDTO, role);
    }
}
