package com.cliqueup.controllers;

import com.cliqueup.entities.ChatMessage;
import com.cliqueup.entities.Group;
import com.cliqueup.entities.User;
import com.cliqueup.services.ChatMessageRepo;
import com.cliqueup.services.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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

    @Autowired
    ChatMessageRepo cms;

//    @MessageMapping("/global")
//    @SendTo("/global")
//    public Message message(Message message, HttpSession session) {
//        String username = (String) session.getAttribute("username");
//        User user = users.findByUsername(username);
//        String text = (String)message.getPayload();
//        ChatMessage chatMessage = new ChatMessage(text, user);
//        cms.save(chatMessage);
//        return message;
//    }

//    public String room(ArrayList<Group> groups) {
//        for (Group group : groups) {
//            String theRoom = group.getName();
//        }
//    }

    @MessageMapping("/global")
    @SendTo("/global")
    public Message message(Message message) {

        if (new String((byte[]) message.getPayload()).length() > 0) {


            LinkedHashMap mapper = new LinkedHashMap();
            JacksonJsonParser parser = new JacksonJsonParser();
            mapper = (LinkedHashMap) parser.parseMap(new String((byte[]) message.getPayload()));
            User user = users.findByUsername((String) mapper.get("username"));
            String text = (String) mapper.get("message");
            ChatMessage chatMessage = new ChatMessage(text, user);
            cms.save(chatMessage);
            this.messenger.convertAndSend("/global", message);
        }

        return message;

    }
}
