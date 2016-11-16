package com.cliqueup.services;

import com.cliqueup.entities.MeetUp;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michaelplott on 11/16/16.
 */
public interface MeetUpRepo extends CrudRepository<MeetUp, Integer> {
    Iterable<MeetUp> findByCategory(MeetUp.Category category);
}
