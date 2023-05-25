package com.benyovszki.user.service;

import com.benyovszki.user.exception.InvalidUsernameException;
import com.benyovszki.user.repository.UserRepository;
import com.benyovszki.user.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new SecurityUser(userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new));
    }
}
