package com.cellythebackenddeveloper.shopping_cart.Data;
import com.cellythebackenddeveloper.shopping_cart.model.Role;
import com.cellythebackenddeveloper.shopping_cart.model.User;
import com.cellythebackenddeveloper.shopping_cart.repository.RoleRepository;
import com.cellythebackenddeveloper.shopping_cart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
       Set <String> defaultRoles = Set.of("ROLE_ADMIN","ROLE_USER");
        createDefaultRoleIfNotExist(defaultRoles);
        createDefaultUserIfNotExists();
        createDefaultAdminIfNotExists();

    }

    private void createDefaultUserIfNotExists() {
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        for (int i = 1; i <= 5; i++) {
            String email = "user" + i + "@gmail.com";
            if (userRepository.existsByEmail(email)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("123456" + i));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default admin " +  " " + i + " " + "created successfully.");
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i <= 2; i++) {
            String email = "admin" + i + "@gmail.com";
            if (userRepository.existsByEmail(email)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The user");
            user.setLastName("user" + i);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode("123456" + i));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default vet user" +  " " + i + " " + "created successfully.");
        }
    }

    private void createDefaultRoleIfNotExist(Set<String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
