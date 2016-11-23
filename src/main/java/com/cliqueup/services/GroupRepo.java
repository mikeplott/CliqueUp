package com.cliqueup.services;

import com.cliqueup.entities.Group;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michaelplott on 11/16/16.
 */
public interface GroupRepo extends CrudRepository<Group, Integer> {
    Group findByName(String name);
}
