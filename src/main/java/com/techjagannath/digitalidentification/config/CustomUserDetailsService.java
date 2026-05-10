package com.techjagannath.digitalidentification.config;

import com.techjagannath.digitalidentification.entity.PermissionsMaster;
import com.techjagannath.digitalidentification.entity.UserMaster;
import com.techjagannath.digitalidentification.repository.UserMasterRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMasterRepository repo;

    public CustomUserDetailsService(UserMasterRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserMaster user = repo.findByEmailId(username);
        Set<PermissionsMaster> permissions = user.getRole().getPermissions();

        return new org.springframework.security.core.userdetails.User(
                user.getEmailId(),
                user.getPassword(),
                permissions.stream().map(
                        permissionsMaster -> new SimpleGrantedAuthority(permissionsMaster.getPermission())).toList()
        );
    }
}
