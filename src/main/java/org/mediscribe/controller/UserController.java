package org.mediscribe.controller;

import org.mediscribe.dto.ApiResponse;
import org.mediscribe.entity.User;
import org.mediscribe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/valid")
    ResponseEntity<?> validUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(new ApiResponse<User>(1,"success",userService.getUser(userDetails.getUsername())), HttpStatus.OK);
    }

}
