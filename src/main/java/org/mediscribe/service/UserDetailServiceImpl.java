package org.mediscribe.service;

import org.mediscribe.dto.CustomUserDetails;
import org.mediscribe.entity.User;
import org.mediscribe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isEmpty()) {
            throw  new UsernameNotFoundException("User Not Found Exception");
        }
        return new CustomUserDetails(user.get());
    }
}
