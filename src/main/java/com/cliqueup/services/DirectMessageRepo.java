package com.cliqueup.services;

import com.cliqueup.entities.DirectMessage;
import com.cliqueup.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by michaelplott on 11/16/16.
 */
public interface DirectMessageRepo extends CrudRepository<DirectMessage, Integer> {
    Iterable<DirectMessage> findByUser(User user);
    Iterable<DirectMessage> findByRecipientIdAndUser(int recipientId, User user);
}
