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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

    public static final String REDIRECTURL = "http://127.0.0.1:8080/";

    public static final String AUTH = "https://secure.meetup.com/oauth2/authorize?client_id=dlevog3jrgb2rn4rr30hv6rs5b&response_type=code&redirect_uri=" + REDIRECTURL + "access";

    public static final String SECRET = "g3dd8kblqshkq7jrplj79et6ko";
    public static String urlCode;

    public static final String ACCESS = "https://secure.meetup.com/oauth2/access?client_id=dlevog3jrgb2rn4rr30hv6rs5b&client_secret=" + SECRET +
            "&grant_type=authorization_code&redirect_uri=" + REDIRECTURL + "about-page.html#homePage" + "&code=" + urlCode;

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
    ResponseRepo responses;

    Server h2;
    private Object access_token;

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
    public User userAuth(HttpSession session, HttpServletResponse response, @RequestBody User user) throws Exception {
        User userFromDb = users.findByUsername(user.getUsername());
        if (userFromDb == null) {
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(userFromDb.getPassword(), user.getPassword())) {
            throw new Exception("Password invalid");
        }
        session.setAttribute("username", user.getUsername());
        return user;
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
            Iterable<DirectMessage> directMessages = dms.findByRecipientIdAndUser(1, userFromDb);
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
            dms.save(new DirectMessage(dm.getMessage(), dm.getTime(), dm.getRecipientId(), user));
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
            cms.save(new ChatMessage(chatMessage.getMessage(), chatMessage.getTime(), chatMessage.getGroup(), chatMessage.getUser()));
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

    @RequestMapping(path = "/redirect", method = RequestMethod.GET)
    public ResponseEntity<String> getRedirecturl(HttpSession session) {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
//        }
//        else {
            return new ResponseEntity<String>(REDIRECTURL, HttpStatus.OK);
//        }
    }

//    @RequestMapping(path = "/token", method = RequestMethod.POST)
//    public ResponseEntity<Token> postToken(HttpSession session, @RequestBody Token token) {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<Token>(HttpStatus.FORBIDDEN);
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            return new ResponseEntity<Token>(HttpStatus.NOT_FOUND);
//        }
//        else {
//            //tokens.save(new Token(token.getKey(), token.getSecret(), REDIRECTURL, userFromDb));
//            Token tokenFromDb = tokens.findByUser(userFromDb);
//            return new ResponseEntity<Token>(tokenFromDb, HttpStatus.OK);
//        }
//    }

//    @RequestMapping(path = "/token", method = RequestMethod.GET)
//    public ResponseEntity<Token> getToken(HttpSession session, @RequestBody User user) {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            return new ResponseEntity<Token>(HttpStatus.FORBIDDEN);
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            return new ResponseEntity<Token>(HttpStatus.NOT_FOUND);
//        }
//        else {
////            Token tokenFromDb = tokens.findByUser(userFromDb);
//            return new ResponseEntity<Token>(tokenFromDb, HttpStatus.OK);
      //  }
    //}

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(HttpSession session) throws Exception {
//        String username = (String) session.getAttribute("username");
//        if (username == null) {
//            throw new Exception("Not logged in!");
//        }
//        User userFromDb = users.findByUsername(username);
//        if (userFromDb == null) {
//            throw new Exception("User does not exist!");
//        }
//        Token tokenToDelete = tokens.findByUser(userFromDb);
//        tokens.delete(tokenToDelete.getId());
        session.invalidate();
    }

//    @RequestMapping(path = "/auth", method = RequestMethod.GET)
//    public String getAuth(HttpServletResponse response) throws IOException {
//        return AUTH;
//    }
//
    @RequestMapping(path = "/access", method = RequestMethod.GET)
    public void getAccess(String code, HttpServletResponse myResponse, HttpSession session) throws IOException {
        OkHttpClient client = new OkHttpClient();

        okhttp3.RequestBody formBody = new FormBody.Builder()
                .add("client_id", "dlevog3jrgb2rn4rr30hv6rs5b")
                .add("client_secret", SECRET)
                .add("grant_type", "authorization_code")
                .add("redirect_uri", REDIRECTURL + "access")
                .add("code", code)
                .build();

        Request request = new Request.Builder()
                .url("https://secure.meetup.com/oauth2/access")
                .post(formBody)
                .build();

        okhttp3.Response response = client.newCall(request).execute();

        session.setAttribute("token", response.body().string());

        System.out.println(session.getAttribute("token").toString());

        access_token = session.getAttribute("token");
        Token token = new Token();
        String username = (String) session.getAttribute("username");

        User user = users.findByUsername(username);
        String accessToken = (String) session.getAttribute("token");
        token.setAccess_token(accessToken);
        user.setToken(token);
        tokens.save(token);
        users.save(user);
        System.out.println(access_token);

        if (!response.isSuccessful())
            throw new IOException("GO FUCK YOURSELF" + response);

        myResponse.sendRedirect("/gettoken");
    }

    @RequestMapping(path = "/gettoken", method = RequestMethod.GET)
    public User getToken(HttpSession session, HttpServletResponse response) throws IOException {
        String username = (String) session.getAttribute("username");
        User user = users.findByUsername(username);
        response.sendRedirect("/#/homePage");
        return user;
    }

       // System.out.println(response.body());

        //System.out.println(access_token);

       // tokens.save(new Token(response.header("access_token"), response.header("refresh_token"), response.header("token_type"), 3600, user));



//        session.setAttribute("token", response.body());
//        System.out.println(session.getAttribute("token").toString());


//        String token = (String) session.getAttribute("token");
//        System.out.println(token);
// System.out.println(response.body().string());
//        Request request = new Request.Builder()
//                .url(url)
//                .post()
//                .build();
//
//        Response response = client.newCall(request).execute();

//        String urlCode = code;
//        response.sendRedirect(ACCESS);

    @RequestMapping(path = "/auth", method = RequestMethod.GET)
    public void getAuth(HttpSession session,HttpServletResponse response) throws IOException {
        //return AUTH;
       // users.save(user);
        response.sendRedirect(AUTH);
    }

//    @RequestMapping(path = "/access", method = RequestMethod.POST)
//    public void getAccess(HttpServletRequest request) throws IOException {
        //code = json.get("code");
        //return ACCESS;
        //response.sendRedirect(ACCESS);

    //}

//    @RequestMapping(path = "/access", method = RequestMethod.POST)
//    public void getAccess(HttpServletResponse response) throws IOException {
//        response.sendRedirect(REDIRECTURL + "access");
//    }

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

// trying to split the access token that meet up provides to get the value of each field but failed.

//String[] json = access_token.toString().split(",");

//        for (int i = 0; i < json.length; i++) {
//            //json[i].split(":");
//            String stuff = json[i];
//            System.out.println(stuff);
//            String[] text = stuff.split(":");
//            String theText = text[0];
//            System.out.println(theText);
//            if (i == 0) {
//                token.setKey1(json[0]);
//            }
//            if (i == 1) {
//                token.setAccess_token(json[1]);
//            }
//            if (i == 2) {
//                token.setKey2(json[2]);
//            }
//            if (i == 3) {
//                token.setToken_type(json[3]);
//            }
//            if (i == 4) {
//                token.setKey3(json[4]);
//            }
//            if (i == 5) {
//                token.setToken_type(json[5]);
//            }
//            if (i == 6) {
//                token.setKey4(json[6]);
//            }
//            if (i == 7) {
//                token.setExpires_in(json[7]);
//            }
//
//        }
//        System.out.println(json.toString());
//        String[] theJson = new String[3];
//        for (int i = 0; i < json.length; i++) {
//            if (i == 0) {
//                token.setKey1(json[0]);
//            }
//            if (i == 1) {
//                token.setAccess_token(json[1]);
//            }
//            if (i == 2) {
//                token.setKey2(json[2]);
//            }
//            if (i == 3) {
//                token.setToken_type(json[3]);
//            }
//            if (i == 4) {
//                token.setKey3(json[4]);
//            }
//            if (i == 5) {
//                token.setToken_type(json[5]);
//            }
//            if (i == 6) {
//                token.setKey4(json[6]);
//            }
//            if (i == 7) {
//                token.setExpires_in(json[7]);
//            }
//        for (int i = 0; i < json.length; i++) {
//            if (i == 0) {
//                token.setAccess_token(json[0]);
//            }
//            if (i == 1) {
//                token.setRefresh_token(json[1]);
//            }
//            if (i == 2) {
//                token.setToken_type(json[2]);
//            }
//            if (i == 3) {
//                token.setExpires_in(json[3]);
//            }
//        }
//            System.out.println(json[i]);
//            token.setKey1(json[0]);
//            token.setAccess_token(json[1]);
//            token.setKey2(json[2]);
//            token.setRefresh_token(json[3]);
//            token.setKey3(json[4]);
//            token.setToken_type(json[5]);
//            token.setKey4(json[6]);
//            token.setExpires_in(json[7]);


