package ch.uzh.ifi.hase.soprafs22.constant;

import java.sql.Timestamp;


public class Current_Date {

    public static final long HOUR = 3600 * 1000; // in milli-seconds.

    public static java.util.Date getDate() {


        java.util.Date currentDate = new java.util.Date();

        java.sql.Timestamp currentDate22 = new java.sql.Timestamp(System.currentTimeMillis());
        //System.out.println(currentDate22);

        //java.util.Date newDate = new java.util.Date(currentDate.getTime() + 10 * HOUR);
        //System.out.println(newDate);


        return currentDate;
    }

    public static Timestamp getSQLDate() {

        java.sql.Timestamp currentDate = new java.sql.Timestamp(System.currentTimeMillis());
        System.out.println(currentDate);

        return currentDate;
    }
}
