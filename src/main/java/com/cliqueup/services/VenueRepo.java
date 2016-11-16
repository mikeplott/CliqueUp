package com.cliqueup.services;

import com.cliqueup.entities.Venue;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michaelplott on 11/16/16.
 */
public interface VenueRepo extends CrudRepository<Venue, Integer> {
}
