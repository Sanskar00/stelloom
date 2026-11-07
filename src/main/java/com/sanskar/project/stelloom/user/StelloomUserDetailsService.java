package com.sanskar.project.stelloom.user;
import com.sanskar.project.stelloom.user.UserRepository;
import com.sanskar.project.stelloom.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StelloomUserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String slug,String username) {
        User user = userRepository.findByOrganizationSlugAndUsername(slug.toLowerCase(),username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
    }
}
