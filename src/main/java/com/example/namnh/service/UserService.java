package com.example.namnh.service;

import com.example.namnh.entity.CustomUserDetails;
import com.example.namnh.entity.User;
import com.example.namnh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername");
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(Integer id) {
        System.out.println("loadUserById");
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(Integer.toString(id));
        }
        return new CustomUserDetails(user.get());
    }
}
