//package com.example.Spring_2.Service_Client;
//
//import com.example.Spring_2.Repository_user.ClientRepo;
//import com.example.Spring_2.Repository_user.ClientRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.User.UserBuilder;
//import org.springframework.security.core.userdetails.User;
//
//import org.springframework.stereotype.Service;
//
//@Service
//public class DetailService {
//
//    @Autowired
//    private ClientRepo clientRepo;
//
//    public UserDetails authenticate_2(String email) throws UsernameNotFoundException {
//        User user = clientRepo.findByEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//        // Convert User entity to UserDetails
//        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(email);
//        builder.password(user.getPassword());
//
//        return builder.build();
//    }
//}
