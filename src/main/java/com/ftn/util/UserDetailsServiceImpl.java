package com.ftn.util;

import com.ftn.model.User;
import com.ftn.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final Optional<User> user = userDao.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("user not found");
        }

        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                final Collection<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.get().getRole().name()));
                return authorities;
            }

            @Override
            public String getPassword() {
                return user.get().getPassword();
            }

            @Override
            public String getUsername() {
                return user.get().getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return user.get().isEnabled();
            }

        };

    }
}