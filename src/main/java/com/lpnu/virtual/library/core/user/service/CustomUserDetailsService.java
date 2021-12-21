package com.lpnu.virtual.library.core.user.service;

import com.lpnu.virtual.library.core.user.model.CustomUserDetails;
import com.lpnu.virtual.library.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepo.findByLogin(login)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Not found: " + login));
    }
}
