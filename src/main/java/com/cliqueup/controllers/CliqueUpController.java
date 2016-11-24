package com.cliqueup.controllers;

import com.cliqueup.entities.*;
import com.cliqueup.services.*;
import com.cliqueup.utlities.PasswordStorage;
import okhttp3.*;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by michaelplott on 11/16/16.
 */
@RestController
public class CliqueUpController {

    public static final String REDIRECTURL = "http://10.1.10.44:8080/access";

    //public static final String REDIRECTURL = "http://127.0.0.1:8080/access";

    public static final String AUTHORIZE_URL = "https://secure.meetup.com/oauth2/authorize";

    public static final String CLIENT_ID = "dlevog3jrgb2rn4rr30hv6rs5b";

    public static final String SECRET = "g3dd8kblqshkq7jrplj79et6ko";

    public static final String AUTH = AUTHORIZE_URL+"?client_id="+CLIENT_ID+"&response_type=code&redirect_uri="+REDIRECTURL;

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

    @Autowired
    TokenRepo tokens;

    @Autowired
    FriendRepo friends;

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
            dms.save(new DirectMessage("Hey what time is the event again?", recipient.getUsername(), user));
        }

        if (cms.count() == 0) {
            User user = users.findByUsername("mike");
            Group group = groups.findOne(1);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = dateFormat.parse("11/16/2016");
            long time = date.getTime();
            cms.save(new ChatMessage("Hey what time is the meetup? I can't get ahold of Henry", group, user));
        }
    }

    @PreDestroy
    public void destroy() {
        h2.stop();
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User userAuth(HttpSession session, @RequestBody User user) throws Exception {
        User userFromDb = users.findByUsername(user.getUsername());
        if (userFromDb == null) {
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            User userForDb = new User(user.getUsername(), true, user.getPassword());
            users.save(userForDb);
            session.setAttribute("username", userForDb.getUsername());
            return user;
        }
        else if (!PasswordStorage.verifyPassword(user.getPassword(), userFromDb.getPassword())) {
            throw new Exception("Password invalid");
        }
        session.setAttribute("username", user.getUsername());
        userFromDb.setOnline(!userFromDb.isOnline());
        users.save(userFromDb);
        return userFromDb;
    }

    @RequestMapping(path = "/auth", method = RequestMethod.GET)
    public void getAuth(HttpSession session,HttpServletResponse response) throws IOException, ServletException {
        String username = (String) session.getAttribute("username");
        String url = AUTH + "?username=" + username;
        response.sendRedirect(url);
    }

    @RequestMapping(path = "/access", method = RequestMethod.GET)
    public void getAccess(String code, String username , HttpServletResponse myResponse, HttpSession session) throws IOException {
        User user = users.findByUsername(username);
        OkHttpClient client = new OkHttpClient();
        okhttp3.RequestBody formBody = new FormBody.Builder()
                .add("client_id", CLIENT_ID)
                .add("client_secret", SECRET)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", REDIRECTURL + "?username=" + username)
                .add("code", code)
                .build();
        Request myRequest = new Request.Builder()
                .url("https://secure.meetup.com/oauth2/access")
                .post(formBody)
                .build();
        okhttp3.Response response = client.newCall(myRequest).execute();
        Token token = new Token(response.body().string());
        tokens.save(token);
        session.setAttribute("token", token.getId());
        user.setToken(token);
        users.save(user);
        session.setAttribute("username", user.getUsername());
        if (!response.isSuccessful())
            throw new IOException("CliqueUp server error: " + response);
        myResponse.sendRedirect("/#/homePage");
    }

    @RequestMapping(path = "/gettoken", method = RequestMethod.GET)
    public Token getToken(HttpSession session) throws Exception {
        String username = (String) session.getAttribute("username");
        int id = (int) session.getAttribute("token");
        if (username == null) {
            throw new Exception("Not logged in");
        }
        return tokens.findOne(id);
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = users.findByUsername(username);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(HttpSession session) throws Exception {
        String username = (String) session.getAttribute("username");
        int id = (int) session.getAttribute("token");
        if (username == null) {
            throw new Exception("Not logged in!");
        }
        User userFromDb = users.findByUsername(username);
        if (userFromDb == null) {
            throw new Exception("User does not exist!");
        }
        userFromDb.setToken(null);
        tokens.delete(id);
        userFromDb.setOnline(false);
        users.save(userFromDb);
        session.invalidate();
    }

    @RequestMapping(path = "/friends", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<String>> getFriends(HttpSession session) throws Exception {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new ResponseEntity<ArrayList<String>>(HttpStatus.FORBIDDEN);
        }
        User user = users.findByUsername(username);
        ArrayList<String> friendNames = new ArrayList<>();
        ArrayList<Friend> userFriends = friends.findAllByUser(user);
        for (Friend friend : userFriends) {
            friendNames.add(friend.getFriendName());
        }
        return new ResponseEntity<ArrayList<String>>(friendNames, HttpStatus.OK);
    }

    @RequestMapping(path = "/friends", method = RequestMethod.POST)
    public ResponseEntity<ArrayList<String>> addFriends(HttpSession session, @RequestBody Map<String, String> json) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new ResponseEntity<ArrayList<String>>(HttpStatus.FORBIDDEN);
        }
        User user = users.findByUsername(username);
        User friendOfUser = users.findByUsername(json.get("friendName"));
        ArrayList<String> friendNames = new ArrayList<>();
        ArrayList<Friend> userFriends = friends.findAllByUser(user);
        for (Friend friend : userFriends) {
            friendNames.add(friend.getFriendName());
        }
        Friend friendFromDb = friends.findByUser(user);
        if (friendFromDb == null) {
            Friend friend = new Friend(json.get("friendName"), user);
            friends.save(friend);
        }
        Friend otherFriendFromDb = friends.findByUser(friendOfUser);
        if (otherFriendFromDb == null) {
            Friend otherFriend = new Friend(user.getUsername(), friendOfUser);
            friends.save(otherFriend);
        }
        friendNames.add(json.get("friendName"));
        return new ResponseEntity<ArrayList<String>>(friendNames, HttpStatus.OK);
    }

    @RequestMapping(path = "/image", method = RequestMethod.POST)
    public String saveImage(HttpSession session, @RequestBody Map<String, String> json) {
        String username = (String) session.getAttribute("username");
        User user = users.findByUsername(username);
        user.setImage(json.get("photo"));
        users.save(user);
        return json.get("photo");
    }

    @RequestMapping(path = "/chat", method = RequestMethod.GET)
    public ArrayList<String> getMessages(HttpSession session) {
        String username = (String) session.getAttribute("username");
        User user = users.findByUsername(username);
        ArrayList<String> theMessages = new ArrayList<>();
        ArrayList<ChatMessage> allMessages = cms.findAll();
        if (cms.count() > 10) {
            for (int i = 0; i > cms.count() - 10; i++) {
                theMessages.add(allMessages.get(i).getMessage());
            }
            return theMessages;
        }
        else {
            for (ChatMessage chatMessage : allMessages) {
                theMessages.add(chatMessage.getMessage());
            }
            return theMessages;
        }
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
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<Iterable<DirectMessage>>(HttpStatus.FORBIDDEN);
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            return new ResponseEntity<Iterable<DirectMessage>>(HttpStatus.NOT_FOUND);
//        }
//        else {
        User userFromDb = users.findByUsername("mike");
        return new ResponseEntity<Iterable<DirectMessage>>(dms.findByUser(userFromDb), HttpStatus.OK);
    }
    //}

    @RequestMapping(path = "/user-recipient-messages", method = RequestMethod.GET)
    public ResponseEntity<Iterable<DirectMessage>> getSomeDirectMessages(HttpSession session, @RequestBody User recipient) {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<Iterable<DirectMessage>>(HttpStatus.FORBIDDEN);
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            return new ResponseEntity<Iterable<DirectMessage>>(HttpStatus.NOT_FOUND);
//        }
//        else {
        User userFromDb = users.findByUsername("mike");
        //Iterable<DirectMessage> directMessages = dms.findByRecipientIdAndUser(recipient.getId(), userFromDb);
        Iterable<DirectMessage> directMessages = dms.findByRecipientNameAndUser(recipient.getUsername(), userFromDb);
        return new ResponseEntity<Iterable<DirectMessage>>(directMessages, HttpStatus.OK);
        //}
    }

    @RequestMapping(path = "direct-messages", method = RequestMethod.POST)
    public ResponseEntity<DirectMessage> postDirectMessage(HttpSession session, @RequestBody DirectMessage dm) {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<DirectMessage>(HttpStatus.FORBIDDEN);
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            return new ResponseEntity<DirectMessage>(HttpStatus.NOT_FOUND);
//        }
//        else {
//            dms.save(new DirectMessage(dm.getMessage(), dm.getTime(), dm.getRecipientId(), userFromDb));
        User user = users.findByUsername("mike");
        dms.save(new DirectMessage(dm.getMessage(), dm.getRecipientName(), user));
        return new ResponseEntity<DirectMessage>(dm, HttpStatus.OK);
//        }
    }

    @RequestMapping(path = "chat-messages", method = RequestMethod.GET)
    public ResponseEntity<Iterable<ChatMessage>> getChatMessages(HttpSession session, @RequestBody Group group) {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<Iterable<ChatMessage>>(HttpStatus.FORBIDDEN);
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            return new ResponseEntity<Iterable<ChatMessage>>(HttpStatus.NOT_FOUND);
//        }
//        else {
        return new ResponseEntity<Iterable<ChatMessage>>(cms.findByGroup(group), HttpStatus.OK);
//        }
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
            cms.save(new ChatMessage(chatMessage.getMessage(), chatMessage.getGroup(), chatMessage.getUser()));
            return new ResponseEntity<Iterable<ChatMessage>>(cms.findByGroup(chatMessage.getGroup()), HttpStatus.OK);
        }
    }

    @RequestMapping(path = "venues", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Venue>> getVenues(HttpSession session) {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<Iterable<Venue>>(HttpStatus.FORBIDDEN);
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            return new ResponseEntity<Iterable<Venue>>(HttpStatus.NOT_FOUND);
//        }
//        else {
        return new ResponseEntity<Iterable<Venue>>(venues.findAll(), HttpStatus.OK);
//        }
    }

    @RequestMapping(path = "venue", method = RequestMethod.GET)
    public ResponseEntity<Venue> getVenue(HttpSession session, @RequestBody Venue venue) {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<Venue>(HttpStatus.FORBIDDEN);
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            return new ResponseEntity<Venue>(HttpStatus.NOT_FOUND);
//        }
//        else {
        return new ResponseEntity<Venue>(venues.findOne(venue.getId()), HttpStatus.OK);
//        }
    }

    @RequestMapping(path = "venue", method = RequestMethod.POST)
    public ResponseEntity<Venue> postVenue(HttpSession session, @RequestBody Venue venue) {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<Venue>(HttpStatus.FORBIDDEN);
//        }
//        User user = users.findByUsername(username);
//        if (user == null) {
//            return new ResponseEntity<Venue>(HttpStatus.NOT_FOUND);
//        }
        if (venue.getImage() == null) {
            venues.save(new Venue(venue.getName(), venue.getAddress()));
            return new ResponseEntity<Venue>(venue, HttpStatus.OK);
        }
        venues.save(new Venue(venue.getName(), venue.getImage(), venue.getAddress()));
        return new ResponseEntity<Venue>(venue, HttpStatus.OK);
    }

    public boolean userValidation(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return false;
        }
        User userFromDb = users.findByUsername(username);
        if (userFromDb == null) {
            return false;
        }
        return true;
    }
}