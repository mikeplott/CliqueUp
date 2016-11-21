package com.cliqueup.controllers;

import com.cliqueup.entities.ChatMessage;
import com.cliqueup.entities.Group;
import com.cliqueup.entities.User;
import com.cliqueup.services.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by michaelplott on 11/19/16.
 */
@Controller
public class WebSocketController {
    static SimpMessagingTemplate messenger;

    @Autowired
    public WebSocketController(SimpMessagingTemplate template) {
        messenger = template;
    }

    @Autowired
    UserRepo users;

    @MessageMapping("/global")
    @SendTo("/global")
    public Message message(Message message, HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = users.findByUsername(username);
        String text = (String)message.getPayload();
        ChatMessage chatMessage = new ChatMessage(text, user);
        return message;
    }

//    public String room(ArrayList<Group> groups) {
//        for (Group group : groups) {
//            String theRoom = group.getName();
//        }
//    }

}
