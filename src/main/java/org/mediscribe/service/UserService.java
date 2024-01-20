package org.mediscribe.service;

import jakarta.transaction.Transactional;
import org.mediscribe.dto.ApiResponseMeta;
import org.mediscribe.dto.RegisterUser;
import org.mediscribe.entity.Role;
import org.mediscribe.entity.User;
import org.mediscribe.exception.RoleNotFoundException;
import org.mediscribe.exception.UserNotFoundException;
import org.mediscribe.repository.RoleRepository;
import org.mediscribe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUser(String username){
        Optional<User> user = this.userRepository.findUserByUsername(username);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException("User not Found");
    }

    public User getUserById(String id) {
        Optional<User> user = this.userRepository.findUserByUserId(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException("User not Found");
    }


    public ResponseEntity<ApiResponseMeta> registerUser(RegisterUser registerUser) {
        User user = new User();
        Optional<Role> role = roleRepository.findRoleByName(registerUser.getRole());
        if (role.isPresent()) {
            HashSet<Role> roles = new HashSet<>();
            roles.add(role.get());
            user.setUserId(UUID.randomUUID().toString());
            user.setFirstname(registerUser.getFirstname());
            user.setLastname(registerUser.getLastname());
            user.setPassword(bCryptPasswordEncoder.encode(registerUser.getPassword()));
            user.setUsername(registerUser.getUsername());
            user.setEnabled(true);
            user.setTokenExpired(false);
            userRepository.save(user);
            return setUserRole(user.getUserId(), roles);
        }
        throw new RoleNotFoundException("Role Not Found");
    }

    private ResponseEntity<ApiResponseMeta> setUserRole(String userId, HashSet<Role> role) {
        Optional<User> user = userRepository.findUserByUserId(userId);
        if(user.isPresent()) {
            User savedUser = user.get();
            savedUser.setRoles(role);
            userRepository.save(savedUser);
            ApiResponseMeta responseMeta = new ApiResponseMeta(1,"success");
            return new ResponseEntity<>(responseMeta, HttpStatus.OK);
        }
        throw new UserNotFoundException("User Not Found");
    }
}
