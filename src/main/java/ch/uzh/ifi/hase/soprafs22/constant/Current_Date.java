package ch.uzh.ifi.hase.soprafs22.constant;

import java.sql.Timestamp;


public class Current_Date {

    public static java.util.Date getDate() {

        java.util.Date currentDate = new java.util.Date();
        System.out.println(currentDate);

        return currentDate;
    }

    public static Timestamp getSQLDate() {

        java.sql.Timestamp currentDate = new java.sql.Timestamp(System.currentTimeMillis());
        System.out.println(currentDate);

        return currentDate;
    }
}
