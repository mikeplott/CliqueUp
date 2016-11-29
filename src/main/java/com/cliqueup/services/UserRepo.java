package com.cliqueup.services;

import com.cliqueup.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by michaelplott on 11/16/16.
 */
public interface UserRepo extends CrudRepository<User, Integer> {
    User findByUsername (String username);
    ArrayList<User> findAll();
}
