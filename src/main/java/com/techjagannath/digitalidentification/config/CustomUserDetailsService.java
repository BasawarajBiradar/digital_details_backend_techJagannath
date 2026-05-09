package com.techjagannath.digitalidentification.config;

import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.UserMasterRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMasterRepository repo;

    public CustomUserDetailsService(UserMasterRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserMaster user = repo.findByEmailId(username);

        return new org.springframework.security.core.userdetails.User(
                user.getEmailId(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
