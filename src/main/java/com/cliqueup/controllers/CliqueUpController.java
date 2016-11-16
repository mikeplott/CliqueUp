package com.cliqueup.controllers;

import com.cliqueup.entities.User;
import com.cliqueup.services.UserRepo;
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

/**
 * Created by michaelplott on 11/16/16.
 */
@RestController
public class CliqueUpController {

    @Autowired
    UserRepo users;

    Server h2;

    @PostConstruct
    public void init() throws SQLException {
        h2.createWebServer().start();
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
}
