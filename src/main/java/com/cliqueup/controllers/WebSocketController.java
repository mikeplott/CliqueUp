package com.cliqueup.controllers;

import com.cliqueup.entities.ChatMessage;
import com.cliqueup.entities.Group;
import com.cliqueup.entities.User;
import com.cliqueup.services.ChatMessageRepo;
import com.cliqueup.services.GroupRepo;
import com.cliqueup.services.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.HashMap;

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

    @Autowired
    GroupRepo groups;

    @MessageMapping("/global")
    @SendTo("/global")
    public ArrayList<String> message (Message message) {

        if (new String((byte[]) message.getPayload()).length() > 0) {
            ArrayList<String> myMessage = new ArrayList<>();
            HashMap mapper;
            JacksonJsonParser parser = new JacksonJsonParser();
            String payload = new String((byte[]) message.getPayload());
            mapper = (HashMap) parser.parseMap(payload);
            User user = users.findByUsername((String) mapper.get("username"));
            String text = (String) mapper.get("message");
            Group group = new Group("global", "General Chat Channel");
            groups.save(group);
            ChatMessage chatMessage = new ChatMessage(text, group, user);
            cms.save(chatMessage);
            myMessage.add(text);
            myMessage.add(user.getUsername());
            return myMessage;
        }

        return null;
    }

    @MessageMapping("/chat/{groupName}")
    @SendTo("/chat/{groupName}")
    public ArrayList<String> groupMessage (Message message) {
        ArrayList<String> theMessage = new ArrayList<>();
        HashMap mapper;
        JacksonJsonParser parser = new JacksonJsonParser();
        String payload = new String((byte[]) message.getPayload());
        mapper = (HashMap) parser.parseMap(payload);
        User user = users.findByUsername((String) mapper.get("username"));
        String text = (String) mapper.get("message");
        String groupName = (String) mapper.get("groupName");
        Group group = new Group(groupName, groupName + "chat channel");
        groups.save(group);
        ChatMessage chatMessage = new ChatMessage(text, group, user);
        cms.save(chatMessage);
        theMessage.add(text);
        theMessage.add(user.getUsername());
        return theMessage;
    }

    @MessageMapping("chat-room/{urlName}")
    @SendTo("chat-room/{urlName}")
    public ArrayList<String> chatMessages (Message message) {
        ArrayList<String> chatMessage = new ArrayList<>();
        HashMap mapper;
        JacksonJsonParser parser = new JacksonJsonParser();
        String payload = new String((byte[]) message.getPayload());
        mapper = (HashMap) parser.parseMap(payload);
        User user = users.findByUsername((String) mapper.get("username"));
        String text = (String) mapper.get("message");
        String groupName = (String) mapper.get("urlName");
        Group group = new Group(groupName, groupName + "chat channel");
        groups.save(group);
        ChatMessage myChatMessage = new ChatMessage(text, group, user);
        cms.save(myChatMessage);
        chatMessage.add(text);
        chatMessage.add(user.getUsername());
        return chatMessage;
    }
}
