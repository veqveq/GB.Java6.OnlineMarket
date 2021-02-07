package com.veqveq.onlinemarket.repositories;

import com.veqveq.onlinemarket.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    Optional <User> findByUsername(String username);
}
