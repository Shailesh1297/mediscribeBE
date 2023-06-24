package org.mediscribe.controller;

import org.mediscribe.dto.ApiResponse;
import org.mediscribe.dto.ApiResponseMeta;
import org.mediscribe.dto.JwtRequest;
import org.mediscribe.dto.JwtResponse;
import org.mediscribe.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/test")
    ResponseEntity<?> test(){
        return new ResponseEntity<String>("tested ok",HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    ResponseEntity<?> authenticate(@RequestBody JwtRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return new ResponseEntity<ApiResponse<JwtResponse>>(new ApiResponse<JwtResponse>(1,"success",new JwtResponse(token)),HttpStatus.OK);
    }

    @GetMapping("/restrict")
    ResponseEntity<?> restrict() {
        return new ResponseEntity<ApiResponseMeta>(new ApiResponseMeta(1,"success"),HttpStatus.OK);
    }


}
