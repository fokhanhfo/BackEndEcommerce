package com.projectRestAPI.studensystem.config;

import com.projectRestAPI.studensystem.enums.Role;
import com.projectRestAPI.studensystem.model.Roles;
import com.projectRestAPI.studensystem.model.Users;
import com.projectRestAPI.studensystem.repository.RolesRepository;
import com.projectRestAPI.studensystem.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UsersRepository usersRepository, RolesRepository rolesRepository){
        return args -> {
            if( usersRepository.findByUsername("admin").isEmpty()){
                List<Roles> roles = rolesRepository.findByNameIn(Arrays.asList("ADMIN", "USER"));
                Users users = Users.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                usersRepository.save(users);
                log.warn("admin user has been creates with default");
            }
        };
    }
}
