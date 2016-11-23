package com.cliqueup.services;

import com.cliqueup.entities.Friend;
import com.cliqueup.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by michaelplott on 11/23/16.
 */
public interface FriendRepo extends CrudRepository<Friend, Integer> {
    ArrayList<Friend> findAllByUser(User user);
    Friend findByFriendName(String friendName);
    Friend findByUser(User user);
}
