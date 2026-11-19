package ru.ksenia.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.security.CustomUserDetails;
import ru.ksenia.NauJava.service.interfaces.UserService;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public UserDetailServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String loginInput) throws UsernameNotFoundException {
        User user = userService.getUserByUsernameOrEmail(loginInput, loginInput);
        return new CustomUserDetails(user);

    }
}
