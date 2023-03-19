package com.numble.service;

import com.numble.vo.AuthUserVO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserVO userInfo = securityService.getApiUser(username);
        if (userInfo != null) {
            return createUserDetails(userInfo);
        }
        throw (new UsernameNotFoundException(username + "없는 사용자입니다."));
    }

    private UserDetails createUserDetails(AuthUserVO member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("admin");

        return new User(
                String.valueOf(member.getUserName()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
