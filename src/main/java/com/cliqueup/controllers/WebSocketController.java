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

//    @MessageMapping("/global")
//    @SendTo("/global")
//    public ArrayList<String> message (Message message) {
//
//        if (new String((byte[]) message.getPayload()).length() > 0) {
//            ArrayList<String> myMessage = new ArrayList<>();
//            HashMap mapper;
//            JacksonJsonParser parser = new JacksonJsonParser();
//            String payload = new String((byte[]) message.getPayload());
//            mapper = (HashMap) parser.parseMap(payload);
//            User user = users.findByUsername((String) mapper.get("username"));
//            String text = (String) mapper.get("message");
//            Group group = groups.findByName("global");
//            if (group == null) {
//                Group theGroup = new Group("global", "General Chat Channel");
//                groups.save(theGroup);
//                ChatMessage theMessage = new ChatMessage(text, theGroup, user);
//                cms.save(theMessage);
//                myMessage.add(text);
//                myMessage.add(user.getUsername());
//                return myMessage;
//            }
//            ChatMessage chatMessage = new ChatMessage(text, group, user);
//            cms.save(chatMessage);
//            myMessage.add(text);
//            myMessage.add(user.getUsername());
//            return myMessage;
    //          array[0] = "mike"
//        }
//
//        return null;
//    }

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
            Group group = groups.findByName(channel);
            if (group == null) {
                Group theGroup = new Group(channel, channel + " chat channel");
                groups.save(theGroup);
                ChatMessage messageForDb = new ChatMessage(text, theGroup, user);
                cms.save(messageForDb);
               // json.put("channel", channel);
                json.put("message", text);
                json.put("username", user.getUsername());
                //theMessage.add(user.getUsername());
                return json;
            }
            ChatMessage chatMessage = new ChatMessage(text, group, user);
            cms.save(chatMessage);
            //json.put("channel", channel);
            json.put("message", text);
            json.put("username", user.getUsername());
            //theMessage.add(text);
            //theMessage.add(user.getUsername());
            return json;
        }
        return null;
    }

//    @MessageMapping("/chat/{groupname}")
//    @SendTo("/chat/{groupname}")
//    public ArrayList<String> groupMessage (Message message) {
//        ArrayList<String> theMessage = new ArrayList<>();
//        HashMap mapper;
//        JacksonJsonParser parser = new JacksonJsonParser();
//        String payload = new String((byte[]) message.getPayload());
//        mapper = (HashMap) parser.parseMap(payload);
//        User user = users.findByUsername((String) mapper.get("username"));
//        String text = (String) mapper.get("message");
//        String groupName = (String) mapper.get("groupname");
//        Group group = groups.findByName(groupName);
//        if (group == null) {
//            Group theGroup = new Group(groupName, groupName + "chat channel");
//            groups.save(theGroup);
//            ChatMessage messageForDb = new ChatMessage(text, theGroup, user);
//            cms.save(messageForDb);
//            theMessage.add(text);
//            theMessage.add(user.getUsername());
//            return theMessage;
//        }
//        ChatMessage chatMessage = new ChatMessage(text, group, user);
//        cms.save(chatMessage);
//        theMessage.add(text);
//        theMessage.add(user.getUsername());
//        return theMessage;
//    }

//    @MessageMapping("/chat-room/{urlname}")
//    @SendTo("/chat-room/{urlname}")
//    public ArrayList<String> chatMessages (Message message) {
//        ArrayList<String> chatMessage = new ArrayList<>();
//        HashMap mapper;
//        JacksonJsonParser parser = new JacksonJsonParser();
//        String payload = new String((byte[]) message.getPayload());
//        mapper = (HashMap) parser.parseMap(payload);
//        User user = users.findByUsername((String) mapper.get("username"));
//        String text = (String) mapper.get("message");
//        String channel = (String) mapper.get("urlname");
//        Group group = groups.findByName(groupName);
//        if (group == null) {
//            Group theGroup = new Group(groupName, groupName + "chat channel");
//            groups.save(theGroup);
//            ChatMessage messageForDb = new ChatMessage(text, theGroup, user);
//            cms.save(messageForDb);
//            chatMessage.add(text);
//            chatMessage.add(user.getUsername());
//            return chatMessage;
//        }
//        ChatMessage myChatMessage = new ChatMessage(text, group, user);
//        cms.save(myChatMessage);
//        chatMessage.add(text);
//        chatMessage.add(user.getUsername());
//        return chatMessage;
//    }

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
                return json;
            }
            ChatMessage chatMessage = new ChatMessage(text, group, user);
            cms.save(chatMessage);
            //json.put("message", text);
            //json.put("username", user.getUsername());
            json.put("channel", "global");
            return json;
        }

        return null;
    }
}
