package com.example.Cookio.controllers.oauth2Login;

import com.example.Cookio.dto.user.UserDTO;
import com.example.Cookio.dto.user.UserWithRoleDTO;
import com.example.Cookio.security.JWT;
import com.example.Cookio.services.ouath2.Oauth2Service;
import com.example.Cookio.services.ouath2.Oauth2ServiceImpl;
import com.example.Cookio.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/oauth2")
public class OAuth2LoginController {

    private final UserService userService;
    private final Oauth2Service oauth2Service;

    @Autowired
    public OAuth2LoginController(UserService userService, Oauth2Service oauth2Service) {

        this.userService = userService;
        this.oauth2Service = oauth2Service;
    }

    @GetMapping("/success")
    public ResponseEntity<String> getUserDetails(@AuthenticationPrincipal OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        UserWithRoleDTO retrievedUser = oauth2Service.createOrUpdateUserFromOAuth(attributes);

        UserDTO user = retrievedUser.getUserDTO();

        String token = JWT.generateToken(user.getId(), user.getEmail(), retrievedUser.getRole());

        return ResponseEntity.ok(token);
    }


}
