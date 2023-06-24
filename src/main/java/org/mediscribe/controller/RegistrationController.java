package org.mediscribe.controller;

import org.mediscribe.dto.ApiResponseMeta;
import org.mediscribe.dto.RegisterUser;
import org.mediscribe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    ResponseEntity<ApiResponseMeta> registerUser(@RequestBody RegisterUser registerUser){
        return  userService.registerUser(registerUser);
    }
}
