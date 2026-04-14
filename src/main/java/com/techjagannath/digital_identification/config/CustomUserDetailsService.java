package com.techjagannath.digital_identification.config;

import com.techjagannath.digital_identification.entity.UserMaster;
import com.techjagannath.digital_identification.repository.UserMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMasterRepository repo;

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
