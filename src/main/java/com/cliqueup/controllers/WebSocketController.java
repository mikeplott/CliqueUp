package com.cliqueup.controllers;

import com.cliqueup.entities.ChatMessage;
import com.cliqueup.entities.DirectMessage;
import com.cliqueup.entities.Group;
import com.cliqueup.entities.User;
import com.cliqueup.services.ChatMessageRepo;
import com.cliqueup.services.DirectMessageRepo;
import com.cliqueup.services.GroupRepo;
import com.cliqueup.services.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
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

    @Autowired
    DirectMessageRepo dms;

    @MessageMapping("/chat-room/{channel}")
    @SendTo("/chat-room/{channel}")
    public HashMap<String, String> groupMessage (Message message) {
        if (new String((byte[]) message.getPayload()).length() > 0) {
            HashMap<String, String> json = new HashMap<>();
            HashMap mapper;
            JacksonJsonParser parser = new JacksonJsonParser();
            String payload = new String((byte[]) message.getPayload());
            mapper = (HashMap) parser.parseMap(payload);
            User user = users.findByUsername((String) mapper.get("username"));
            String text = (String) mapper.get("message");
            String channel = (String) mapper.get("channel");
            String image = (String) mapper.get("image");
            Group group = groups.findByName(channel);
            if (group == null) {
                Group theGroup = new Group(channel, channel + " chat channel");
                groups.save(theGroup);
                ChatMessage messageForDb = new ChatMessage(text, theGroup, user);
                cms.save(messageForDb);
                json.put("message", text);
                json.put("username", user.getUsername());
                json.put("image", user.getImage());
                return json;
            }
            ChatMessage chatMessage = new ChatMessage(text, group, user);
            cms.save(chatMessage);
            json.put("message", text);
            json.put("username", user.getUsername());
            json.put("image", user.getImage());
            return json;
        }
        return null;
    }

    @MessageMapping("direct-message/{recipientname}")
    @SendTo("direct-message/{recipientname}")
    public HashMap<String, String> directMessages (Message message) {
        HashMap<String, String> directMessage = new HashMap<>();
        HashMap mapper;
        JacksonJsonParser parser = new JacksonJsonParser();
        String payload = new String((byte[]) message.getPayload());
        mapper = (HashMap) parser.parseMap(payload);
        User user = users.findByUsername("username");
        String text = (String) mapper.get("message");
        String recipientName = (String) mapper.get("recipientname");
        DirectMessage directMessageForDb = new DirectMessage(text, recipientName, user);
        dms.save(directMessageForDb);
        directMessage.put("username", user.getUsername());
        directMessage.put("message", text);
        directMessage.put("recipientname", recipientName);
        return directMessage;
    }

    @MessageMapping("/global")
    @SendTo("/global")
    public HashMap<String, String> message (Message message) {
        if (new String((byte[]) message.getPayload()).length() > 0) {
            HashMap<String, String> json = new HashMap<>();
            HashMap mapper;
            JacksonJsonParser parser = new JacksonJsonParser();
            String payload = new String((byte[]) message.getPayload());
            mapper = (HashMap) parser.parseMap(payload);
            String image = (String) mapper.get("image");
            User user = users.findByUsername((String) mapper.get("username"));
            String text = (String) mapper.get("message");
            Group group = groups.findByName("global");
            if (group == null) {
                Group theGroup = new Group("global", "General Chat Channel");
                groups.save(theGroup);
                ChatMessage theMessage = new ChatMessage(text, theGroup, user);
                cms.save(theMessage);
                json.put("message", text);
                json.put("username", user.getUsername());
                json.put("image", user.getImage());
                return json;
            }
            ChatMessage chatMessage = new ChatMessage(text, group, user);
            cms.save(chatMessage);
            json.put("channel", "global");
            return json;
        }
        return null;
    }
}
