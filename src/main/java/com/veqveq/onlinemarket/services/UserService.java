package com.veqveq.onlinemarket.services;

import com.veqveq.onlinemarket.dto.JwtRequest;
import com.veqveq.onlinemarket.exceptions.ResourceNotFoundException;
import com.veqveq.onlinemarket.models.Role;
import com.veqveq.onlinemarket.models.User;
import com.veqveq.onlinemarket.repositories.RoleRepository;
import com.veqveq.onlinemarket.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    public final UsersRepository usersRepository;
    private final RoleRepository roleRepository;


    public Optional<User> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Transactional
    public void save(JwtRequest request) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(2L).orElseThrow(() -> new ResourceNotFoundException(String.format("Role by id: %d not found", 2))));
        usersRepository.save(new User(request.getUsername(),request.getPassword(),roles));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User by username: %s not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapUserRolesToGrantedAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapUserRolesToGrantedAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }
}
