package com.cliqueup.services;

import com.cliqueup.entities.Event;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michaelplott on 11/16/16.
 */
public interface EventRepo extends CrudRepository<Event, Integer> {
}
