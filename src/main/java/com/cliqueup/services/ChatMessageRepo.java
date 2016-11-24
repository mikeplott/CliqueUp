package com.cliqueup.services;

import com.cliqueup.entities.ChatMessage;
import com.cliqueup.entities.Group;
import com.cliqueup.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by michaelplott on 11/16/16.
 */
public interface ChatMessageRepo extends CrudRepository<ChatMessage, Integer> {
    ArrayList<ChatMessage> findAll();
    ArrayList<ChatMessage> findByGroup(Group group);
    ArrayList<ChatMessage> findAllByUser(User user);
}
