package ch.uzh.ifi.hase.soprafs22.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;


class UserTest {

    @Test
    void setGetUserId() {
        //setter
        User user = new User();
        user.setUserId(1l);

        //getter
        assertEquals(1l, user.getUserId());
    }

    @Test
    void GetToken() {
        //setter
        User user = new User();

        //getter as the user is not created, the token has to be null
        assertEquals(null, user.getToken());
    }

    @Test
    void setGetUsername() {
        //setter
        User user = new User();
        user.setUsername("username");

        //getter
        assertEquals("username", user.getUsername());
    }

    @Test
    void setGetPassword() {
        //setter
        User user = new User();
        user.setPassword("password");

        //getter
        assertEquals("password", user.getPassword());
    }

    @Test
    void setGetEmail() {
        //setter
        User user = new User();
        user.setEmail("email@email.com");

        //getter
        assertEquals("email@email.com", user.getEmail());
    }

    @Test
    void setGetMoreInfo() {
        //setter
        User user = new User();
        user.setMoreInfo("IG: Username");

        //getter
        assertEquals("IG: Username", user.getMoreInfo());
    }

    @Test
    void setGetHighlightCounter() {
        //setter
        User user = new User();
        user.setHighlightCounter(0);

        //getter
        assertEquals(0, user.getHighlightCounter());
    }

    @Test
    void setGetCreationDate() {
        //setter
        User user = new User();
        Timestamp date = new Timestamp(System.currentTimeMillis());
        user.setCreationDate(date);

        //getter
        assertEquals(date, user.getCreationDate());
    }
}