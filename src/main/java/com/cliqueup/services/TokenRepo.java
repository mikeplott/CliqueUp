package com.cliqueup.services;

import com.cliqueup.entities.Token;
import com.cliqueup.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michaelplott on 11/17/16.
 */
public interface TokenRepo extends CrudRepository<Token, Integer> {
    Token findByUser(User user);
}
