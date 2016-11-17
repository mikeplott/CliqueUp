package com.cliqueup.controllers;

import com.cliqueup.entities.*;
import com.cliqueup.services.*;
import com.cliqueup.utlities.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by michaelplott on 11/16/16.
 */
@RestController
public class CliqueUpController {

    @Autowired
    UserRepo users;

    @Autowired
    VenueRepo venues;

    @Autowired
    MeetUpRepo meetups;

    @Autowired
    GroupRepo groups;

    @Autowired
    EventRepo events;

    @Autowired
    DirectMessageRepo dms;

    @Autowired
    ChatMessageRepo cms;

    Server h2;

    @PostConstruct
    public void init() throws SQLException, ParseException, PasswordStorage.CannotPerformOperationException {
        h2.createWebServer().start();

        if (users.count() == 0) {
            users.save(new User("http://statici.behindthevoiceactors.com/behindthevoiceactors/_img/chars/mikey-blumberg-disneys-recess-9.77.jpg",
                    "mike",
                    PasswordStorage.createHash("123")));
            users.save(new User("sam", PasswordStorage.createHash("123")));
            users.save(new User("rob", PasswordStorage.createHash("123")));
            users.save(new User("Henry", PasswordStorage.createHash("123")));
        }

        if (venues.count() == 0) {
            venues.save(new Venue("Closed For Business",
                    "https://limelightcustomsigns.files.wordpress.com/2009/12/l_1600_1200_4c7d886b-f5cf-490b-ae98-8450332c4c71.jpeg",
                    "453 King Street"));
        }

        if (groups.count() == 0) {
            User user = users.findByUsername("Henry");
            groups.save(new Group("Beer-Enthusiasts", "Beer-Enthusiasts Group", user));
        }

        if (meetups.count() == 0) {
            User user = users.findByUsername("Henry");
            Group group = groups.findOne(1);
            meetups.save(new MeetUp("Beer-Enthusiasts", MeetUp.Category.BEER, 0, 0, "Meet up for beer lovers!", user, group));
        }

        if (events.count() == 0) {
            User user = users.findByUsername("Henry");
            MeetUp meetUp = meetups.findOne(1);
            Venue venue = venues.findOne(1);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = dateFormat.parse("11/16/2016");
            long time = date.getTime();
            events.save(new Event("Beer-Fest", new Timestamp(time), "453 King Street", venue, user, meetUp));
        }

        if (dms.count() == 0) {
            User user = users.findByUsername("mike");
            User recipient = users.findByUsername("Henry");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = dateFormat.parse("11/16/2016");
            long time = date.getTime();
            dms.save(new DirectMessage("Hey what time is the event again?", new Timestamp(time), recipient.getId(), user));
        }

        if (cms.count() == 0) {
            User user = users.findByUsername("mike");
            Group group = groups.findOne(1);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = dateFormat.parse("11/16/2016");
            long time = date.getTime();
            cms.save(new ChatMessage("Hey what time is the meetup? I can't get ahold of Henry", new Timestamp(time), group, user));
        }
    }

    @PreDestroy
    public void destroy() {
        h2.stop();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> userAuth(HttpSession session, @RequestBody User user) throws PasswordStorage.InvalidHashException, PasswordStorage.CannotPerformOperationException {
        User userFromDb = users.findByUsername(user.getUsername());
        if (userFromDb == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        else if (!PasswordStorage.verifyPassword(userFromDb.getPassword(), user.getPassword())) {
            return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
        }

        session.setAttribute("username", userFromDb.getUsername());
        return new ResponseEntity<User>(userFromDb, HttpStatus.OK);
        }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public ResponseEntity<User> userSignUp(HttpSession session, @RequestBody User user) throws PasswordStorage.CannotPerformOperationException {
        if (user.getUsername() == null || user.getPassword() == null) {
            return new ResponseEntity<User>(HttpStatus.EXPECTATION_FAILED);
        }
        if (user.getImage() == null) {
            User userForDb = new User(null, user.getUsername(), PasswordStorage.createHash(user.getPassword()));
        }
        User userForDb = new User(user.getImage(), user.getUsername(), PasswordStorage.createHash(user.getPassword()));
        users.save(userForDb);
        session.setAttribute("username", userForDb.getUsername());
        return new ResponseEntity<User>(userForDb, HttpStatus.OK);
    }

    @RequestMapping(path = "/all-direct-messages", method = RequestMethod.GET)
    public ResponseEntity<Iterable<DirectMessage>> getAllDirectMessages(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new ResponseEntity<Iterable<DirectMessage>>(HttpStatus.FORBIDDEN);
        }
        User userFromDb = users.findByUsername(username);
        if (userFromDb == null) {
            return new ResponseEntity<Iterable<DirectMessage>>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<Iterable<DirectMessage>>(dms.findByUser(userFromDb), HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/user-recipient-messages", method = RequestMethod.GET)
    public ResponseEntity<Iterable<DirectMessage>> getSomeDirectMessages(HttpSession session, @RequestBody User recipient) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new ResponseEntity<Iterable<DirectMessage>>(HttpStatus.FORBIDDEN);
        }
        User userFromDb = users.findByUsername(username);
        if (userFromDb == null) {
            return new ResponseEntity<Iterable<DirectMessage>>(HttpStatus.NOT_FOUND);
        }
        else {
            Iterable<DirectMessage> directMessages = dms.findByRecipientIdAndUser(recipient.getId(), userFromDb);
            return new ResponseEntity<Iterable<DirectMessage>>(directMessages, HttpStatus.OK);
        }
    }

    @RequestMapping(path = "direct-messages", method = RequestMethod.POST)
    public ResponseEntity<DirectMessage> postDirectMessage(HttpSession session, @RequestBody DirectMessage dm) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new ResponseEntity<DirectMessage>(HttpStatus.FORBIDDEN);
        }
        User userFromDb = users.findByUsername(username);
        if (userFromDb == null) {
            return new ResponseEntity<DirectMessage>(HttpStatus.NOT_FOUND);
        }
        else {
            dms.save(new DirectMessage(dm.getMessage(), dm.getTime(), dm.getRecipientId(), userFromDb));
            return new ResponseEntity<DirectMessage>(dm, HttpStatus.OK);
        }
    }

    @RequestMapping(path = "chat-messages", method = RequestMethod.GET)
    public ResponseEntity<Iterable<ChatMessage>> getChatMessages(HttpSession session, @RequestBody Group group) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new ResponseEntity<Iterable<ChatMessage>>(HttpStatus.FORBIDDEN);
        }
        User userFromDb = users.findByUsername(username);
        if (userFromDb == null) {
            return new ResponseEntity<Iterable<ChatMessage>>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<Iterable<ChatMessage>>(cms.findByGroup(group), HttpStatus.OK);
        }
    }

    @RequestMapping(path = "chat-messages", method = RequestMethod.POST)
    public ResponseEntity<Iterable<ChatMessage>> postChatMessages(HttpSession session, @RequestBody ChatMessage chatMessage) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new ResponseEntity<Iterable<ChatMessage>>(HttpStatus.FORBIDDEN);
        }
        User userFromDb = users.findByUsername(username);
        if (userFromDb == null) {
            return new ResponseEntity<Iterable<ChatMessage>>(HttpStatus.NOT_FOUND);
        }
        else {
            cms.save(new ChatMessage(chatMessage.getMessage(), chatMessage.getTime(), chatMessage.getGroup(), chatMessage.getUser()));
            return new ResponseEntity<Iterable<ChatMessage>>(cms.findByGroup(chatMessage.getGroup()), HttpStatus.OK);
        }
    }

    @RequestMapping(path = "venue", method = RequestMethod.POST)
    public ResponseEntity<Venue> postVenue(HttpSession session, @RequestBody Venue venue) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new ResponseEntity<Venue>(HttpStatus.FORBIDDEN);
        }
        User user = users.findByUsername(username);
        if (user == null) {
            return new ResponseEntity<Venue>(HttpStatus.NOT_FOUND);
        }
        if (venue.getImage() == null) {
            venues.save(new Venue(venue.getName(), venue.getAddress()));
            return new ResponseEntity<Venue>(venue, HttpStatus.OK);
        }
            venues.save(new Venue(venue.getName(), venue.getImage(), venue.getAddress()));
            return new ResponseEntity<Venue>(venue, HttpStatus.OK);
    }





    public ResponseEntity userValidation(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        User userFromDb = users.findByUsername(username);
        if (userFromDb == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}


