package ch.uzh.ifi.hase.soprafs22.constant;
import java.util.Date;


public class Current_Date {

    public static java.util.Date getDate() {

        java.util.Date currentDate = new java.util.Date();

        System.out.println(currentDate);
        return currentDate;
    }
}
