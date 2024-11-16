package com.example.Cookio.controllers.oauth2Login;

import com.example.Cookio.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public OAuth2LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/success")
    public Map<String, Object> getUserDetails(@AuthenticationPrincipal OAuth2User oAuth2User) {
        System.out.println(oAuth2User.getAttributes());
        return oAuth2User.getAttributes();
    }


}
