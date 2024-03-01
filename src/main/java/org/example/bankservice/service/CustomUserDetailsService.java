//package org.example.bankservice.service;
//
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.val;
//import org.example.bankservice.domain.CustomUserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class CustomUserDetailsService implements UserDetailsService {
//
//    UserService userService;
//
//    @Override
//    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        val user = userService.findByEmail(username);
//        return user.map(CustomUserDetails::fromUserEntityToCustomUserDetails).orElse(null);
//    }
//
//}
